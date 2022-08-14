package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.presentation.view.adapter.CustomSpinnerAdapter;

public class NavHistoryFragment extends BaseNavFragment {

    private Spinner classifyList;
    private String classifySelected;
    private final String[] classifyName = {"All", "Waiting", "Scheduled", "Completed", "Cancelled"};
    private final int[] classifyIcon = {
            R.drawable.ic_all_circle,
            R.drawable.ic_wait_circle,
            R.drawable.ic_schedule_circle,
            R.drawable.ic_check_circle,
            R.drawable.ic_cancel_circle};

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