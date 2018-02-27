package com.td.framework.base.view

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat.startActivity
import android.util.Pair
import android.view.View
import com.td.framework.biz.NetError
import com.td.framework.global.app.Constant
import com.td.framework.global.router.RouterHelper
import com.td.framework.model.BaseIntentDto
import com.td.framework.utils.T
import com.td.framework.utils.amin.JumpAnimUtils
import com.trello.rxlifecycle2.components.support.RxFragment
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus


/**
 * Created by Administrator on 2016/12/15 0015.
 *
 * 基类 碎片
 */

open class TDBaseFragment : RxFragment() {
    protected val mActivity: Activity by lazy { activity!! }

    protected var isCreate = false
    protected var subscribe: Disposable? = null
    protected val REQUEST_CODE = 2017
    protected val LOGIN_OUT = NetError.LOGIN_OUT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCreate = true
        if (useEventBus()) EventBus.getDefault().register(this)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (useEventBus()) EventBus.getDefault().unregister(this)
    }

    /**
     * 是否使用事件总线

     * @return
     */
    protected fun useEventBus(): Boolean {
        return false
    }

    /**
     * 打开一个activity

     * @param classPath   路由地址
     * *
     * @param requestCode 请求code
     */
    protected fun openActivityForResult(classPath: String, requestCode: Int) {
        //启用路由打开页面
        RouterHelper.navigationActivityForResult(classPath, mActivity, requestCode)
    }

    /**
     * 通过路由地址打开一个activity

     * @param classPath
     */
    protected fun openActivity(classPath: String) {
        RouterHelper.navigationActivity(classPath, mActivity)
    }

    protected fun openActivityFromSharedElementsTransition(classPath: String) {
        RouterHelper.navigationActivity(classPath, mActivity)
    }

    /**
     * 通过路由地址打开一个activity,携带数据的

     * @param classPath
     * *
     * @param bundle
     */
    protected fun openActivity(classPath: String, bundle: Bundle) {
        //打开携带数据的
        RouterHelper.navigationActivity(classPath, mActivity, bundle)
    }

    /**
     * 打开一个页面，有值 有数据返回

     * @param classPath
     * *
     * @param bundle
     * *
     * @param requestCode
     */
    protected fun openActivityForResult(classPath: String, bundle: Bundle, requestCode: Int) {
        //打开携带数据的
        RouterHelper.navigationActivityForResult(classPath, mActivity, bundle, requestCode)
    }

    /**
     * 打开某个Activity  没有参数的

     * @param toClass
     */
    protected fun openActivity(toClass: Class<*>) {
        startActivity(Intent(mActivity, toClass))
        slideRightIn()
    }

    protected fun openActivity(toClass: Intent) {
        startActivity(toClass)
        slideRightIn()
    }


    /**
     * 右边划出
     */
    protected fun slideLeftOut() {
        JumpAnimUtils.out(mActivity)
    }

    /**
     * 进入
     */
    fun slideRightIn() {
        JumpAnimUtils.jumpTo(mActivity)
    }


    /**
     * 显示文字信息

     * @param msgText
     */
    protected fun showToast(msgText: String) {
        T.showToast(mActivity, msgText)
    }

    /**
     * 显示资源文字信息

     * @param msgId
     */
    protected fun showToast(msgId: Int) {
        T.showToast(mActivity, msgId)
    }

    /**
     * 用动画的方式打开一个Activity

     * @param toClass        需要打开到那个Activity
     * *
     * @param intentData     需要传递的数据对象
     * *
     * @param sharedElements 需要分享的展现动画的元素
     */
    @SafeVarargs
    protected fun startActivityFromSharedElementsTransition(toClass: Class<*>, intentData: BaseIntentDto?, vararg sharedElements: Pair<View, String>) {

        val intent = Intent(mActivity, toClass)
        if (intentData != null) {
            intent.putExtra(Constant.IDK, intentData)
        }
        //5.0及以上使用过度动画
        if (Build.VERSION.SDK_INT >= 21) {
            val options = ActivityOptions.makeSceneTransitionAnimation(mActivity, *sharedElements)
            ActivityCompat.startActivity(mActivity, intent, options.toBundle())
        } else {
            if (sharedElements.size == 0) {
                openActivity(intent)
            } else {
                val transitionView = sharedElements[0].first
                //让新的Activity从一个小的范围扩大到全屏
                val options = ActivityOptionsCompat.makeScaleUpAnimation(transitionView, //The View that the new activity is animating from
                        transitionView.width.toInt() / 2, transitionView.height.toInt() / 2, //拉伸开始的坐标
                        0, 0)//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
                ActivityCompat.startActivity(mActivity, intent, options.toBundle())
            }

        }
    }

    /**
     *  *  1. 线程切换
     *  *  2. Rx生命周期绑定
     * @return
     */
    protected fun <T> getCompose(): FlowableTransformer<T, T> {
        return FlowableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose<T>(bindToLifecycle())
        }
    }
}
