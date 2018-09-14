package com.td.framework.utils.amin;

import android.app.Activity;

import com.td.framework.utils.R;


/**
 * Activity的跳转动画
 *
 * @author jjc
 */
public class JumpAnimUtils {
    /**
     * 跳转到
     *
     * @param activity
     */
    public static void jumpTo(Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activity.getWindow().setExitTransition(new Explode());
//            activity.getWindow().setEnterTransition(new Explode());
//        }else{
            activity.overridePendingTransition(R.anim.setup_next_in,
                    R.anim.setup_next_out);
//        }

    }

    /**
     * 退出动画
     */
    public static void out(Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activity.getWindow().setEnterTransition(new Explode());
//            activity.getWindow().setExitTransition(new Explode());
//        }else{
            activity.overridePendingTransition(R.anim.setup_pre_in, R.anim.setup_pre_out);
//        }

    }
}
