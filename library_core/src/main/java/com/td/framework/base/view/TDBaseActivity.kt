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
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.td.framework.R
import com.td.framework.global.app.AppManager
import com.td.framework.global.app.Constant
import com.td.framework.global.helper.ThemHelper
import com.td.framework.ui.swipebacklayout.SwipeBackLayout
import com.td.framework.ui.swipebacklayout.app.SwipeBackActivity
import com.td.framework.utils.DensityUtils
import com.td.framework.utils.T
import com.td.framework.utils.amin.JumpAnimUtils
import com.td.framework.utils.statusbar.StatusBarModeUtil
import com.td.framework.utils.statusbar.StatusBarUtil
import com.trello.rxlifecycle2.android.ActivityEvent
import com.umeng.analytics.MobclickAgent
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import java.io.Serializable


/**
 * Created by jc on 2016/12/14 0014.
 *
 * 基类 活动
 * -------------------------------------
 * - ① 滑动返回
 * - ② EventBus
 * - ③ 友盟
 * - ④ Toolbar封装
 * -------------------------------------
 * -------------------------------------
 * 2018年3月7日12:36:45
 * 增加了 ThemHelper
 * -------------------------------------
 *
 * -------------------------------------
 * 2018年3月15日12:25:08
 * 修改了使用白色作为主色调的情况
 * -------------------------------------
 *
 * -------------------------------------
 * 2018年4月2日16:25:30
 * 增加了滑动的时候  添加遮罩
 * -------------------------------------
 *
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

    override protected fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)

        isCreate = true
        mAppManager.addOnStartActivity(this)
        if (useEventBus()) {
            try {
                EventBus.getDefault().register(this)
            } catch (e: Exception) {
            }
        }
        if (usSwipeBack()) initSwipeActivity()
    }

    //2018年3月7日增加
    override fun onStart() {
        super.onStart()
//        if (useEventBus() && isCreate) {
//            try {
//                EventBus.getDefault().register(this)
//            } catch (e: Exception) {
//            }
//        }
        if (isCreate && isInitToolBar()) {
            //初始化纯颜色的标题栏
            initToolbar(ThemHelper.getPrimaryColor(), null)
        }
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
    protected fun startActivityFromSharedElementsTransition(toClass: Class<*>,
                                                            intentData: Serializable?,
                                                            vararg sharedElements: Pair<View, String>) {

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
    protected open fun useEventBus(): Boolean {
        return false
    }

    /**
     * 是否自动初始化标题栏
     */
    protected open fun isInitToolBar(): Boolean {
        return true
    }

    /**
     * 发送事件对象
     */
    protected fun postEvent(event: Any) {
        //发送
        EventBus.getDefault().post(event)
    }

    /**
     * 是否使用灰色的状态栏
     */
    protected open fun useDarkStatusBar(): Boolean {
        // return true //太多了，不想在每个页面进行重写
        // 2018年3月15日12:26:15 进行了修改
        // 2018年3月16日14:53:20  直接更改为了是否使用灰色模式
        return ThemHelper.getPrimaryColor() == (0xffffffff.toInt())

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
     *
     * -----------------------------------
     * 2018年3月8日12:04:58
     * 修改为在values文件中进行配置
     * -----------------------------------
     */
    protected open fun getAppBarLayoutHeight(): Float {
        return resources.getDimension(R.dimen.AppBarLayoutHeight)
    }

    /**
     *-------------
     *- 判断能不能更改状态栏的颜色，
     *- 能修改，使用状态栏颜色，不
     *- 能修改，再直接使用颜色作为背景
    //-------------

     * @param colorPrimaryId  必须不能为空
     * @param drawableColorPrimary
     */
    private fun initToolbar(colorPrimaryId: Int,
                            drawableColorPrimary: Int?) {
        mToolbar?.title = ""
        if (useDarkStatusBar()) {// 使用灰色状态栏
            mToolbar?.setNavigationIcon(R.drawable.ic_chevron_left_gray_24dp)
            (findViewById(R.id.tv_toolbar_title) as? TextView)?.setTextColor((0xff333333).toInt())
            (findViewById(R.id.toolbar_nav_text) as? TextView)?.setTextColor((0xff333333).toInt())

        } else {
            mToolbar?.setNavigationIcon(R.drawable.ic_chevron_left_write_24dp)
        }
        mToolbar?.contentInsetStartWithNavigation = DensityUtils.dp2px(mActivity, 16f)/*返回菜单的边距*/
        mToolbar?.setBackgroundColor(0x00000000)
        mToolbar?.background?.alpha = 0

        //大于 19    设置沉浸
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //获取状态栏
            val appBarLayout = findViewById(R.id.app_bar_layout) as? AppBarLayout?
            if (appBarLayout != null) {
                //参数
                val params = appBarLayout.layoutParams as ViewGroup.MarginLayoutParams
                //高度
                val statusBarHeight = StatusBarUtil.getStatusBarHeight(mActivity)
                //设置标题栏的高度
                params.height = (getAppBarLayoutHeight() + statusBarHeight).toInt()
                //设置padding 不使用纯色的时候，padding去除
                appBarLayout.setPadding(0, statusBarHeight, 0, 0)
                //设置颜色模式
                StatusBarModeUtil.setStatusTextColor(useDarkStatusBar(), mActivity)
                if (!useDarkStatusBar()) {
                    StatusBarUtil.setTranslucentForImageView(mActivity, 0, null)
                }
                //布局参数的更改
                appBarLayout.layoutParams = params
                // 增加状态栏占位图 start
                (findViewById(R.id.base_root_toolbar) as? RelativeLayout?)?.apply {
                    val statusBarView = LinearLayout(mActivity)
                    //创建布局参数
                    val statusBarParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT)
                    //设置布局参数
                    statusBarView.layoutParams = statusBarParams

                    statusBarView.setBackgroundColor(ThemHelper.getPrimaryColor())
//                    statusBarView.setBackgroundColor(0xff03a4f9.toInt())
                    statusBarView.visibility = View.GONE
                    addView(statusBarView)
                    appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
                        //                        L.e("verticalOffset:$verticalOffset")

                        statusBarView.visibility = if (verticalOffset < -statusBarHeight) View.VISIBLE else View.GONE
                    }
                }
                // 增加状态栏占位图 end

                // 使用drawable類型的背景
                if (drawableColorPrimary == null) {
                    try {
                        //颜色资源ID
                        appBarLayout.setBackgroundResource(colorPrimaryId)
                    } catch (e: Exception) {
                        //纯颜色
                        appBarLayout.setBackgroundColor(colorPrimaryId)
                    }
                } else {
                    //使用渐变的颜色作为背景
                    appBarLayout.setBackgroundResource(drawableColorPrimary)
                }
            }
        } else {
//            val color = resources.getColor(colorPrimaryId)
            StatusBarUtil.setColor(mActivity, colorPrimaryId, 1)
            mToolbar?.setBackgroundColor(colorPrimaryId)
        }

        /**
         * 初始化导航点击事件
         */
        initNavigationEvent()
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
     * 设置标题
     */
    protected open fun setToolBarTitle(title: String) {
        val tvTitle = findViewById(R.id.tv_toolbar_title) as? TextView?
        tvTitle?.let {
            tvTitle.text = title
        }
    }

    /**
     * 菜单按钮点击
     */
    protected open fun onNavigationClick() {
        onBackPressed()
        slideLeftOut()
        System.gc()
    }

    /**
     * 右边划出
     */
    protected open fun slideLeftOut() {
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
                    .compose<T>(bindUntilEvent(ActivityEvent.DESTROY))
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
