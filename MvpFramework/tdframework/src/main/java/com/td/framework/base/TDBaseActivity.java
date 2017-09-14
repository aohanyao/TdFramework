package com.td.framework.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.td.framework.R;
import com.td.framework.global.app.AppManager;
import com.td.framework.ui.swipebacklayout.SwipeBackLayout;
import com.td.framework.ui.swipebacklayout.app.SwipeBackActivity;
import com.td.framework.utils.DensityUtils;
import com.td.framework.utils.T;
import com.td.framework.utils.amin.JumpAnimUtils;
import com.td.framework.utils.statusbar.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.Disposable;


/**
 * Created by Administrator on 2016/12/14 0014.
 * <p>基类 活动</p>
 */

public class TDBaseActivity extends SwipeBackActivity {
    protected Activity mActivity;
    protected String TAG;
    protected Toolbar mToolbar;
    protected AppManager mAppManager;
    protected final int REQUEST_CODE = 2016;
    protected boolean isCreate = false;
    private SwipeBackLayout mSwipeBackLayout;
    protected Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mActivity = this;
        isCreate = true;
        mAppManager = AppManager.getAppManager();
        mAppManager.addOnStartActivity(this);
        TAG = getClass().toString();
        if (useEventBus()) EventBus.getDefault().register(this);
        if (usSwipeBack()) initSwipActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        System.gc();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        System.gc();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppManager.removeOnStartActivity(this);
        if (useEventBus()) EventBus.getDefault().unregister(this);
        System.gc();
    }

    protected boolean usSwipeBack() {
        return true;
    }

    protected void initSwipActivity() {
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.fontScale = 1.0f;
        //设置字体的大小不会变化， 不会随着 系统字体的更改而变化
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    public void finish() {
        super.finish();
        slideLeftOut();
        System.gc();
    }

    /**
     * 是否使用事件总线
     *
     * @return
     */
    protected boolean useEventBus() {
        return false;
    }

    /**
     * 初始化普通的标题工具栏
     *
     * @param colorId
     */
    protected void initGeneralToolBar(@Nullable int... colorId) {
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.ic_chevron_left_write_24dp);
        mToolbar.setContentInsetStartWithNavigation(10);
        int color = getResources().getColor(colorId[0]);
        StatusBarUtil.setColor(mActivity, color, 1);
        mToolbar.setBackgroundColor(color);

        /**
         * 初始化导航点击事件
         */
        initNavigationEvent();
    }

    /**
     * 初始化导航点击事件
     */
    private void initNavigationEvent() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNavigationClick();
            }
        });

        //导航文字
        View navText = findViewById(R.id.toolbar_nav_text);
        if (navText != null) {
            navText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigationClick();
                }
            });
        }
    }

    /**
     * 标题栏
     */
    protected void initChangerToolbarGradientColor(@Nullable int... colorId) {
        //setSupportActionBar(mToolbar);
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.ic_chevron_left_write_24dp);
        mToolbar.setContentInsetStartWithNavigation(10);
        mToolbar.setBackgroundColor(0x00000000);
        mToolbar.getBackground().setAlpha(0);
        if (colorId.length > 0) {

            //19以上，直接使用渐变背景

            //大于 19     故意设置的 UI要的渐进变化的状态栏和 标题栏 注释掉后面的条件就可以看到
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT /*&& Build.VERSION.SDK_INT > 99*/) {
                AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

                if (appBarLayout != null) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) appBarLayout.getLayoutParams();
                    int statusBarHeight = StatusBarUtil.getStatusBarHeight(mActivity);
                    params.height = DensityUtils.dp2px(mActivity, 44) + statusBarHeight;
                    appBarLayout.setLayoutParams(params);
                    appBarLayout.setPadding(0, statusBarHeight, 0, 0);
                }
                StatusBarUtil.setTransparentForImageViewInFragment(this, null);
            } else {
//                mToolbar.setBackgroundResource(R.drawable.color_primary);
                int color = getResources().getColor(colorId[0]);
                StatusBarUtil.setColor(mActivity, color, 1);
                mToolbar.setBackgroundColor(color);
            }

        }
        /**
         * 初始化导航点击事件
         */
        initNavigationEvent();
    }

    /**
     * 重装的toolbar 工具
     *
     * @param navText 导航文字
     * @param title   标题
     * @param colorId 颜色
     */
    protected void initChangerToolbarGradientColor(@NonNull String navText, @NonNull String title, @Nullable int... colorId) {
        initChangerToolbarGradientColor(colorId);

        //导航文字
        TextView tvNav = (TextView) findViewById(R.id.toolbar_nav_text);
        if (tvNav != null) {
            tvNav.setText(navText);
        }

        //标题栏中间的文字
        TextView tvTitle = (TextView) findViewById(R.id.tv_toolbar_title);
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    /**
     * 重装的toolbar 工具
     *
     * @param title   标题
     * @param colorId 颜色
     */
    protected void initChangerToolbarGradientColor(@NonNull String title, @Nullable int... colorId) {
        initChangerToolbarGradientColor(colorId);

        //标题栏中间的文字
        TextView tvTitle = (TextView) findViewById(R.id.tv_toolbar_title);
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }


    /**
     * 菜单按钮点击
     */
    protected void onNavigationClick() {
        onBackPressed();
        slideLeftOut();
        System.gc();
    }

    /**
     * 右边划出
     */
    protected void slideLeftOut() {
        JumpAnimUtils.out(mActivity);
    }

    /**
     * 进入
     */
    public void slideRightIn() {
        JumpAnimUtils.jumpTo(mActivity);
    }

    /**
     * 显示文字信息
     *
     * @param msgText
     */
    protected void showToast(String msgText) {
        T.showToast(mActivity, msgText);
    }

    /**
     * 显示资源文字信息
     *
     * @param msgId
     */
    protected void showToast(@StringRes int msgId) {
        T.showToast(mActivity, msgId);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        slideLeftOut();

    }

}
