package com.td.framework.expand

import com.td.framework.base.view.TDBaseActivity
import com.td.framework.base.view.TDBaseFragment
import com.td.framework.global.app.Constant
import com.td.framework.global.router.RouterBasePath

/**
 * Created by 江俊超 on 8/4/2017.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>单独只在一些地方使用的拓展</li>
 */
fun TDBaseActivity.openSelectItem(title: String, types: String, requestCode: Int) {

    navigationActivityFromPairForResult(RouterBasePath.SelectItem, requestCode, "title" to title, Constant.IDK to types)
}
fun TDBaseFragment.openSelectItem(title: String
                                  , types: String
                                  , requestCode: Int) {

    navigationActivityFromPairForResult(RouterBasePath.SelectItem, requestCode, "title" to title, Constant.IDK to types)
}
