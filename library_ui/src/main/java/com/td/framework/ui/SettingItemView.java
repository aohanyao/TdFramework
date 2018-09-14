package com.td.framework.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



/**
 * <p>作者：jc on 2016/8/9 15:36</p>
 * <p></p>
 */
public class SettingItemView extends LinearLayout {
    private ImageView mLeftIcon;
    private ImageView mRightArrow;
    private TextView mLineText;
    private TextView mRightText;
    private View mTopLine, mBottomLine;

    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttr(attrs, context);
    }

    /**
     * 初始化视图组件
     *
     * @param context
     */
    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_setting_item, this);
        mLeftIcon = (ImageView) view.findViewById(R.id.iv_left_icon);
        mRightArrow = (ImageView) view.findViewById(R.id.iv_right_arrow);
        mLineText = (TextView) view.findViewById(R.id.tv_line_text);
        mRightText = (TextView) view.findViewById(R.id.tv_right_text);
        mTopLine = view.findViewById(R.id.v_top_line);
        mBottomLine = view.findViewById(R.id.v_bottom_line);
       // setBackgroundResource(R.drawable.setting_item_selector);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return true;
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return true;
//    }

    /**
     * 获取一些参数
     *
     * @param attrs
     * @param context
     */
    private void initAttr(AttributeSet attrs, Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView);
        String lineText = typedArray.getString(R.styleable.SettingItemView_line_text);
        int leftIconId = typedArray.getResourceId(R.styleable.SettingItemView_left_icon, 0);
        boolean rightArrowIconVisible = typedArray.getBoolean(R.styleable.SettingItemView_right_arrow_visible, true);
        boolean topLine = typedArray.getBoolean(R.styleable.SettingItemView_top_line_visible, true);
        boolean bottomLine = typedArray.getBoolean(R.styleable.SettingItemView_bottom_line_visible, true);
        if (leftIconId == 0) {
            mLeftIcon.setVisibility(GONE);
        } else {
            if (mLeftIcon != null) {
                mLeftIcon.setVisibility(VISIBLE);
                mLeftIcon.setImageResource(leftIconId);
            }
        }
        int bg = typedArray.getResourceId(R.styleable.SettingItemView_background,R.drawable.setting_item_selector);
       setBackgroundResource(bg);
        mLineText.setText(lineText);
        mTopLine.setVisibility(topLine ? VISIBLE : GONE);
        mBottomLine.setVisibility(bottomLine ? VISIBLE : GONE);
        mRightArrow.setVisibility(rightArrowIconVisible ? VISIBLE : GONE);
        typedArray.recycle();
    }

}
