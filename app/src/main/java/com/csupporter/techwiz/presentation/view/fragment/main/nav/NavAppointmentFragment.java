package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.csupporter.techwiz.presentation.presenter.authentication.UserAppointmentPresenter;
import com.csupporter.techwiz.presentation.view.adapter.DoctorListAdapter;
import com.csupporter.techwiz.presentation.view.dialog.LoadingDialog;

import java.util.List;

public class NavAppointmentFragment extends BaseNavFragment implements MainViewCallBack.UserAppointmentCallBack {

    private View view;
    private RecyclerView rcvListDoctor;
    private DoctorListAdapter doctorListAdapter;
    private UserAppointmentPresenter userAppointmentPresenter;
    private LoadingDialog dialog;
    private int department = -1;

    public static NavAppointmentFragment newInstance(int department) {

        Bundle args = new Bundle();
        args.putInt("department", department);
        NavAppointmentFragment fragment = new NavAppointmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            department = bundle.getInt("department");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nav_appointment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        userAppointmentPresenter = new UserAppointmentPresenter(this);
        if (getActivity() != null) {
            dialog = new LoadingDialog(getActivity());
        }
        if (department == -1) {
            setData();
        } else {
            setDataByDoctorType();
        }
    }

    private void setData() {

        userAppointmentPresenter.getAllDoctor(this);

        doctorListAdapter = new DoctorListAdapter(new DoctorListAdapter.OnItemCLickListener() {
            @Override
            public void onItemClick(Account account) {
                Toast.makeText(getActivity(), "" + account.getFirstName(), Toast.LENGTH_SHORT).show();
            }
        });

        rcvListDoctor.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rcvListDoctor.setAdapter(doctorListAdapter);
    }


    private void setDataByDoctorType() {

        userAppointmentPresenter.getDoctorsByDepartment(department,this);

        doctorListAdapter = new DoctorListAdapter(new DoctorListAdapter.OnItemCLickListener() {
            @Override
            public void onItemClick(Account account) {
                Toast.makeText(getActivity(), "" + account.getFirstName(), Toast.LENGTH_SHORT).show();
            }
        });

        rcvListDoctor.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rcvListDoctor.setAdapter(doctorListAdapter);
    }

    private void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view1 -> popLastFragment());
        rcvListDoctor = view.findViewById(R.id.rcv_list_doctor);
    }

    @Override
    public void doctorList(List<Account> accounts) {
        doctorListAdapter.setDoctorList(accounts);
    }

    @Override
    public void getNameAcc(Account account) {

    }

    @Override
    public void doctorListByDepartment(List<Account> accounts) {
        doctorListAdapter.setDoctorList(accounts);
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void showLoading() {
        if (getContext() == null) return;
        if (dialog != null && dialog.isShowing()) {
            return;
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