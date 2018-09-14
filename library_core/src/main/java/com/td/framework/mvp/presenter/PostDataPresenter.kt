package com.td.framework.mvp.presenter

import com.td.framework.mvp.contract.PostContract
import com.td.framework.mvp.model.BaseParamsInfo

/**
 * Created by jc on 2018年3月15日11:05:04
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>提交数据的基类P</li>
 * @param RP 请求参数类型
 */
abstract class PostDataPresenter<in RP : BaseParamsInfo>(view: PostContract.View)
    : PostContract.Presenter<PostContract.View, RP>(view)