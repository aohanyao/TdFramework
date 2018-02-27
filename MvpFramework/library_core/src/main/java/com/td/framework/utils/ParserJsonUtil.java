package com.td.framework.utils;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.List;

/**
 * 解析对象基类
 *
 */
public class ParserJsonUtil {

    /**
     * 从JSON字符串中反序列化T对象
     *
     * @param pJsonStr JSON字符串
     * @param pClass 对象的Class
     * @param <T> 将要反序列化成的T对象
     * @return T对象
     */
    public static <T> T parserTFromJson(String pJsonStr, final Class<T> pClass){
        T _T;
        try{
            if (!TextUtils.isEmpty(pJsonStr)){
                Gson _Gson = new Gson();
                _T = _Gson.fromJson(pJsonStr, pClass);
            }else {
                _T = null;
            }
        }catch (Exception e){
            e.printStackTrace();
            _T = null;
        }
        return _T;
    }

    /**
     * 序列化T对象为JSON字符串
     *
     * @param pT T对象
     * @param <T> 将要序列化的T对象
     * @return JSON字符串
     */
    public static <T> String parserTToJson(T pT){
        String _JosnStr;
        try{
            if (pT != null) {
                Gson _Gson = new Gson();
                _JosnStr = _Gson.toJson(pT);
            }else {
                _JosnStr = "";
            }
        }catch (Exception e){
            e.printStackTrace();
            _JosnStr = "";
        }
        return _JosnStr;
    }

    /**
     * 从JSON字符串中反序列化List<T>集合对象
     *
     * @param pJsonData JSON字符串
     * @param pClass T对象的Class
     * @param <T> 对象类型
     * @return List<T>集合对象
     */
    public static <T> List<T> parserListTFromJson(final String pJsonData, final Class<T> pClass){
        List<T> _TList;
        try{
            if (!TextUtils.isEmpty(pJsonData)){
                Gson _Gson = new Gson();
                _TList = _Gson.fromJson(pJsonData, new ListOfJson<T>(pClass));
            }else {
                _TList = null;
            }
        }catch (Exception e){
            e.printStackTrace();
            _TList = null;
        }
        return _TList;
    }

    /**
     * 序列化List<T>集合对象为JSON字符串
     *
     * @param pListT List<T>集合对象
     * @param <T> 对象类型
     * @return JSON字符串
     */
    public static <T> String parserListTToJson(List<T> pListT){
        String _JosnStr;
        try{
            if (pListT != null) {
                Gson _Gson = new Gson();
                _JosnStr = _Gson.toJson(pListT);
            }else {
                _JosnStr = "";
            }
        }catch (Exception e){
            e.printStackTrace();
            _JosnStr = "";
        }
        return _JosnStr;
    }
}