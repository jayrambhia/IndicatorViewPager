package com.fenchtose.indicatorviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Jay Rambhia on 16/06/15.
 */
public class IndicatorViewPager extends LinearLayout {

    private final static int DEFAULT_VIEW_MARGIN = 25;
    private static final String TAG = "IndicatorViewPager";

    private int mViewMargin;

    private SliderViewPager mViewPager;
    private SliderIndicator mPageChangeListener;

    public IndicatorViewPager(Context context) {
        super(context);
        init(context, null);
    }

    public IndicatorViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public IndicatorViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        float density = context.getResources().getDisplayMetrics().density;
        mViewMargin = (int)(density * DEFAULT_VIEW_MARGIN);

        if (attrs != null) {
            TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.IndicatorViewPager);
            mViewMargin = mTypedArray.getDimensionPixelSize(R.styleable.IndicatorViewPager_vp_margin,
                    mViewMargin);
            mTypedArray.recycle();
        }

        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_indicator_viewpager,
                this, true);

        mViewPager = (SliderViewPager)rootView.findViewById(R.id.sliderviewpager);
        mPageChangeListener = (SliderIndicator)rootView.findViewById(R.id.indicators);
        mPageChangeListener.configureIndicator(attrs);

//        addView(mViewPager);
        LayoutParams params = (LayoutParams) mViewPager.getLayoutParams();
        params.bottomMargin = mViewMargin;
        mViewPager.setLayoutParams(params);

//        mPageChangeListener = new SlidePageChangeListener(context, attrs);
//        addView(mPageChangeListener);
    }

    public void setAdapter(PagerAdapter adapter) {
        mViewPager.setAdapter(adapter);
        mPageChangeListener.setViewPager(mViewPager);
        mPageChangeListener.setAutoTransition(true);
    }

    public void setPageTransformer(boolean status, ViewPager.PageTransformer pageTransformer) {
        mViewPager.setPageTransformer(status, pageTransformer);
    }

    public int getCurrentItem() {
        return  mViewPager.getCurrentItem();
    }

    public void setCurrentItem(int position) {
        mViewPager.setCurrentItem(position);
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    public void setScrollFactor(double scrollFactor) {
        mViewPager.setScrollerDurationFactor(scrollFactor);
    }

    public void setAutoTransition(boolean status) {
        mPageChangeListener.setAutoTransition(status);
    }

    public void setTransitionDelay(int delay) {
        mPageChangeListener.setTransitionDelay(delay);
    }
}
