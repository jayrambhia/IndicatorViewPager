package com.fenchtose.indicatorviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by Jay Rambhia on 16/06/15.
 */
public class SliderViewPager extends ViewPager {

    private ScrollerCustomDuration mScroller;

    private static final String TAG = "SliderViewPager";

    public SliderViewPager(Context context) {
        super(context);
        init(context, null);
    }

    public SliderViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        try {
            Field scroller = ViewPager.class.getDeclaredField("mScroller");
            scroller.setAccessible(true);

            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            mScroller = new ScrollerCustomDuration(context,
                    (Interpolator)interpolator.get(null));
            mScroller.setScrollDurationFactor(2);
            scroller.set(this, mScroller);

        } catch (NoSuchFieldException e) {
            Log.e(TAG, "no such field exists");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = 0;
        for (int i=0; i<getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();

            if (h > height) {
                height = h;
            }
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setScrollerDurationFactor(double scrollFactor) {
        mScroller.setScrollDurationFactor(scrollFactor);
    }

    /**
     * http://stackoverflow.com/a/14179739/891373
     */
    private class ScrollerCustomDuration extends Scroller {

        private double mScrollFactor = 1;

        public ScrollerCustomDuration(Context context) {
            super(context);
        }

        public ScrollerCustomDuration(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public void setScrollDurationFactor(double scrollFactor) {
            mScrollFactor = scrollFactor;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, (int) (duration * mScrollFactor));
        }
    }

}
