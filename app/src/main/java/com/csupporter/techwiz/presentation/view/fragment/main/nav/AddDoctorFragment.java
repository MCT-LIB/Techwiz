package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.mct.components.toast.ToastUtils;

import java.util.List;

public class AddDoctorFragment extends BaseNavFragment implements MainViewCallBack.UserAppointmentCallBack,
        DoctorListAdapter.OnItemCLickListener,
        MainViewCallBack.AddMyDoctor {

    private View view;
    private RecyclerView rcvListDoctor;
    private DoctorListAdapter doctorListAdapter;
    private UserAppointmentPresenter userAppointmentPresenter;
    private LoadingDialog dialog;
    private int department;

    public static AddDoctorFragment newInstance(int department) {
        Bundle args = new Bundle();
        args.putInt("department", department);
        AddDoctorFragment fragment = new AddDoctorFragment();
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
        rcvListDoctor.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        doctorListAdapter = new DoctorListAdapter(this);
        rcvListDoctor.setAdapter(doctorListAdapter);
        userAppointmentPresenter.getDoctorsByDepartment(department, this);
    }

    private void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view1 -> popLastFragment());
        rcvListDoctor = view.findViewById(R.id.rcv_list_doctor);
    }

    @Override
    public void onRequestSuccess(List<Account> accounts) {
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

    @Override
    public void onItemClick(Account account) {

    }

    @Override
    public void onClickLike(Account account, int position) {
        userAppointmentPresenter.createMyDoctor(account, position, this);
    }

    @Override
    public void addMyDoctorSuccess(Account doctor, int position) {
        doctorListAdapter.deleteItemAccount(position);
        ToastUtils.makeSuccessToast(getActivity(), Toast.LENGTH_SHORT, "Be Added doctor in your favorite ! ", true).show();
    }

    @Override
    public void addMyDoctorFail() {
        ToastUtils.makeSuccessToast(getActivity(), Toast.LENGTH_SHORT, "Add doctor fail ! ", true).show();
    }

}