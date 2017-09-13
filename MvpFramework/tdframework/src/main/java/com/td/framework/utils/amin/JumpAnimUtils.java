package com.td.framework.utils.amin;

import android.app.Activity;

import com.td.framework.R;


/**
 * Activity的跳转动画
 *
 * @author direct-2015
 */
public class JumpAnimUtils {
    /**
     * 跳转到
     *
     * @param activity
     */
    public static void jumpTo(Activity activity) {
        activity.overridePendingTransition(R.anim.setup_next_in,
                R.anim.setup_next_out);
    }

    /**
     * 退出动画
     */
    public static void out(Activity activity) {
        activity.overridePendingTransition(R.anim.setup_pre_in, R.anim.setup_pre_out);
    }
}
