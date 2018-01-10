package com.td.framework.global.app;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jc on 2016/12/23 0023.
 * <p>Gihub https://github.com/aohanyao</p>
 * <p>程序管理器</p>
 */

public class AppManager {
    /**
     * 存放所有已经启动的活动
     */
    private List<Activity> onStartActivity;

    private AppManager() {
        onStartActivity = new ArrayList<>();
    }

    private static AppManager mAppManager;

    public static AppManager getAppManager() {
        if (mAppManager == null) {
            mAppManager = new AppManager();
        }
        return mAppManager;
    }


    /**
     * 添加一个活动
     */
    public void addOnStartActivity(Activity activity) {
        if (onStartActivity == null) {
            onStartActivity = new ArrayList<>();
        }
        onStartActivity.add(activity);
    }

    /**
     * 移除一个活动
     */
    public void removeOnStartActivity(Activity activity) {
        try {
            if (onStartActivity != null) {
                onStartActivity.remove(activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        activity.finish();
//        http://idserver.sjqianjin.com/API/ClientService.aspx?rnd=0.123456
//        String params = "Data2=" + URLEncoder.encode("ble4,server," + SysUtil.getImei(context) + ",V1.51", "UTF-8");
    }

    /**
     * 是否存在 Activity
     * @param activity
     * @return
     */
    public boolean isExitActivity(Class activity) {
        if (onStartActivity != null) {
            for (Activity activity1 : onStartActivity) {
                if (activity1.getClass().equals(activity)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 退出应用程序
     */
    public void exitApplication() {

        try {
            for (Activity activity : onStartActivity) {
                if (activity != null) {
                    activity.finish();
                }
            }
            onStartActivity.clear();
            onStartActivity = null;
        }catch (Exception e){}


    }

}
