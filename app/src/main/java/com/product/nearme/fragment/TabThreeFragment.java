package com.product.nearme.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.product.nearme.R;

public class TabThreeFragment extends Fragment {
    View rootView;
    Bundle savedInstance;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab_threefragment, container, false);

        savedInstance = savedInstanceState;
        return rootView;
    }
}
