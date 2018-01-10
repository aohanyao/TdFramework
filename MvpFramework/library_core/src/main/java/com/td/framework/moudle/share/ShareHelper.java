package com.td.framework.moudle.share;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.td.framework.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jc on 2017/3/1 0001.
 * <p>版本:1.0.0</p>
 * <b>说明<b>自定义的dialog帮助类<br/>
 * <li></li>
 */
public class ShareHelper {
    private Activity mContext;
    private AlertDialog mAlertDialog;
    private ShareListAdapter mAdapter;
    private List<ShareInfo> mDatas;

    public static ShareHelper newInstance(Activity mContext) {
        return new ShareHelper(mContext);
    }

    private ShareHelper(Activity mContext) {
        this.mContext = mContext;
        initData();
    }

    private void initData() {
        mDatas = new ArrayList<>();

        mDatas.add(new ShareInfo(R.mipmap.share_firend_icon, "微信朋友圈"));
        mDatas.add(new ShareInfo(R.mipmap.share_wechat_icon, "微信好友"));
        mDatas.add(new ShareInfo(R.mipmap.share_weibo_icon, "新浪微博"));
        mDatas.add(new ShareInfo(R.mipmap.share_qzone_icon, "QQ空间"));
        mDatas.add(new ShareInfo(R.mipmap.share_qq_icon, "QQ好友"));
    }

    public void showDialog(String mMsg) {
        showDialog();
    }

    public void showDialog(String mTitle, String mMsg, String mConfirmText) {
        showDialog();
    }

    public void showDialog() {
        View mDialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_share_layout, null);

        RecyclerView rvShare = (RecyclerView) mDialogView.findViewById(R.id.rv_share);
        rvShare.setLayoutManager(new GridLayoutManager(mContext, 3));
        mAdapter = new ShareListAdapter(mDatas);

        rvShare.setAdapter(mAdapter);
        rvShare.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                destry();
            }
        });
        mAlertDialog = new AlertDialog.Builder(mContext, R.style.DialogStyle)
                .setView(mDialogView)
                .setCancelable(true).create();
        mAlertDialog.show();
    }

    public void destry() {
        try {
            mAlertDialog.dismiss();
        } catch (Exception e) {
        }
    }

}
