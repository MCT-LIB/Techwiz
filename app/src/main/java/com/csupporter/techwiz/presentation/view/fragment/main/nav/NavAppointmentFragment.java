package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.presentation.view.adapter.DoctorListAdapter;

import java.util.ArrayList;
import java.util.List;

public class NavAppointmentFragment extends BaseNavFragment {

    private GridView doctorListLayout;
    private DoctorListAdapter doctorListAdapter;
    private List<Account> doctorList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_appointment, container, false);
        doctorListAdapter = new DoctorListAdapter();
        init(view);
        setDataForGrid();
        return view;
    }

    private void init(View view) {
        doctorListLayout = view.findViewById(R.id.doctor_list_layout);
    }

    private void setDataForGrid() {
        doctorListAdapter.setDataToDoctorList(doctorList);
        doctorListLayout.setAdapter(doctorListAdapter);
    }


}