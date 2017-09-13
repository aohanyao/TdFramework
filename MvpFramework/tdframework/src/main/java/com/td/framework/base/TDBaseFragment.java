package com.td.framework.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.td.framework.global.app.Constant;
import com.td.framework.global.router.RouterHelper;
import com.td.framework.utils.T;
import com.td.framework.utils.amin.JumpAnimUtils;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.disposables.Disposable;


/**
 * Created by Administrator on 2016/12/15 0015.
 * <p>基类 碎片</p>
 */

public class TDBaseFragment extends RxFragment {
    protected Activity mActivity;
    protected boolean isCreate = false;
    protected Disposable disposable;
    protected final int REQUEST_CODE = 2017;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        isCreate = true;
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
     * 打开一个activity
     *
     * @param classPath   路由地址
     * @param requestCode 请求code
     */
    protected void openActivityForResult(String classPath, int requestCode) {
        //启用路由打开页面
        RouterHelper.INSTANCE.navigationActivityForResult(classPath, mActivity, requestCode);
    }

    /**
     * 通过路由地址打开一个activity
     *
     * @param classPath
     */
    protected void openActivity(String classPath) {
        RouterHelper.INSTANCE.navigationActivity(classPath, mActivity);
    }

    /**
     * 通过路由地址打开一个activity,携带数据的
     *
     * @param classPath
     * @param bundle
     */
    protected void openActivity(String classPath, Bundle bundle) {
        //打开携带数据的
        RouterHelper.INSTANCE.navigationActivity(classPath, mActivity, bundle);
    }

    /**
     * 打开一个页面，有值 有数据返回
     * @param classPath
     * @param bundle
     * @param requestCode
     */
    protected void openActivityForResult(String classPath, Bundle bundle, int requestCode) {
        //打开携带数据的
        RouterHelper.INSTANCE.navigationActivityForResult(classPath, mActivity, bundle, requestCode);
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

    protected void openActivityForResult(Class<? extends Activity> c, int requestCode) {
        Intent intent = new Intent(mActivity, c);
        startActivityForResult(intent, requestCode);
        slideRightIn();
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
    protected void showTost(int msgId) {
        T.showToast(mActivity, msgId);
    }

}
