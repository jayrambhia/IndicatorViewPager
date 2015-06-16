package com.fenchtose.indicatorviewpagersample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.fenchtose.indicatorviewpager.IndicatorViewPager;

/**
 * Created by Jay Rambhia on 16/06/15.
 */
public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_layout);
        IndicatorViewPager viewPager = (IndicatorViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager(), 8));
    }

    public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        private final int NUM_PAGES;

        public ScreenSlidePagerAdapter(FragmentManager fm, int pages) {
            super(fm);
            NUM_PAGES = pages;
        }

        @Override
        public Fragment getItem(int position) {
            return SampleFragment.newInstance();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
