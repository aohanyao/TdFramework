package com.td.framework.model.bean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jc on 2018-03-21.
 * Version:1.0
 * Description:
 * ChangeLog:
 */

public class ListDataModelImp<T> implements ListDataModel {

    /**
     * code : 0
     * data : {"data":[{"createTime":"2018-03-21T08:54:21.717Z","creator":"string","deadline":"2018-03-21T08:54:21.717Z","description":"string","id":0,"status":0,"title":"string"}],"index":0,"size":0,"total":0,"totalRecord":0}
     * message : string
     */

    private int code;
    private DataBeanX<T> data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBeanX<T> getData() {
        return data;
    }

    public void setData(DataBeanX<T> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int getMaxPage() {
        if (data == null) {
            return 0;
        }
        return data.total;
    }

    @NotNull
    @Override
    public List getList() {
        if (data == null) {
            return new ArrayList();
        }
        return data.getData();
    }

    public static class DataBeanX<T> {

        private int index;
        private int size;
        private int total;
        private int totalRecord;
        private List<T> data;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotalRecord() {
            return totalRecord;
        }

        public void setTotalRecord(int totalRecord) {
            this.totalRecord = totalRecord;
        }

        public List<T> getData() {
            return data;
        }

        public void setData(List<T> data) {
            this.data = data;
        }


    }
}
