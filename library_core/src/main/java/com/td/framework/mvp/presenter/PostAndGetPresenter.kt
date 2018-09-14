package com.td.framework.mvp.presenter

import com.td.framework.mvp.contract.PostAndGetContract
import com.td.framework.mvp.model.BaseParamsInfo

/**
 * Created by jc on 2018-03-23.
 * Version:1.0
 * Description: 获取和提交数据P
 * ChangeLog:
 */

abstract class PostAndGetPresenter<PT, in GP : BaseParamsInfo, in PP : BaseParamsInfo>
(view: PostAndGetContract.View<PT>) :
        PostAndGetContract.Presenter<PostAndGetContract.View<PT>, PT, GP, PP>(view) {
}
