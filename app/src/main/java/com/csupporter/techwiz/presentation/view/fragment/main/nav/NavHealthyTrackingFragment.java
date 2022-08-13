package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csupporter.techwiz.App;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.HealthTracking;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.csupporter.techwiz.presentation.presenter.authentication.HealthyTrackingPresenter;
import com.csupporter.techwiz.presentation.view.adapter.HealthTrackItemAdapter;
import com.csupporter.techwiz.presentation.view.dialog.AddNewHealthTracking;
import com.csupporter.techwiz.presentation.view.dialog.DetailHealthTracDialog;
import com.csupporter.techwiz.presentation.view.dialog.LoadingDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mct.components.baseui.BaseFragment;
import com.mct.components.toast.ToastUtils;

import java.util.List;

public class NavHealthyTrackingFragment extends BaseFragment implements View.OnClickListener
        , MainViewCallBack.HealthTrackingCallBack
        , AddNewHealthTracking.OnClickAddNewHealthTracking {

    private FloatingActionButton btnAddTrack;

    private LoadingDialog dialog;

    private HealthyTrackingPresenter healthyTrackingPresenter;
    private AddNewHealthTracking dialogAddHealthTracking;

    private RecyclerView rcvListTrack;
    private HealthTrackItemAdapter healthTrackItemAdapter;
    private HealthyTrackingPresenter healthyTrackingPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_healthy_tracking, container, false);
        healthyTrackingPresenter = new HealthyTrackingPresenter(this);
        initView(view);
        healthyTrackingPresenter = new HealthyTrackingPresenter(this);
        setAdapter();
        return view;
    }

    private void setAdapter() {
        healthyTrackingPresenter.getListTrack(this);

        rcvListTrack.setLayoutManager(new LinearLayoutManager(getActivity()));
        healthTrackItemAdapter = new HealthTrackItemAdapter(new HealthTrackItemAdapter.OnCLickItemTrack() {
            @Override
            public void onClickItem(HealthTracking healthTracking) {
                if (getActivity() != null){
                    new DetailHealthTracDialog(getActivity(), healthTracking, App.getApp().getAccount()).create(null);
                }
            }
        });
        rcvListTrack.setAdapter(healthTrackItemAdapter);

    }

    private void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view1 -> popLastFragment());
        rcvListTrack = view.findViewById(R.id.rcv_list_track);
        btnAddTrack = view.findViewById(R.id.btn_add_track);
        btnAddTrack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_track:
                dialogAddHealthTracking = new AddNewHealthTracking(getActivity(), this);
                dialogAddHealthTracking.create(null);
                break;
        }
    }

    @Override
    public void onClickAddNew(String txtHeight, String txtWeight, String txtHeartBeat, String txtBloodSugar, String txtBloodPressure, String txtNote) {
        healthyTrackingPresenter.addNewHealthTracking(txtHeight, txtWeight, txtHeartBeat, txtBloodSugar, txtBloodPressure, txtNote, this);
    }

    @Override
    public void addHealthTrackingSuccess() {
        dialogAddHealthTracking.dismiss();
        ToastUtils.makeSuccessToast(getActivity(), Toast.LENGTH_SHORT, "Add new health tracking success !", true).show();
    }

    @Override
    public void addHealthTrackingFail(String message) {
        ToastUtils.makeWarningToast(getActivity(), Toast.LENGTH_SHORT, message + "", true).show();
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
        return null;
    }

    @Override
    public void onGetDataSuccess(List<HealthTracking> trackingList) {
        healthTrackItemAdapter.setTrackingList(trackingList);
    }

    @Override
    public void onGetDataFailure() {
    }
}