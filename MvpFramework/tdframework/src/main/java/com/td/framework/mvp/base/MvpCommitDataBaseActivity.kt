package com.td.framework.mvp.base

import com.td.framework.mvp.contract.CommitDataContract

/**
 * Created by 江俊超 on 7/27/2017.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>专门用来提交数据基类</li>
 * @param T 返回数据类型
 * @param P presenter
 */
abstract class MvpCommitDataBaseActivity<out P> : MvpLoadingActivity<P>()
        , CommitDataContract.View {


    override fun handlerComplete(msg: String?) {
        super.handlerComplete(msg)
        dismissDialog()
    }

    override fun commitDataFail(msg: String?) {
        showMessageDialog(msg ?: "")

    }

    override fun onRetry() {
        showContent()
    }
}