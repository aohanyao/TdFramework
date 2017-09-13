package com.td.framework.moudle.share;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.td.framework.R;

import java.util.List;

/**
 * Created by jjc on 2017/4/16.
 * <p>分享条目适配器</p>
 */

public class ShareListAdapter extends BaseQuickAdapter<ShareInfo,BaseViewHolder> {
    public ShareListAdapter(List<ShareInfo> data) {
        super(R.layout.list_share_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShareInfo item) {
        helper.addOnClickListener(R.id.ll_share);
        helper.setText(R.id.tv_share_text,item.getName());
        helper.setImageResource(R.id.iv_share_img,item.getImageId());
    }
}
