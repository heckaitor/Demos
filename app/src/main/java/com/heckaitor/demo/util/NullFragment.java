package com.heckaitor.demo.util;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heckaitor.demo.R;

/**
 * 空的Fragment，可以指定背景色，仅此而已
 * Created by kaige1 on 2018/4/27.
 */
public class NullFragment extends Fragment {

    private static final String ARGUMENT_BG_COLOR = "ARGUMENT_BG_COLOR";

    public static NullFragment create(@ColorInt int color) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_BG_COLOR, color);
        NullFragment fragment = new NullFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = new View(container.getContext());

        Bundle arguments = getArguments();
        if (arguments != null) {
            final int color = arguments.getInt(ARGUMENT_BG_COLOR);
            if (color > 0) {
                view.setBackgroundColor(color);
            } else {
                view.setBackgroundColor(getResources().getColor(R.color.light_background));
            }
        }
        return view;
    }
}
