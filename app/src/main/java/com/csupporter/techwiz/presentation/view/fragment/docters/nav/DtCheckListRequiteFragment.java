package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.HealthTracking;
import com.mct.components.baseui.BaseFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HealthyTrackingFragment extends BaseFragment {


    private List<HealthTracking> healthTrackingList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_healthy_tracking, container, false);
        init(view);

        return view;

    }

    private void init(View view) {

    }
}


