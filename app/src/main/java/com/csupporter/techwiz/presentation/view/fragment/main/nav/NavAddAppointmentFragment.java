package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.model.Appointment;
import com.csupporter.techwiz.domain.model.MyDoctor;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.csupporter.techwiz.presentation.presenter.authentication.AddAppointmentPresenter;
import com.csupporter.techwiz.presentation.view.adapter.AddAppointmentAdapter;
import com.csupporter.techwiz.presentation.view.dialog.AddAppointmentDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mct.components.baseui.BaseActivity;

import java.util.List;


public class NavAddAppointmentFragment extends BaseNavFragment implements View.OnClickListener,
        MainViewCallBack.GetAllMyDoctorCallBack, AddAppointmentAdapter.OnClickBookAppointment {

    private FloatingActionButton btnAddNew;
    private RecyclerView rcvListMyDoctor;

    private AddAppointmentAdapter addAppointmentAdapter;
    private AddAppointmentPresenter addAppointmentPresenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        addAppointmentPresenter = new AddAppointmentPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_add_appointment, container, false);
        init(view);
        addAppointmentPresenter.getAllMyDoctor(this);
        return view;
    }

    private void init(View view) {
        rcvListMyDoctor = view.findViewById(R.id.rcv_list_my_doctor);
        btnAddNew = view.findViewById(R.id.add_new_doctor);
        btnAddNew.setOnClickListener(this);

        addAppointmentAdapter = new AddAppointmentAdapter(this);
        rcvListMyDoctor.setAdapter(addAppointmentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rcvListMyDoctor.setLayoutManager(linearLayoutManager);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_new_doctor:
                replaceFragment(AddDoctorFragment.newInstance(-1), true, BaseActivity.Anim.RIGHT_IN_LEFT_OUT);
                break;
        }
    }

    @Override
    public void allMyDoctor(List<Account> myDoctorList) {
        addAppointmentAdapter.setDoctorList(myDoctorList);
    }

    @Override
    public void onClickBookAppointment() {
        new AddAppointmentDialog(requireContext(), new AddAppointmentDialog.OnClickAddNewAppointment() {
            @Override
            public void onClickAddNew(Appointment appointment) {
//                addAppointmentPresenter.
            }
        }).create(null);
    }
}