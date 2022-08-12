package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.presentation.view.adapter.CustomSpinnerAdapter;
import com.mct.components.baseui.BaseFragment;

public class NavHistoryFragment extends BaseNavFragment {

    private Spinner classifyList;
    private String classifySelected;
    private String[] classifyName = {"Completed", "Cancelled"};
    private int[] classifyIcon = {R.drawable.ic_baseline_check_circle_24, R.drawable.ic_baseline_cancel_presentation_24};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_history, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        classifyList = view.findViewById(R.id.status_appointment);
        setDataForSpinner();
    }

    private void setDataForSpinner() {
        CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(getActivity(), classifyIcon, classifyName);
        classifyList.setAdapter(customAdapter);
        classifyList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                classifySelected = classifyName[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}