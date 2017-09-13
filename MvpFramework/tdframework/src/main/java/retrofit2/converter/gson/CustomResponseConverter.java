package retrofit2.converter.gson;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.td.framework.biz.NetError;
import com.td.framework.utils.L;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.converter.gson.dto.BaseResponseDto;

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


            //登陆失效
            if (body.contains("验证失败") ||body.contains("系统异常:null")
                    ||body.contains("Object of class [com.common.MemberManager.entity.MemberInformation] with identifier")) {
                throw new NetError("您登陆已失效，请重新登陆", NetError.LOGIN_OUT);
            }
            if (L.isDebug){
//                L.e(body);
                Logger.json(body);
            }
            //陈肖锋特殊情况
//            body.startsWith()
            if (body.contains("{\"code\":") && !body.contains("{\"record\":{\"code\":")) {
                //未登录状态  {"code":"-1","data":"获取不到当前登陆用户，请先登陆"}
                JSONObject mCodeJsonObject = new JSONObject(body);
                String code = mCodeJsonObject.getString("code");
                if ("-1".equals(code) && body.contains("获取不到当前登陆用户")){
                    throw new NetError("您登陆已失效，请重新登陆", NetError.LOGIN_OUT);
                }

                return adapter.fromJson(body);//陈肖锋特殊种类
            }

            //处理林伟泽企业邀请码认证的错误情况
            if (body.contains("无此企业邀请信息")) {
                // {"record":{"flag":0,"msg":"无此企业邀请信息！"},"message":null,"totalCount":0,"records":null,"instanceId":null,"trancode":"
                // enterpriseEmployeesManager.acceptEnterpriseInvitation"}
                throw new NetError("邀请码无效", NetError.OTHER);
            }
            //处理林伟泽 登陆密码错误
            if (body.contains("密码错误") || body.contains("登录失败")) {
                throw new NetError("用户名或密码错误", NetError.OTHER);
            }

            JSONObject jsonObject = new JSONObject(body);

            //园区新闻 活动 返回的数据 start
            try {
                JSONArray records = jsonObject.getJSONArray("records");
                return adapter.fromJson(jsonObject.getString("records"));
            } catch (JSONException e) {//Json
//                if (L.isDebug)
//                    e.printStackTrace();
                try {
                    String trancode = jsonObject.getString("trancode");  //处理活动的特殊情况！！！
                    if (trancode.equals("activityApplyManager.getPagerActivityApplys")
                            || trancode.equals("preappointmentManager.getPagerPreappointmentsByUserId")) {
                        return null;
                    }

                } catch (JSONException e2) {
                    //e2.printStackTrace();
                }
            }
            //园区新闻 活动  返回的数据 end


            try {
                jsonObject.getJSONObject("message");
            } catch (JSONException e) {
                try {
                    String trancode = jsonObject.getString("trancode");  //处理林伟泽的数据返回结果
                    if (trancode.equals("memberInformationManager.getMemberInformationByLoginUser")) {
                        //林伟泽 查看是否是企业会员
                        return adapter.fromJson(jsonObject.getString("record"));
                    }
                } catch (Exception e2) {
                    return adapter.fromJson(body);//其他种类
                }
            }


            //统一的状态码处理 start
            BaseResponseDto.MessageBean messageBean = gson.fromJson(body, BaseResponseDto.class).getMessage();

            if (messageBean != null && messageBean.getCode() != null && !messageBean.getCode().equals("000000")) {//全局请求不成功
                if (Integer.parseInt(messageBean.getCode()) == NetError.USER_NOT_BAND_WECHAT) {//用户没有绑定手机号码
                    //抛出用户未绑定手机号码的异常
                    return adapter.fromJson(jsonObject.getString("record"));
                }
                //entity.MemberInformation] with identifier [f8cd82315a83ce2c015a83ea1cce0003]: not found
                // token校验失败:消费者没有token参数
                throw new NetError(new RuntimeException(messageBean.getInfo()), Integer.parseInt(messageBean.getCode()));
            }

            //统一的状态码处理 end

            String record = jsonObject.getString("record");


            return adapter.fromJson(record);
        } catch (JsonSyntaxException e) {
            if (L.isDebug)
                e.printStackTrace();
            throw new RuntimeException("数据解析错误");
        } catch (JSONException e) {
            if (L.isDebug)
                e.printStackTrace();
            throw new RuntimeException("数据解析错误");
        } finally {
            value.close();
        }
    }

}