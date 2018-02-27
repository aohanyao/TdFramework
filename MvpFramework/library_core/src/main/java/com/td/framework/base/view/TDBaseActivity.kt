package com.td.framework.base.view

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.AppBarLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.Toolbar
import android.util.Pair
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.td.framework.R
import com.td.framework.global.app.AppManager
import com.td.framework.global.app.Constant
import com.td.framework.model.BaseIntentDto
import com.td.framework.ui.swipebacklayout.SwipeBackLayout
import com.td.framework.ui.swipebacklayout.app.SwipeBackActivity
import com.td.framework.utils.DensityUtils
import com.td.framework.utils.T
import com.td.framework.utils.amin.JumpAnimUtils
import com.td.framework.utils.statusbar.StatusBarUtil
import com.umeng.analytics.MobclickAgent
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus


/**
 * Created by Administrator on 2016/12/14 0014.
 *
 * 基类 活动
 */

open class TDBaseActivity : SwipeBackActivity() {
    protected open val mActivity: Activity by lazy { TDBaseActivity@ this }
    protected val TAG: String by lazy { javaClass.toString() }
    protected val mToolbar: Toolbar?  by lazy {
        findViewById(R.id.base_toolbar) as? Toolbar?
    }
    protected val mAppManager: AppManager by lazy { AppManager.getAppManager() }
    protected val REQUEST_CODE = 2016
    protected var isCreate = false
    private var mSwipeBackLayout: SwipeBackLayout? = null
    protected open var subscribe: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)

        isCreate = true
        mAppManager.addOnStartActivity(this)
        if (useEventBus()) EventBus.getDefault().register(this)
        if (usSwipeBack()) initSwipeActivity()
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

    override fun onDestroy() {
        super.onDestroy()
        mAppManager.removeOnStartActivity(this)
        if (useEventBus()) EventBus.getDefault().unregister(this)
        System.gc()
    }

    protected open fun usSwipeBack(): Boolean {
        return true
    }

    /***
     * 初始化话滑动返回
     */
    private fun initSwipeActivity() {
        mSwipeBackLayout = swipeBackLayout
        mSwipeBackLayout?.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
    }

    override fun getResources(): Resources {
        val res = super.getResources()
        val config = Configuration()
        config.fontScale = 1.0f
        //设置字体的大小不会变化， 不会随着 系统字体的更改而变化
        //2018-2-27 附加解释：在App中固定字体的大小，防止当手机设置大字体，老人模式
        //的时候 字体变大，样式更改
        res.updateConfiguration(config, res.displayMetrics)
        return res
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
            val transitionView = sharedElements[0].first
            //让新的Activity从一个小的范围扩大到全屏
            val options = ActivityOptionsCompat.makeScaleUpAnimation(transitionView, //The View that the new activity is animating from
                    transitionView.width.toInt() / 2, transitionView.height.toInt() / 2, //拉伸开始的坐标
                    0, 0)//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
            ActivityCompat.startActivity(mActivity, intent, options.toBundle())
        }
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
     * 初始化普通的标题工具栏

     * @param colorId
     */
    protected fun initGeneralToolBar(vararg colorId: Int) {
        mToolbar?.title = ""
        mToolbar?.setNavigationIcon(R.drawable.ic_chevron_left_write_24dp)
        mToolbar?.contentInsetStartWithNavigation = 10
        //默认为颜色
        var color: Int = colorId[0]
        try {
            //颜色资源ID
            color = resources.getColor(colorId[0])
        } catch (e: Exception) {
        }
        StatusBarUtil.setColor(mActivity, color, 1)
        mToolbar?.setBackgroundColor(color)

        /**
         * 初始化导航点击事件
         */
        initNavigationEvent()
    }

    /**
     * 初始化导航点击事件
     */
    protected fun initNavigationEvent() {
        mToolbar?.setNavigationOnClickListener { onNavigationClick() }

        //导航文字
        val navText = findViewById(R.id.toolbar_nav_text)
        navText?.setOnClickListener { onNavigationClick() }
    }


    /**
     *
     * 获取标题栏的高度设置，如果说哪个页面不一样，可以在子页面选择<p>
     * 重写这个方法，返回自己需要的值
     */
    protected fun getAppBarLayoutHeight(): Float {
        return 44f
    }

    /**
     * @param colorPrimaryId  必须不能为空
     * @param drawableColorPrimary
     * @param useDefaultGradient  是否使用默认渐变颜色
     */
    private fun initGradientToolbar(colorPrimaryId: Int,
                                    drawableColorPrimary: Int?,
                                    useDefaultGradient: Boolean = false) {
        mToolbar?.title = ""
        mToolbar?.setNavigationIcon(R.drawable.ic_chevron_left_write_24dp)
        mToolbar?.contentInsetStartWithNavigation = 10
        mToolbar?.setBackgroundColor(0x00000000)
        mToolbar?.background?.alpha = 0

        //大于 19    设置沉浸
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //获取状态栏
            val appBarLayout = findViewById(R.id.app_bar_layout) as? AppBarLayout?

            if (appBarLayout != null) {
                val params = appBarLayout.layoutParams as ViewGroup.MarginLayoutParams
                val statusBarHeight = StatusBarUtil.getStatusBarHeight(mActivity)
                //设置标题栏的高度
                params.height = DensityUtils.dp2px(mActivity, getAppBarLayoutHeight()) + statusBarHeight
                appBarLayout.layoutParams = params
                appBarLayout.setPadding(0, statusBarHeight, 0, 0)
                if (drawableColorPrimary == null) {
                    if (useDefaultGradient) {//默认颜色
                        appBarLayout.setBackgroundResource(R.drawable.color_primary)
                    } else {
                        try {
                            //颜色资源ID
                            appBarLayout.setBackgroundResource(colorPrimaryId)
                        } catch (e: Exception) {
                            //纯颜色
                            appBarLayout.setBackgroundColor(colorPrimaryId)
                        }
                    }
                } else {
                    appBarLayout.setBackgroundResource(drawableColorPrimary)
                }
            }
            StatusBarUtil.setTransparentForImageViewInFragment(this, null)
        } else {
            val color = resources.getColor(colorPrimaryId)
            StatusBarUtil.setColor(mActivity, color, 1)
            mToolbar?.setBackgroundColor(color)
        }

        /**
         * 初始化导航点击事件
         */
        initNavigationEvent()
    }

    /**
     * 标题栏
     * 设计成可变参数的原因是为了适配5.0以下，标题栏和状态栏不一致颜色的情况下也可以直接使用
     * @param colorPrimaryId 颜色ID
     * @param drawableColorPrimary 渐变的颜色
     */
    protected fun initToolbar(colorPrimaryId: Int, drawableColorPrimary: Int?) {
        initGradientToolbar(colorPrimaryId, drawableColorPrimary, true)
    }

    /**
     * 不适用渐变颜色，直接以colorPrimaryId作为全部颜色
     * @param colorPrimaryId 颜色
     */
    protected fun initToolbar(colorPrimaryId: Int) {
        initGradientToolbar(colorPrimaryId, null, false)
    }

    /**
     * 重装的toolbar 工具

     * @param navText 导航文字
     * *
     * @param title   标题
     * *
     * @param colorId 颜色
     */
    protected fun initToolbar(navText: String? = "",
                              title: String? = "",
                              colorId: Int,
                              drawableColorPrimary: Int?) {
        initToolbar(colorPrimaryId = colorId, drawableColorPrimary = drawableColorPrimary)

        //导航文字
        val tvNav = findViewById(R.id.toolbar_nav_text) as? TextView?

        tvNav?.let {
            tvNav.text = navText ?: ""
        }

        //标题栏中间的文字
        val tvTitle = findViewById(R.id.tv_toolbar_title) as? TextView?
        tvTitle?.let {
            tvTitle.text = title ?: ""
        }

    }

    /**
     * 设置标题
     */
    protected open fun setToolBarTitle(title: Int) {
        val tvTitle = findViewById(R.id.tv_toolbar_title) as? TextView?
        tvTitle?.let {
            tvTitle.setText(title)
        }
    }

    /**
     * 重装的toolbar 工具

     * @param title   标题
     * *
     * @param colorId 颜色
     */
    protected fun initToolbar(title: String = "",
                              colorId: Int,
                              drawableColorPrimary: Int?) {
        initToolbar(colorPrimaryId = colorId, drawableColorPrimary = drawableColorPrimary)

        //标题栏中间的文字
        val tvTitle = findViewById(R.id.tv_toolbar_title) as? TextView?
        tvTitle?.let {
            tvTitle.text = title
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
    protected fun showToast(msgText: String?) {
        T.showToast(mActivity, msgText)
    }

    /**
     * 显示资源文字信息

     * @param msgId
     */
    protected fun showToast(@StringRes msgId: Int) {
        T.showToast(mActivity, msgId)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        slideLeftOut()

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

    /**
     * 重启activity
     */
    protected fun reloadActivity() {
        val intent = intent
        overridePendingTransition(0, 0)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
    }
}
