package com.td.framework.moudle.share;

/**
 * Created by jjc on 2017/4/16.
 * <p>分享条目的数据对象</p>
 */

public class ShareInfo {
    private int imageId;
    private String name;

    public ShareInfo(int imageId, String name) {
        this.imageId = imageId;
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
