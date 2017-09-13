package retrofit2.converter.gson;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.td.framework.utils.L;

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


            return adapter.fromJson(body);
        } catch (JsonSyntaxException e) {
            if (L.isDebug)
                e.printStackTrace();
            throw new RuntimeException("数据解析错误");
        } finally {
            value.close();
        }
    }

}