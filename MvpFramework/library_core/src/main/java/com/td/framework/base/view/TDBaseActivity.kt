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
import io.reactivex.disposables.Disposable
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
        if (usSwipeBack()) initSwipActivity()
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

    protected fun usSwipeBack(): Boolean {
        return true
    }

    protected fun initSwipActivity() {
        mSwipeBackLayout = swipeBackLayout
        mSwipeBackLayout!!.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
    }

    override fun getResources(): Resources {
        val res = super.getResources()
        val config = Configuration()
        config.fontScale = 1.0f
        //设置字体的大小不会变化， 不会随着 系统字体的更改而变化
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
        val color = resources.getColor(colorId[0])
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
     * 标题栏
     * 设计成可变参数的原因是为了适配5.0以下，标题栏和状态栏不一致颜色的情况下也可以直接使用
     */
    protected fun initChangerToolbarGradientColor(vararg colorId: Int) {
        mToolbar?.title = ""
        mToolbar?.setNavigationIcon(R.drawable.ic_chevron_left_write_24dp)
        mToolbar?.contentInsetStartWithNavigation = 10
        mToolbar?.setBackgroundColor(0x00000000)
        mToolbar?.background?.alpha = 0
        if (colorId.isNotEmpty()) {
            //19以上，直接使用渐变背景
            //大于 19     故意设置的 UI要的渐进变化的状态栏和 标题栏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val appBarLayout = findViewById(R.id.app_bar_layout) as? AppBarLayout?

                if (appBarLayout != null) {
                    val params = appBarLayout.layoutParams as ViewGroup.MarginLayoutParams
                    val statusBarHeight = StatusBarUtil.getStatusBarHeight(mActivity)
                    params.height = DensityUtils.dp2px(mActivity, 44f) + statusBarHeight
                    appBarLayout.layoutParams = params
                    appBarLayout.setPadding(0, statusBarHeight, 0, 0)
                    appBarLayout.setBackgroundResource(R.drawable.color_primary)
                }
                StatusBarUtil.setTransparentForImageViewInFragment(this, null)
            } else {
                //                mToolbar.setBackgroundResource(R.drawable.color_primary);
                val color = resources.getColor(colorId[0])
                StatusBarUtil.setColor(mActivity, color, 1)
                mToolbar?.setBackgroundColor(color)
            }

        }
        /**
         * 初始化导航点击事件
         */
        initNavigationEvent()
    }

    /**
     * 重装的toolbar 工具

     * @param navText 导航文字
     * *
     * @param title   标题
     * *
     * @param colorId 颜色
     */
    protected fun initChangerToolbarGradientColor(navText: String?, title: String?, vararg colorId: Int) {
        initChangerToolbarGradientColor(*colorId)

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
     * 重装的toolbar 工具

     * @param title   标题
     * *
     * @param colorId 颜色
     */
    protected fun initChangerToolbarGradientColor(title: String, vararg colorId: Int) {
        initChangerToolbarGradientColor(*colorId)

        //标题栏中间的文字
        val tvTitle = findViewById(R.id.tv_toolbar_title) as? TextView?
        tvTitle?.let {
            tvTitle.text = title ?: ""
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

}
