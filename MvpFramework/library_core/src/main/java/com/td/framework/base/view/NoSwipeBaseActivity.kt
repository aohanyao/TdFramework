package com.td.framework.base.view

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.Toolbar
import android.util.Pair
import android.view.View

import com.td.framework.R
import com.td.framework.global.app.AppManager
import com.td.framework.global.app.Constant
import com.td.framework.model.BaseIntentDto
import com.td.framework.ui.swipebacklayout.SwipeBackLayout
import com.td.framework.utils.L
import com.td.framework.utils.T
import com.td.framework.utils.amin.JumpAnimUtils
import com.td.framework.utils.statusbar.StatusBarUtil
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.umeng.analytics.MobclickAgent
import io.reactivex.disposables.Disposable

import org.greenrobot.eventbus.EventBus


/**
 * Created by Administrator on 2016/12/14 0014.
 *
 * 基类 活动
 *
 * 没有滑动返回的
 */

open class NoSwipeBaseActivity : RxAppCompatActivity() {

    protected val mActivity: Activity by lazy { NoSwipeBaseActivity@ this }
    protected val TAG: String by lazy { javaClass.toString() }
    protected var mToolbar: Toolbar? = null
    protected val mAppManager: AppManager by lazy { AppManager.getAppManager() }
    protected val REQUEST_CODE = 2016
    protected var isCreate = false
    private var mSwipeBackLayout: SwipeBackLayout? = null
    protected var subscribe: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAppManager.addOnStartActivity(this)
        if (useEventBus()) EventBus.getDefault().register(this)
    }

    override fun getResources(): Resources {
        val res = super.getResources()
        val config = Configuration()
        config.fontScale = 1.0f

        res.updateConfiguration(config, res.displayMetrics)
        return res
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
        System.gc()
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
        System.gc()
    }

    override fun finish() {
        super.finish()
        slideLeftOut()
        System.gc()
    }

    /**
     * 是否使用事件总线

     * @return
     */
    protected fun useEventBus(): Boolean {
        return false
    }

    /**
     * 标题栏
     */
    protected fun initToolbar(vararg colorId: Int) {
        //setSupportActionBar(mToolbar);
        mToolbar?.title = ""
        mToolbar?.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp)
        if (colorId.isNotEmpty()) {
            mToolbar?.setBackgroundColor(resources.getColor(colorId[0]))
            try {
                StatusBarUtil.setColor(mActivity, resources.getColor(colorId[1]), 1)
            } catch (e: Exception) {
                StatusBarUtil.setColor(mActivity, 0xffd2d2d2.toInt(), 1)

            }

        }
        mToolbar?.setNavigationOnClickListener { onNavigationClick() }

    }

    /**
     * 用动画的方式打开一个Activity

     * @param toClass        需要打开到那个Activity
     * *
     * @param intentData     需要传递的数据对象
     * *
     * @param sharedElements 需要分享的展现动画的元素
     */
    protected fun startActivityFromSharedElementsTransition(toClass: Class<*>, intentData: BaseIntentDto, vararg sharedElements: Pair<View, String>) {

        val intent = Intent(mActivity, toClass)
        intent.putExtra(Constant.IDK, intentData)
        //5.0及以上使用过度动画
        if (Build.VERSION.SDK_INT >= 21) {
            val options = ActivityOptions.makeSceneTransitionAnimation(mActivity, *sharedElements)
            ActivityCompat.startActivity(mActivity, intent, options.toBundle())
        } else {
            val transitionView = sharedElements[0].first
            //让新的Activity从一个小的范围扩大到全屏
            val options = ActivityOptionsCompat.makeScaleUpAnimation(transitionView, //The View that the new activity is animating from
                    transitionView.width.toInt() / 2, transitionView.height.toInt() / 2, //拉伸开始的坐标
                    0, 0)//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
            ActivityCompat.startActivity(mActivity, intent, options.toBundle())
        }
    }

    /**
     * 菜单按钮点击
     */
    protected fun onNavigationClick() {
        onBackPressed()
        slideLeftOut()
        System.gc()
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
    protected fun showTost(msgText: String) {
        T.showToast(mActivity, msgText)
    }

    /**
     * 显示资源文字信息

     * @param msgId
     */
    protected fun showTost(@StringRes msgId: Int) {
        T.showToast(mActivity, msgId)
    }

    /**
     * 打印错误信息

     * @param msgText
     */
    protected fun e(msgText: String) {
        L.e(TAG, msgText)
    }

    /**
     * 调试信息

     * @param msgText
     */
    protected fun d(msgText: String) {
        L.d(TAG, msgText)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        slideLeftOut()

    }

    override fun onDestroy() {
        super.onDestroy()
        mAppManager.removeOnStartActivity(this)
        if (useEventBus()) EventBus.getDefault().unregister(this)
        System.gc()
    }


}
