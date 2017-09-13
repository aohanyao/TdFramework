package com.td.framework.ui.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class PopWindow extends PopupWindow {
    public PopWindow(Context mContext, View view) {
        super(mContext);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(0x44000000));
        //this.setAnimationStyle(R.style.popwin_anim_style);
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        update();
    }
}