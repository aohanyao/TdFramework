package com.td.framework.utils;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by 江俊超 on 2017/7/13 0013.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>修改TabLayout的宽度为包裹内容</li>
 */
public class TabLayoutUtil {


    public static void setWrapContent(final TabLayout tabLayout, final int padding) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {

                try {
                    //拿到tabLayout的mTabStrip属性
                    Field mTabStripField = tabLayout.getClass().getDeclaredField("mTabStrip");
                    mTabStripField.setAccessible(true);

                    //获得 SlidingTabStrip
                    LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tabLayout);
//                    Bc bc = (Bc) mTabStrip;
                    //遍历 SlidingTabStrip子view
                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        //获得TabView
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到TabView的mTextView属性
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        //获取的TextView
                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);
                        //字体大小
//                        mTextView.setTextSize(textSize);
//                        mTextView.setTypeface(Typeface.DEFAULT_BOLD);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = padding;
                        params.rightMargin = padding;
                        tabView.setLayoutParams(params);

                        tabView.postInvalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
