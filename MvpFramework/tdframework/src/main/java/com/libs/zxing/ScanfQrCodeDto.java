package com.libs.zxing;

import java.io.Serializable;

/**
 * <p>作者：江俊超 on 2016/12/17 14:09</p>
 * <p>邮箱：928692385@qq.com</p>
 * <p>扫描二维码的数据传输对象</p>
 */
public class ScanfQrCodeDto implements Serializable {
    public String title;
    public String msgTip;

    public ScanfQrCodeDto(String title, String msgTip) {
        this.title = title;
        this.msgTip = msgTip;
    }
}
