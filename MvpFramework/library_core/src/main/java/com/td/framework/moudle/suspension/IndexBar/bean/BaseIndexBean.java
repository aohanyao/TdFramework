package com.td.framework.moudle.suspension.IndexBar.bean;


import com.td.framework.moudle.suspension.suspension.ISuspensionInterface;

import java.io.Serializable;

/**
 * 介绍：索引类的标志位的实体基类
 */

public abstract class BaseIndexBean implements ISuspensionInterface ,Serializable{
    private String topTag;//顶部悬浮的文字

    public String getTopTag() {
        return topTag;
    }

    public BaseIndexBean setTopTag(String topTag) {
        this.topTag = topTag;
        return this;
    }

    @Override
    public String getSuspensionTag() {
        return topTag;
    }

    @Override
    public boolean isShowSuspension() {
        return true;
    }
}
