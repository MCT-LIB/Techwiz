package com.csupporter.techwiz.presentation.view.fragment.docters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.csupporter.techwiz.R;
import com.mct.components.baseui.BaseFragment;

public class DoctorFragment extends BaseFragment implements View.OnClickListener{

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_doctor, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        eventClick();
    }

    private void eventClick() {
    }

    private void initView(View view) {
    }

    @Override
    public void onClick(View v) {

    }
}
