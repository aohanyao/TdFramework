package com.td.framework.base;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;

import com.td.framework.R;
import com.td.framework.global.app.AppManager;
import com.td.framework.global.app.Constant;
import com.td.framework.model.BaseIntentDto;
import com.td.framework.utils.L;
import com.td.framework.utils.T;
import com.td.framework.utils.amin.JumpAnimUtils;
import com.td.framework.utils.statusbar.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Administrator on 2016/12/14 0014.
 * <p>基类 活动</p>
 * <p>没有滑动返回的</p>
 */

public class NoSwipeBaseActivity extends RxAppCompatActivity {
    protected Activity mActivity;
    protected String TAG;
    protected Toolbar mToolbar;
    protected AppManager mAppManager;
    protected final int REQUEST_CODE = 2016;
    protected boolean isCreate = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        isCreate = true;
        mAppManager = AppManager.getAppManager();
        mAppManager.addOnStartActivity(this);
        TAG = getClass().toString();
        if (useEventBus()) EventBus.getDefault().register(this);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.fontScale = 1.0f;

        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
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
     * 标题栏
     */
    protected void initToolbar(@Nullable int... colorId) {
        //setSupportActionBar(mToolbar);
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);
        if (colorId.length > 0) {
            mToolbar.setBackgroundColor(getResources().getColor(colorId[0]));
            try {
                StatusBarUtil.setColor(mActivity, getResources().getColor(colorId[1]), 1);
            } catch (Exception e) {
                StatusBarUtil.setColor(mActivity, 0xffd2d2d2, 1);

            }
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNavigationClick();
            }
        });

    }

    /**
     * 用动画的方式打开一个Activity
     *
     * @param toClass        需要打开到那个Activity
     * @param intentData     需要传递的数据对象
     * @param sharedElements 需要分享的展现动画的元素
     */
    protected void startActivityFromSharedElementsTransition(Class toClass, BaseIntentDto intentData, Pair<View, String>... sharedElements) {

        final Intent intent = new Intent(mActivity, toClass);
        intent.putExtra(Constant.INTENT_DATA_KEY, intentData);
        //5.0及以上使用过度动画
        if (Build.VERSION.SDK_INT >= 21) {
            final ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(mActivity, sharedElements);
            ActivityCompat.startActivity(mActivity, intent, options.toBundle());
        } else {
            View transitionView = sharedElements[0].first;
            //让新的Activity从一个小的范围扩大到全屏
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeScaleUpAnimation(transitionView, //The View that the new activity is animating from
                            (int) transitionView.getWidth() / 2, (int) transitionView.getHeight() / 2, //拉伸开始的坐标
                            0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
            ActivityCompat.startActivity(mActivity, intent, options.toBundle());
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
    protected void showTost(String msgText) {
        T.showToast(mActivity, msgText);
    }

    /**
     * 显示资源文字信息
     *
     * @param msgId
     */
    protected void showTost(@StringRes int msgId) {
        T.showToast(mActivity, msgId);
    }

    /**
     * 打印错误信息
     *
     * @param msgText
     */
    protected void e(String msgText) {
        L.e(TAG, msgText);
    }

    /**
     * 调试信息
     *
     * @param msgText
     */
    protected void d(String msgText) {
        L.d(TAG, msgText);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        slideLeftOut();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppManager.removeOnStartActivity(this);
        if (useEventBus()) EventBus.getDefault().unregister(this);
        System.gc();
    }

    /**
     * 打开某个Activity  没有参数的
     *
     * @param toClass
     */
    protected void openActivity(Class<?> toClass) {
        startActivity(new Intent(mActivity, toClass));
        slideRightIn();
    }

    protected void openActivity(Intent toClass) {
        startActivity(toClass);
        slideRightIn();
    }

    /**
     * 打开有返回值的Activity 无传递数据
     *
     * @param toClass
     */
    protected void openActivityForResult(Class<?> toClass) {
        startActivityForResult(new Intent(mActivity, toClass), REQUEST_CODE);
        slideRightIn();
    }

    protected void openActivityForResult(Class<? extends Activity> c, int requestCode, int data) {
        Intent intent = new Intent(mActivity, c);
        intent.putExtra(Constant.INTENT_DATA_KEY, data);
        startActivityForResult(intent, requestCode);
        slideRightIn();
    }

    protected void openActivityForResult(Class<? extends Activity> c, int requestCode, String data) {
        Intent intent = new Intent(mActivity, c);
        intent.putExtra(Constant.INTENT_DATA_KEY, data);
        startActivityForResult(intent, requestCode);
        slideRightIn();
    }

    protected void openActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        slideRightIn();
    }
}
