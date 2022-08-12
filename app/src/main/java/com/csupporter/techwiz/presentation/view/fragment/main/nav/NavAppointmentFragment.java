package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.csupporter.techwiz.presentation.presenter.authentication.UserAppointmentPresenter;
import com.csupporter.techwiz.presentation.view.adapter.DoctorListAdapter;
import com.csupporter.techwiz.presentation.view.dialog.LoadingDialog;
import com.mct.components.baseui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class NavAppointmentFragment extends BaseNavFragment implements MainViewCallBack.UserAppointmentCallBack {

    private GridView doctorListLayout;
    private DoctorListAdapter doctorListAdapter;
    private UserAppointmentPresenter userAppointmentPresenter;
    private List<Account> doctorList = new ArrayList<>();
    private LoadingDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_appointment, container, false);
        doctorListAdapter = new DoctorListAdapter();
        userAppointmentPresenter = new UserAppointmentPresenter(this);
        userAppointmentPresenter.getAllDoctor(this);
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

    @Override
    public void doctorList(List<Account> accounts) {
       doctorList.addAll(accounts);
    }

    @Override
    public void showLoading() {
        if (getContext() == null) return;
        if (dialog != null) {
            dialog.dismiss();
        }
        dialog = new LoadingDialog(getContext());
        dialog.create(null);
    }

    @Override
    public void hideLoading() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

}