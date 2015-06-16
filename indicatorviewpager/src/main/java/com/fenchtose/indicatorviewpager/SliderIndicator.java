package com.fenchtose.indicatorviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Jay Rambhia on 15/06/15.
 */
public class SliderIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {

    private final static int DEFAULT_INDICATOR_SIZE = 5;
    private static final String TAG = "Slider";

    private int mIndicatorWidth;
    private int mIndicatorHeight;
    private int mIndicatorMargin;

    private int mActiveRes;
    private int mInactiveRes;

    private View mCurrView;
    private int mCurrPos = 0;

    private ViewPager mPager;
    private boolean autoTransition = false;
    private Handler mHandler;
    private int TRANSITION_DELAY = 5000;

    public SliderIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public SliderIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SliderIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        getAttrs(context, attrs);

        mHandler = new Handler();

        if (isInEditMode()) {
            createIndicators(5);
        }
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        float density = context.getResources().getDisplayMetrics().density;

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator);
        mIndicatorWidth = mTypedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_width,
                (int)(density * DEFAULT_INDICATOR_SIZE));
        mIndicatorHeight = mTypedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_height,
                (int)(density * DEFAULT_INDICATOR_SIZE));
        mIndicatorMargin = mTypedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_margin,
                (int)(density * DEFAULT_INDICATOR_SIZE));

        mActiveRes = mTypedArray.getResourceId(R.styleable.CircleIndicator_ci_drawable_active,
                R.drawable.circle_yellow);
        mInactiveRes = mTypedArray.getResourceId(R.styleable.CircleIndicator_ci_drawable_inactive,
                R.drawable.circle_green);

        mTypedArray.recycle();
    }

    public void setViewPager(@NonNull ViewPager pager) {
        if (pager.getAdapter() == null) {
            throw new IllegalArgumentException("ViewPager has no adapter");
        }

        int size = pager.getAdapter().getCount();
        createIndicators(size);
        pager.addOnPageChangeListener(this);
        onPageSelected(mCurrPos);

        mPager = pager;
    }

    public void setAutoTransition(boolean status) {
        if (mPager == null) {
            throw new IllegalStateException("View Pager is not set");
        }

        autoTransition = status;
        startTransition();
    }

    public void configureIndicator(AttributeSet attrs) {
        getAttrs(getContext(), attrs);
    }

    private boolean startTransition() {
        if (autoTransition && mCurrPos < mPager.getAdapter().getCount() - 1) {
            mHandler.postDelayed(mTransitionRunnable, TRANSITION_DELAY);
            return true;
        }

        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        if (mCurrView != null) {
//            mCurrView.setAlpha(1 - positionOffset/2);
//        }

//        Log.i(TAG, "onPageScrolled: " + position + " " + positionOffset + " " + positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {

        View prevIndicator = getChildAt(mCurrPos);
        if (prevIndicator == null) {
            return;
        }

        prevIndicator.setBackgroundResource(mInactiveRes);

        mCurrView = getChildAt(position);
        if (mCurrView == null) {
            return;
        }

        mCurrView.setBackgroundResource(mActiveRes);

        mCurrPos = position;

        mHandler.removeCallbacks(mTransitionRunnable);
        if (autoTransition) {
            startTransition();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void createIndicators(int size) {
        removeAllViews();
        for (int i = 0; i < size; i++) {
            addIndicator();
        }
    }

    private void addIndicator() {
        View indicator = new View(getContext());
        indicator.setBackgroundResource(mInactiveRes);
        addView(indicator, mIndicatorWidth, mIndicatorHeight);

        LayoutParams params = (LayoutParams) indicator.getLayoutParams();
        params.leftMargin = mIndicatorMargin;
        params.rightMargin = mIndicatorMargin;
        indicator.setLayoutParams(params);
    }

    Runnable mTransitionRunnable = new Runnable() {
        @Override
        public void run() {
            mPager.setCurrentItem(mPager.getCurrentItem() + 1);
        }
    };

    public void setTransitionDelay(int delay) {
        TRANSITION_DELAY = delay;
    }
}
