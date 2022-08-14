package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.annotation.SuppressLint;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csupporter.techwiz.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mct.components.baseui.BaseActivity;


public class NavAddAppointmentFragment extends BaseNavFragment implements View.OnClickListener {

    private FloatingActionButton btnAddNew;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_add_appointment, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        btnAddNew = view.findViewById(R.id.add_new_doctor);
        btnAddNew.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_new_doctor:
                replaceFragment(new AddDoctorFragment(), true, BaseActivity.Anim.RIGHT_IN_LEFT_OUT);
                break;
        }
    }
}