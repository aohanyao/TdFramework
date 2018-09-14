package com.td.framework.ui.viewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.td.framework.ui.R;
import com.td.framework.utils.DensityUtils;

import java.util.List;

/**
 * create by jc  in 2018年3月19日11:46:04
 * 包裹文字内容的指示器
 * <p>不能大于整个屏幕。暂时</p>
 */
public class WarpTitleViewPagerIndicator extends LinearLayout {
    /**
     * 绘制线条的画笔
     */
    private Paint mPaint;

    /**
     * 线条的宽度
     */
    private int mItemWidth = DensityUtils.dp2px(getContext(), 45f);
    /**
     * 线条的高度
     */
    private int mTabIndicatorHeight = DensityUtils.dp2px(getContext(), 2.5f);
    /**
     * 手指滑动时的偏移量
     */
    private float mTranslationX;
    /**
     * 标题的左右间距
     */
    private int mTitleLeftAndRightMargin = DensityUtils.dp2px(getContext(), 15f);

    /**
     * 默认的Tab数量
     */
    private static final int COUNT_DEFAULT_TAB = 4;
    /**
     * tab数量
     */
    private int mTabIndicatorCount = COUNT_DEFAULT_TAB;

    /**
     * 与之绑定的ViewPager
     */
    public ViewPager mViewPager;

    /**
     * 标题正常时的颜色
     */
    private int mTabTextColor = 0xFF999999;
    /**
     * 标题选中时的颜色
     */
    private int mTabSelectedTextColor = 0xFF30babf;
    /**
     * 默认的字体大小
     */
    private int mTabIndicatorTextSize = 14;

    public WarpTitleViewPagerIndicator(Context context) {
        this(context, null);
    }

    public WarpTitleViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 获得自定义属性，tab的数量
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.WarpTitleViewPagerIndicator);
        //item的数量
        mTabIndicatorCount = mTypedArray.getInt(R.styleable.WarpTitleViewPagerIndicator_tabIndicatorCount,
                COUNT_DEFAULT_TAB);
        if (mTabIndicatorCount < 0)
            mTabIndicatorCount = COUNT_DEFAULT_TAB;
        //字体大小
        mTabIndicatorTextSize = (int) mTypedArray.getDimension(R.styleable.WarpTitleViewPagerIndicator_tabIndicatorTextSize
                , mTabIndicatorTextSize);

        //正常颜色
        mTabTextColor = mTypedArray.getColor(R.styleable.WarpTitleViewPagerIndicator_tabTextColor,
                mTabTextColor);

        //选中的颜色
        mTabSelectedTextColor = mTypedArray.getColor(R.styleable.WarpTitleViewPagerIndicator_tabSelectedTextColor,
                mTabSelectedTextColor);

        //指示器高度
        mTabIndicatorHeight = mTypedArray.getColor(R.styleable.WarpTitleViewPagerIndicator_WarpTabIndicatorHeight,
                mTabIndicatorHeight);
        mTypedArray.recycle();

        // 初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mTabSelectedTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mItemWidth = (getWidth() / mTabIndicatorCount);
    }

    /**
     * 绘制指示器
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {

        canvas.save();
        // 画笔平移到正确的位置
        canvas.translate(mTranslationX, getHeight() - mTabIndicatorHeight);
        //包裹文字，和文字一样的大小
        canvas.drawRect(mTitleLeftAndRightMargin, 0, mItemWidth - mTitleLeftAndRightMargin, mTabIndicatorHeight, mPaint);
        canvas.restore();

        super.dispatchDraw(canvas);
    }


    /**
     * 设置可见的tab的数量
     *
     * @param count 可见数量
     */
    public void setVisibleTabCount(int count) {
        this.mTabIndicatorCount = count;
    }

    /**
     * 设置tab的标题内容 可选，可以自己在布局文件中写死
     *
     * @param tabTitles
     */
    public void setTabItemTitles(List<String> tabTitles) {
        // 如果传入的list有值，则移除布局文件中设置的view
        if (tabTitles != null && tabTitles.size() > 0) {
            this.removeAllViews();
            for (int i = 0; i < tabTitles.size(); i++) {
                String title = tabTitles.get(i);
                // 添加view
                addView(generateTextView(title, i));
            }
            // 设置item的click事件
            setItemClickEvent();
        }

    }


    // 设置关联的ViewPager
    public void setViewPager(ViewPager mViewPager, int pos) {
        this.mViewPager = mViewPager;


        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // 设置字体颜色高亮
                resetTextViewColor();
                highLightTextView(position);

            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                // 滚动
                scroll(position, positionOffset);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // 设置当前页
        mViewPager.setCurrentItem(pos);
        // 高亮
        highLightTextView(pos);
    }

    /**
     * 高亮文本
     *
     * @param position 下标
     */
    protected void highLightTextView(int position) {
        View view = getChildAt(position);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(mTabSelectedTextColor);
        }

    }

    /**
     * 重置文本颜色
     */
    private void resetTextViewColor() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(mTabTextColor);
            }
        }
    }


    /**
     * 设置点击事件
     */
    private void setItemClickEvent() {
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            final int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }

    /**
     * 根据标题生成我们的TextView
     *
     * @param text     文本内容
     * @param position 下标
     * @return 生成后的TextView
     */
    private TextView generateTextView(String text, int position) {
        TextView itemView = new TextView(getContext());
        LayoutParams lp = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        itemView.setGravity(Gravity.CENTER);
        itemView.setPadding(mTitleLeftAndRightMargin, 0, mTitleLeftAndRightMargin, 0);
        //第一个 默认选中
        itemView.setTextColor(position == 0 ? mTabSelectedTextColor : mTabTextColor);
        itemView.setText(text);
        itemView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabIndicatorTextSize);
        itemView.setLayoutParams(lp);
        // 使用Android默认的背景选择
        itemView.setBackgroundResource(R.drawable.abc_item_background_holo_dark);
        return itemView;
    }


    /**
     * 指示器跟随手指滚动，以及容器滚动
     *
     * @param position
     * @param offset
     */
    public void scroll(int position, float offset) {
        /**
         * <pre>
         *  0-1:position=0 ;1-0:postion=0;
         * </pre>
         */


        // 不断改变偏移量，invalidate
//        float leftMargin = (position + offset) * mTitleLeftAndRightMargin;
        mTranslationX = (mItemWidth * (position + offset));
        float leftMargin = position * mTitleLeftAndRightMargin;
//        mTranslationX = mItemWidth * position /*+ leftMargin*/;

        int tabWidth = mItemWidth;

        // 容器滚动，当移动到倒数最后一个的时候，开始滚动
//        if (offset > 0 && position >= (mTabIndicatorCount - 2)
//                && getChildCount() > mTabIndicatorCount) {
//            //总共滚动的tab宽度
//            int offsetTabWidth = (int) (tabWidth * offset);
//            //大于1的情况
//            if (mTabIndicatorCount != 1) {
//                int what = (position - (mTabIndicatorCount - 2)) * tabWidth;
////                this.scrollTo(what + offsetTabWidth, 0);
//                mTranslationX += mTitleLeftAndRightMargin;
//            } else
//            // 为count为1时 的特殊处理
//            {
////                this.scrollTo(position * tabWidth + offsetTabWidth, 0);
//            }
//        }

        invalidate();
    }

    /**
     * 设置布局中view的一些必要属性；如果设置了setTabTitles，布局中view则无效
     */
    @Override
    protected void onFinishInflate() {
        Log.e("TAG", "onFinishInflate");
        super.onFinishInflate();

        int cCount = getChildCount();

        if (cCount == 0)
            return;

        for (int i = 0; i < cCount; i++) {
            View view = getChildAt(i);
            LayoutParams lp = (LayoutParams) view
                    .getLayoutParams();
            lp.weight = 0;
            lp.width = getWidth() / mTabIndicatorCount;
            view.setLayoutParams(lp);
        }
        // 设置点击事件
        setItemClickEvent();
        //重置颜色
        resetTextViewColor();
        //设置第一个为选中
        highLightTextView(0);
    }

}
