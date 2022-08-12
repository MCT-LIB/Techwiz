package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.SearchView;
import android.widget.TextView;

import com.csupporter.techwiz.R;

import com.csupporter.techwiz.domain.model.Appointment;
import com.csupporter.techwiz.presentation.presenter.AuthenticationCallback;
import com.csupporter.techwiz.presentation.view.adapter.UserHomeAppointmentsAdapter;
import com.mct.components.baseui.BaseFragment;
import com.mct.components.toast.ToastUtils;
import com.csupporter.techwiz.presentation.view.adapter.HomeCategoryDoctorAdapter;

import java.util.ArrayList;
import java.util.List;


public class NavHomeFragment extends BaseNavFragment implements AuthenticationCallback.UserHomeCallBack,
        HomeCategoryDoctorAdapter.OnClickCategoryItems {

    private SearchView txtSearch;
    private TextView txtMyAppointment;
    private RecyclerView categoryDoctor;
    private RecyclerView appointmentList;
    private CircleImageView avatar;

    private HomeCategoryDoctorAdapter homeCategoryDoctorAdapter;
    private UserHomeAppointmentsAdapter userHomeAppointmentsAdapter;
    private UserHomePresenter userHomePresenter;
    private List<Appointment> listAppointment = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_home, container, false);

        homeCategoryDoctorAdapter = new HomeCategoryDoctorAdapter(this);
        userHomeAppointmentsAdapter = new UserHomeAppointmentsAdapter();
        userHomePresenter = new UserHomePresenter(this);

        init(view);
        setDataCategoryDoctor();
        eventClickItem();
        return view;
    }

    private void init(View view) {
        categoryDoctor = view.findViewById(R.id.category_doctor_list);
        appointmentList = view.findViewById(R.id.home_list_appointment_of_day);
        txtSearch = view.findViewById(R.id.search_bar);
        txtSearch.setOnClickListener(this);

        txtMyAppointment = view.findViewById(R.id.nav_home_text);

        avatar = view.findViewById(R.id.img_avatar);
        avatar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_bar:
                txtSearch.setIconified(false);
                txtSearch.onActionViewExpanded();
                break;
            case R.id.img_avatar:

                break;
        }
    }

    private void setDataCategoryDoctor() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        categoryDoctor.setLayoutManager(linearLayoutManager);
        homeCategoryDoctorAdapter.setCategoryDoctorList();
        categoryDoctor.setAdapter(homeCategoryDoctorAdapter);
    }

    private void setDataAppointmentList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        appointmentList.setLayoutManager(linearLayoutManager);
        userHomeAppointmentsAdapter.setDataToAppointmentList(listAppointment);
        appointmentList.setAdapter(userHomeAppointmentsAdapter);
    }


    @Override
    public void onClickCategoryItem(String typeDoctor) {
        showToast(typeDoctor + "", ToastUtils.SUCCESS, true);
    }

    @Override
    public void listAppointment(List<Appointment> appointmentList) {

    }
}