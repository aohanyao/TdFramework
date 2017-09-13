package com.td.framework.global;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.td.framework.R;

/**
 * <p>作者：江俊超 on 2016/9/6 17:07</p>
 * <p>邮箱：928692385@qq.com</p>
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
        initAdapter(mContext, mAdapter, mRecyclerView,LinearLayoutManager.VERTICAL);
    }

    /**
     * 水平的
     *
     * @param mContext
     * @param mAdapter
     * @param mRecyclerView
     */
    public static void initAdapterHorizontal(Context mContext, BaseQuickAdapter mAdapter, RecyclerView mRecyclerView) {
        initAdapter(mContext, mAdapter, mRecyclerView, LinearLayoutManager.HORIZONTAL);
    }

    private static void initAdapter(Context mContext, BaseQuickAdapter mAdapter, RecyclerView mRecyclerView, int orientation) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(orientation);
        mRecyclerView.setLayoutManager(layoutManager);
        View emptyView = LayoutInflater.from(mContext).inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        mAdapter.setEmptyView(emptyView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

    }
}
