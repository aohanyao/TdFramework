package com.td.framework.global.helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.td.framework.R;
import com.td.framework.ui.dialog.CustomBottomSheetDialog;
import com.td.framework.utils.L;
import com.td.framework.utils.T;

import java.io.File;

/**
 * Created by 江俊超 on 2017/4/12 0012.
 * <p>版本:1.0.0</p>
 * <b>说明<b>地图导航帮助类<br/>
 * <li>直接导航到华强文化创意公园</li>
 */
public class NavigationMapHelper {
    private Activity mActivity;
    private CustomBottomSheetDialog mBottomSheetDialog;
    //http://ditu.amap.com/?p=B0FFGXSM68,22.736674,113.957220,
    private double mEndLat = 22.736674;
    private double mEndLng = 113.957220;
    private String mAddress = "";

    public NavigationMapHelper(Activity mActivity) {
        this.mActivity = mActivity;
    }

    //判断是否安装目标应用
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName)
                .exists();
    }

    /**
     * 导航
     */
    public void navigationMap(double lat, double lng, String address) {
        this.mEndLat = lat;
        this.mEndLng = lng;
        this.mAddress = address;
        //1.两个地图都安装了，让用户选择
        boolean installBaidu = isInstallByread("com.baidu.BaiduMap");
        boolean installAmap = isInstallByread("com.autonavi.minimap");
        if (installBaidu && installAmap) {//两个地图都安装了 让用户进行选择
            showSelectMap();
        } else if (installBaidu) {//安装了百度地图
            startBaiduMap();
        } else if (installAmap) {//安装了高德地图
            startAmapMap();
        } else {//两个地图都没安装，使用web Api进行导航
            //startWebMap();
            T.showToast(mActivity, "您的手机未安装高德地图或者百度地图");
        }
    }


    /**
     * 使用webView 加载地图
     */
    private void startWebMap() {
//        String AmapUrl = "http://uri.amap.com/navigation?from,startpoint&to=" + mEndLng + "," + mEndLat + ",endpoint&&mode=car&policy=1&src=mypage&coordinate=gaode&callnative=0";
//        String url = "http://api.map.baidu.com/direction?" +
//                "destination=latlng:" + mEndLat + "," + mEndLng +
//                "|name:" +
//                "&mode=walking" +
//                "&region=深圳" +
//                "&output=html" +
//                "&src=唐豆科技|华强创意公园#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";
//        X5WebViewActivity.openHtml("华强文化创意产业园", url, mActivity, 0);
    }

    /**
     * 显示选择地图
     */
    private void showSelectMap() {
        mBottomSheetDialog = new CustomBottomSheetDialog(mActivity);
        View mSelectMapRootView = LayoutInflater.from(mActivity).inflate(R.layout.layout_map_selection, null);


        TextView tvAmapMap = (TextView) mSelectMapRootView.findViewById(R.id.tv_amap_map);
        TextView tvBaiduMap = (TextView) mSelectMapRootView.findViewById(R.id.tv_baidu_map);
        TextView tvCancel = (TextView) mSelectMapRootView.findViewById(R.id.tv_cancel);

        //高德地图
        tvAmapMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
                startAmapMap();
            }
        });
        //百度地图
        tvBaiduMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
                startBaiduMap();
            }
        });

        //取消按钮
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });

        mBottomSheetDialog.setContentView(mSelectMapRootView);
        try {
            mBottomSheetDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 唤起高德地图
     */
    private void startAmapMap() {
        try {
            Intent intent = new Intent("android.intent.action.VIEW",
                    android.net.Uri.parse("androidamap://route?sourceApplication=softname" +
                            "&dlat=" + mEndLat +
                            "&dlon=" + mEndLng +
                            "&dname=" +mAddress+
                            "&dev=0&t=2"));
            intent.setPackage("com.autonavi.minimap");
            mActivity.startActivity(intent);
        } catch (Exception e) {
            T.showToast(mActivity, "调起高德地图失败");
            if (L.isDebug) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭弹窗
     */
    private void dismissDialog() {
        try {
            mBottomSheetDialog.dismiss();
        } catch (Exception e) {
        }
    }


    /**
     * 移动APP调起Android百度地图方式
     */
    private void startBaiduMap() {
        try {
            Intent intent = new Intent();
            intent.setData(Uri.parse("baidumap://map/direction?origin=name:"+mAddress+"|" +
                    "latlng:" + mEndLat + "," + mEndLng + "&mode=driving&sy=3&index=0&target=1"));
            mActivity.startActivity(intent); // 启动调用
        } catch (Exception e) {
            T.showToast(mActivity, "调起百度地图失败");
            if (L.isDebug) {
                e.printStackTrace();
            }
        }

    }

}
