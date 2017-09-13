package com.td.framework.global.router;

/**
 * Created by 江俊超 on 2017/6/30 0030.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>招商相关的路由</li>
 */
public interface RouterMerchantsPath {

    /**
     * 客户
     */
    interface Customer {
        /**
         * 客户分析
         */
        String CustomerInput = "/Customer/CustomerInput";
        /**
         * 新建行动  对客户的行动
         */
        String NewAction = "/Customer/NewAction";
        /**
         * 客户排行
         */
        String Ranking = "/Customer/Ranking";
        /**
         * 通讯录导入客户
         */
        String  ContactsImport = "/Customer/ContactsImport";

        /**
         * 客户详情
         */
        String  CustomerDetails = "/Customer/CustomerDetails";
    }

    /**
     * 联系人
     */
    interface Contact{
        /**
         * 联系人详情
         */
        String ContactDetails="/Contact/ContactDetails";
        /**
         * 联系人列表
         */
        String  ContactsList = "/Contact/ContactsList";
    }

    /**
     * 订单
     */
    interface Order {

        /**
         * 新建订单
         */
        String NewOrder = "/Order/NewOrder";
    }
}
