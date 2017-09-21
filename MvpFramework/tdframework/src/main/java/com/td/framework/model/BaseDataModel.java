package com.td.framework.model;

/**
 * Created by 江俊超 on 2017/7/21 0021.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>基本的数据返回对象</li>
 */
public class BaseDataModel<T> {
    private String msg;
    private int code;
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
