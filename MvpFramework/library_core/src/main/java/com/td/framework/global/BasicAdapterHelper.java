package com.td.framework.global;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.td.framework.R;

/**
 * <p>作者：jc on 2016/9/6 17:07</p>
 * <p>适配器帮助类，统一初始化适配器</p>
 */
public class BasicAdapterHelper {
    /**
     * 垂直的
     *
     * @param mContext
     * @param mAdapter
     * @param mRecyclerView
     */
    public static void initAdapterVertical(Context mContext, BaseQuickAdapter mAdapter, RecyclerView mRecyclerView) {
        initAdapter(mContext, mAdapter, mRecyclerView, LinearLayoutManager.VERTICAL, "");
    }

    public static void initAdapterVertical(Context mContext, BaseQuickAdapter mAdapter, RecyclerView mRecyclerView, String emptyTip) {
        initAdapter(mContext, mAdapter, mRecyclerView, LinearLayoutManager.VERTICAL, emptyTip);
    }

    /**
     * 水平的
     *
     * @param mContext
     * @param mAdapter
     * @param mRecyclerView
     */
    public static void initAdapterHorizontal(Context mContext, BaseQuickAdapter mAdapter, RecyclerView mRecyclerView) {
        initAdapter(mContext, mAdapter, mRecyclerView, LinearLayoutManager.HORIZONTAL, "");
    }

    private static void initAdapter(Context mContext, BaseQuickAdapter mAdapter, RecyclerView mRecyclerView, int orientation, String emptyTip) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(orientation);
        mRecyclerView.setLayoutManager(layoutManager);
        View emptyView = LayoutInflater.from(mContext).inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        TextView tvEmptyTip = (TextView) emptyView.findViewById(R.id.tv_empty_tip);
        if (!TextUtils.isEmpty(emptyTip) && tvEmptyTip != null) {
            tvEmptyTip.setText(emptyTip);
        }
        mAdapter.setEmptyView(emptyView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

    }
}
