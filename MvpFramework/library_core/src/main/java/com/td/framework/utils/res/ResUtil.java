package com.td.framework.utils.res;

import android.graphics.drawable.Drawable;

import com.td.framework.global.app.App;

/**
 * Created by jc on 2017/1/5 0005.
 * <p>Gihub https://github.com/aohanyao</p>
 * <p>获取资源</p>
 */
public class ResUtil {
    private ResUtil() {

    }

    /**
     * 获取对应的颜色
     *
     * @param resColorId
     * @return
     */
    public static int getResourcesColor( int resColorId) {
        return App.newInstance().getResources().getColor(resColorId);
    }

    /**
     * 获取文字资源
     * @param resStringId
     * @return
     */
    public static String getResourcesString(int resStringId) {
        return App.newInstance().getResources().getString(resStringId);
    }
    /**
     * 获取Drawable
     * @param resDrawableId
     * @return
     */
    public static Drawable getResourcesDrawable(int resDrawableId) {
        Drawable mDistanceDrawable =  App.newInstance().getResources().getDrawable(resDrawableId);
        mDistanceDrawable.setBounds(0, 0, mDistanceDrawable.getMinimumWidth(), mDistanceDrawable.getMinimumHeight());
        return mDistanceDrawable;
    }
}
