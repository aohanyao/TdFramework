package retrofit2.converter.gson;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.MalformedJsonException;
import com.orhanobut.logger.Logger;
import com.td.framework.biz.NetError;
import com.td.framework.utils.L;

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
            String body = value.string();
            Logger.json(body);
            System.out.print(body);
            JSONObject jsonObject = new JSONObject(body);

            //先判断登陆的情况
            //--------------登陆start-------------------
            //包含了 access_token 和refresh_token 默认为
            // 登陆成功
            if (body.contains("access_token") && body.contains("access_token")) {
                String loginDataCode = "{\"code\": 200,\n" +
                        "\"data\":" + body + ",\n" +
                        "\"message\": \"string\"\n" +
                        "}".trim();

                //返回登陆结果
                return adapter.fromJson(loginDataCode);
            }


            //--------------登陆end-------------------


            try {
                int code = jsonObject.getInt("code");
                String msg = jsonObject.getString("message");
                JSONObject data = jsonObject.getJSONObject("data");

                if (code != 200) {
                    if (code==0){
                        throw new NetError(msg, NetError.NoDataError);
                    }
                    L.e("获取服务数据错误");
                    throw new NetError(msg, NetError.OTHER);
                }
            } catch (Exception e) {

            }


//            //------------------------------------------测试
//
//            //成功 没有返回值的情况
//            if (TextUtils.isEmpty(jsonObject.getString("msg"))) {
//                //直接返回
//                return adapter.fromJson(body);
//            }
//            //统一的状态码处理 end
//            String record = jsonObject.getString("data");
//            //getToke解析


            return adapter.fromJson(body);
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