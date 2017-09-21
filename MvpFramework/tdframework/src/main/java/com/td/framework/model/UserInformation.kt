package com.td.framework.model

import java.io.Serializable

/**
 * Created by 江俊超 on 2017/7/20 0020.
 *
 * 版本:1.0.0
 * **说明**<br></br>
 *  * 用户信息
 **** */
class UserInformation : Serializable {
    /**
     * id : 1
     * loginName : admin
     * memberNo : 1234567890
     * userNo : 1234567890
     * realName : 李雷
     * nickName : null
     * companyId : 1
     * userType : 1
     * userImg : null
     */
    var id: Int = 0
    var loginName: String = ""
    var memberNo: String = ""
    var userNo: String = ""
    var realName: String? = ""
    var nickName: String = ""
    var companyId: Int = 0
    var userType: Int = 0
    var userImg: String? = ""

}
