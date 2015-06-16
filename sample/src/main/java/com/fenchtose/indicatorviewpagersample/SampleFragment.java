package com.fenchtose.indicatorviewpagersample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Jay Rambhia on 16/06/15.
 */
public class SampleFragment extends Fragment {

    public static int frag_num = 0;

    private int fragNum;

    public static SampleFragment newInstance() {
        return new SampleFragment();
    }

    public SampleFragment() {
        fragNum = frag_num;
        frag_num++;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        TextView textView = (TextView)inflater.inflate(R.layout.fragment_sample_layout, parent, false);
        textView.setText(String.valueOf(fragNum));
        return textView;
    }
}
