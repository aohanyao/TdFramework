package com.td.framework.global.app;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.td.framework.utils.L;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by 江俊超 on 2016/12/23 0023.
 * <p>Gihub https://github.com/aohanyao</p>
 * <p>应用程序</p>
 */

/**
 * ,s555SB@@&
 * :9H####@@@@@Xi
 * 1@@@@@@@@@@@@@@8
 * ,8@@@@@@@@@B@@@@@@8
 * :B@@@@X3hi8Bs;B@@@@@Ah,
 * ,8i                  r@@@B:     1S ,M@@@@@@#8;
 * 1AB35.i:               X@@8 .   SGhr ,A@@@@@@@@S
 * 1@h31MX8                18Hhh3i .i3r ,A@@@@@@@@@5
 * ;@&i,58r5                 rGSS:     :B@@@@@@@@@@A
 * 1#i  . 9i                 hX.  .: .5@@@@@@@@@@@1
 * sG1,  ,G53s.              9#Xi;hS5 3B@@@@@@@B1
 * .h8h.,A@@@MXSs,           #@H1:    3ssSSX@1
 * s ,@@@@@@@@@@@@Xhi,       r#@@X1s9M8    .GA981
 * ,. rS8H#@@@@@@@@@@#HG51;.  .h31i;9@r    .8@@@@BS;i;
 * .19AXXXAB@@@@@@@@@@@@@@#MHXG893hrX#XGGXM@@@@@@@@@@MS
 * s@@MM@@@hsX#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@&,
 * :GB@#3G@@Brs ,1GM@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@B,
 * .hM@@@#@@#MX 51  r;iSGAM@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@8
 * :3B@@@@@@@@@@@&9@h :Gs   .;sSXH@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:
 * s&HA#@@@@@@@@@@@@@@M89A;.8S.       ,r3@@@@@@@@@@@@@@@@@@@@@@@@@@@r
 * ,13B@@@@@@@@@@@@@@@@@@@5 5B3 ;.         ;@@@@@@@@@@@@@@@@@@@@@@@@@@@i
 * 5#@@#&@@@@@@@@@@@@@@@@@@9  .39:          ;@@@@@@@@@@@@@@@@@@@@@@@@@@@;
 * 9@@@X:MM@@@@@@@@@@@@@@@#;    ;31.         H@@@@@@@@@@@@@@@@@@@@@@@@@@:
 * SH#@B9.rM@@@@@@@@@@@@@B       :.         3@@@@@@@@@@@@@@@@@@@@@@@@@@5
 * ,:.   9@@@@@@@@@@@#HB5                 .M@@@@@@@@@@@@@@@@@@@@@@@@@B
 * ,ssirhSM@&1;i19911i,.             s@@@@@@@@@@@@@@@@@@@@@@@@@@S
 * ,,,rHAri1h1rh&@#353Sh:          8@@@@@@@@@@@@@@@@@@@@@@@@@#:
 * .A3hH@#5S553&@@#h   i:i9S          #@@@@@@@@@@@@@@@@@@@@@@@@@A.
 * <p>
 * <p>
 * 又看源码，看你妹妹呀！
 */
public class App extends MultiDexApplication {
    protected AppManager mAppManager;
    private static Context mContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppManager = AppManager.getAppManager();
        //CrashHandler.getInstance().init(this);
        mContext = this;
        //腾讯浏览服务
        preinitX5WebCore();
        //友盟统计
//        MobclickAgent.setCatchUncaughtExceptions(!L.isDebug);
        MobclickAgent.setDebugMode( L.isDebug );
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        if (L.isDebug) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    /**
     * X5内核在使用preinit接口之后，对于首次安装首次加载没有效果
     * 实际上，X5webview的preinit接口只是降低了webview的冷启动时间；
     * 因此，现阶段要想做到首次安装首次加载X5内核，必须要让X5内核提前获取到内核的加载条件
     */
    private void preinitX5WebCore() {
        if (!QbSdk.isTbsCoreInited()) {//preinit只需要调用一次，如果已经完成了初始化，那么就直接构造view
            QbSdk.preInit(getApplicationContext(), null);//设置X5初始化完成的回调接口  第三个参数为true：如果首次加载失败则继续尝试加载；
        }
    }

    public static Context newInstance() {

        return mContext;
    }
}
