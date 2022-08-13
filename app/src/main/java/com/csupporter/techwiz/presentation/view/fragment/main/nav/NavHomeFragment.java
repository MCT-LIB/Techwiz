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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.csupporter.techwiz.App;
import com.csupporter.techwiz.R;

import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.model.Appointment;
import com.csupporter.techwiz.presentation.presenter.AuthenticationCallback;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.csupporter.techwiz.presentation.presenter.authentication.UserHomePresenter;
import com.csupporter.techwiz.presentation.view.adapter.UserHomeAppointmentsAdapter;
import com.csupporter.techwiz.presentation.view.dialog.LoadingDialog;
import com.mct.components.baseui.BaseFragment;
import com.mct.components.toast.ToastUtils;
import com.csupporter.techwiz.presentation.view.adapter.HomeCategoryDoctorAdapter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class NavHomeFragment extends BaseNavFragment implements MainViewCallBack.UserHomeCallBack,
        HomeCategoryDoctorAdapter.OnClickCategoryItems, View.OnClickListener {

    private SearchView txtSearch;
    private TextView txtMyAppointment;
    private RecyclerView categoryDoctor;
    private RecyclerView rclAppointmentList;
    private CircleImageView avatar;
    private TextView name;

    private HomeCategoryDoctorAdapter homeCategoryDoctorAdapter;
    private UserHomeAppointmentsAdapter userHomeAppointmentsAdapter;
    private UserHomePresenter userHomePresenter;
    private List<Appointment> listAppointment = new ArrayList<>();
    private LoadingDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_home, container, false);

        homeCategoryDoctorAdapter = new HomeCategoryDoctorAdapter(this);
        userHomeAppointmentsAdapter = new UserHomeAppointmentsAdapter();
        userHomePresenter = new UserHomePresenter(this);

        init(view);
        useDataFromSearchBar();

        return view;
    }

    private void init(View view) {
        categoryDoctor = view.findViewById(R.id.category_doctor_list);
        rclAppointmentList = view.findViewById(R.id.home_list_appointment_of_day);
        txtSearch = view.findViewById(R.id.search_bar);
        txtSearch.setOnClickListener(this);
        name = view.findViewById(R.id.tv_username);
        txtMyAppointment = view.findViewById(R.id.nav_home_text);

        avatar = view.findViewById(R.id.img_avatar);
        avatar.setOnClickListener(this);

        setDataForUI();
        setDataCategoryDoctor();
    }

    @SuppressLint("SetTextI18n")
    private void setDataForUI() {
        Account account = App.getApp().getAccount();

        if (account.getAvatar() == null) {
            avatar.setImageResource(R.drawable.ic_baseline_person_pin_24);
        } else {
            Glide.with(getActivity()).load(account.getAvatar())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(avatar);
        }

        name.setText(account.getLastName() + " " + account.getFirstName());

        setDataAppointmentList();
        userHomePresenter.getUpcomingAppointment(account, this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_bar:
                txtSearch.setIconified(false);
                txtSearch.onActionViewExpanded();
                break;
            case R.id.img_avatar:
                changeTap(4, false);
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
        rclAppointmentList.setLayoutManager(linearLayoutManager);
    }


    @Override
    public void onClickCategoryItem(int typeDoctor) {
        showToast(typeDoctor + "", ToastUtils.SUCCESS, true);
    }

    @Override
    public void listAppointment(List<Appointment> appointmentList) {
        userHomeAppointmentsAdapter.setDataToAppointmentList(appointmentList);
        rclAppointmentList.setAdapter(userHomeAppointmentsAdapter);
    }


    private void useDataFromSearchBar() {
        txtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
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