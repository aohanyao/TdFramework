package com.td.framework.global.app;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.td.framework.utils.L;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by jc on 2016/12/23 0023.
 * <p>Gihub  </p>
 * <p>应用程序</p>
 */

public class App extends MultiDexApplication {
    protected AppManager mAppManager;
    private static Context mContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        initNetConfig();
    }

    /**
     * 初始化网络配置
     * <p>在子类中进行重写，配置网络相关</p>
     * <p>可以在其他地方重写调用方法重新进行网络相关的配置</p>
     */
    public void initNetConfig() {

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
        MobclickAgent.setDebugMode(L.isDebug);
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
