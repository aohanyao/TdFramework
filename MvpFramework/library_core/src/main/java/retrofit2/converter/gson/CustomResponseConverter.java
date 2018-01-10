package retrofit2.converter.gson;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.MalformedJsonException;
import com.td.framework.biz.NetError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 自定义的类型解析器
 * <p>用来对付不安常理出牌的后台数据格式</p>
 *
 * @param <T>
 */
public class CustomResponseConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    public CustomResponseConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {


            //TODO  一定要适配    有分页 和无分页的情况

            String body = value.string();


            JSONObject jsonObject = new JSONObject(body);

            int code = 0;
            try {
                //有code的方式 为服务器返回的数据
                code = jsonObject.getInt("code");
            } catch (JSONException ignored) {

            }
            if (code == NetError.LOGIN_OUT) {
                throw new NetError("登陆超时,请重新登陆.", code);

            }

            //不成功的状态 抛出
            if (code != 200) {
                throw new NetError(jsonObject.getString("msg"), code);
            }
            //成功 没有返回值的情况
            if (TextUtils.isEmpty(jsonObject.getString("msg"))) {
                //直接返回
                return adapter.fromJson(body);
            }
            //统一的状态码处理 end
            String record = jsonObject.getString("data");
            //getToke解析


            return adapter.fromJson(record);
        } catch (JsonSyntaxException | MalformedJsonException e) {
//            if (BuildConfig.DEBUG)
            e.printStackTrace();
            throw new RuntimeException("数据解析错误");
        } catch (JSONException e) {
//            if (BuildConfig.DEBUG)
            e.printStackTrace();
            throw new RuntimeException("数据解析错误");
        } finally {
            value.close();
        }
    }

}