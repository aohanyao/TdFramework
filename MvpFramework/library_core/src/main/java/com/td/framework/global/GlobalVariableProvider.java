package com.td.framework.global;

import com.td.framework.global.app.App;
import com.td.framework.global.app.Constant;
import com.td.framework.utils.SPUtils;
import com.tencent.smtt.sdk.CookieManager;

/**
 * Created by jc on 7/24/2017.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>全局变量提供者</li>
 */
@Deprecated()
public class GlobalVariableProvider {
    /**
     * 用户信息
     */
//    private static UserInformation USER_INFO;


    private GlobalVariableProvider() {
    }

    //----------------------------------------用户信息start


    /**
     * 重置用户信息
     */
//    public static void resetUserInfo() {
//        USER_INFO = null;
//    }


    /**
     * 提供用户信息
     * <p>从私有目录中反序列化信息</p>
     *
     * @return
     */
//    public static UserInformation getUserInfo() {
//        if (USER_INFO == null) {
//            synchronized (GlobalVariableProvider.class) {
//                USER_INFO = new SerializableUtil<UserInformation>(App.newInstance())
//                        .getSerializable(Constant.USER_INFO);
//            }
//        }
//        return USER_INFO;
//    }

//    /**
//     * 替换本地信息
//     * <p>修改用户信息的时候</p>
//     */
//    public static void saveUserInfo(UserInformation infoMationBean) {
//        SerializableUtil<UserInformation> userInfoSerializableUtil = new SerializableUtil<>(App.newInstance());
//        userInfoSerializableUtil.clearSerializable(Constant.USER_INFO);
//        userInfoSerializableUtil.saveSerializableObject(infoMationBean, Constant.USER_INFO);
//        USER_INFO = null;
//    }

    //----------------------------------------用户信息end


    //----------------------------------------项目信息start

    /**
     * 这里返回项目ID
     *
     * @return
     */
    public static int getProjectId() {
        int projectId = (int) SPUtils.get(App.newInstance(), Constant.PROJECT_ID, -1);
//        if (projectId == -1) {
//            //登陆失效
//            T.showToast(App.newInstance(), "登陆失效，请重新登陆!");
//            RouterHelper.INSTANCE.navigationActivity(RouterUserPath.Entrance.Login);
//        }
        return projectId;
    }

    /**
     * 项目名称
     *
     * @return
     */
    public static String getProjectName() {
        String projectId = (String) SPUtils.get(App.newInstance(), Constant.PROJECT_NAME, "");
        return projectId;
    }

    /**
     * 项目图片
     *
     * @return
     */
    public static String getProjectImg() {
        String projectId = (String) SPUtils.get(App.newInstance(), Constant.PROJECT_IMG, "");
        return projectId;
    }

    /**
     * 保存项目信息
     *
     * @param projectId   项目ID
     * @param projectName 项目名称
     * @param projectImg  项目图片
     */
    public static void saveProjectNews(int projectId, String projectName, String projectImg) {
        SPUtils.put(App.newInstance(), Constant.PROJECT_ID, projectId);
        SPUtils.put(App.newInstance(), Constant.PROJECT_NAME, projectName);
        SPUtils.put(App.newInstance(), Constant.PROJECT_IMG, projectImg);
    }
    //----------------------------------------项目信息end


    /**
     * 退出登陆之后清除本地的一些信息
     */
    public static void loginOutClearCache() {
//        resetUserInfo();
        //清除用户信息
//        new SerializableUtil<UserInformation>(App.newInstance()).clearSerializable(Constant.USER_INFO);
        //清除X5浏览器信息
        CookieManager.getInstance().removeAllCookie();

        //清除项目信息
        SPUtils.remove(App.newInstance(), Constant.PROJECT_ID);
        SPUtils.remove(App.newInstance(), Constant.PROJECT_NAME);
        SPUtils.remove(App.newInstance(), Constant.PROJECT_IMG);
    }
}
