package com.td.framework.model.bean;

/**
 * Created by jc on 2017/7/21 0021.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>基本的数据返回对象</li>
 * <p>不是列表的情况下可以直接进行使用</p>
 */
public class BaseDataModel<T> {
    private String message;
    private int code = 200;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
