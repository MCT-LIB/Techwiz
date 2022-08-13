package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.csupporter.techwiz.App;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.data.firebase_source.FirebaseUtils;
import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.model.HealthTracking;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.csupporter.techwiz.presentation.presenter.authentication.HealthyTrackingPresenter;
import com.csupporter.techwiz.presentation.view.adapter.HealthTrackItemAdapter;
import com.csupporter.techwiz.presentation.view.dialog.ConfirmDialog;
import com.csupporter.techwiz.presentation.view.dialog.DetailHealthTracDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mct.components.baseui.BaseFragment;
import com.mct.components.baseui.BaseOverlayDialog;
import com.mct.components.toast.ToastUtils;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NavHealthyTrackingFragment extends BaseFragment implements View.OnClickListener, MainViewCallBack.HealthTrackingCallBack {

    private FloatingActionButton btnAddTrack;
    private EditText height;
    private EditText weight;
    private EditText heartBeat;
    private EditText bloodSugar;
    private EditText bloodPressure;
    private EditText note;
    private AppCompatButton btnAddNew;
    private RecyclerView rcvListTrack;
    private HealthTrackItemAdapter healthTrackItemAdapter;
    private HealthyTrackingPresenter healthyTrackingPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_healthy_tracking, container, false);
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
        rcvListTrack = view.findViewById(R.id.rcv_list_track);
        btnAddTrack = view.findViewById(R.id.btn_add_track);
        btnAddTrack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_track:
                createAddDialog();
                break;
        }
    }

    private void createAddDialog() {
        Dialog dialogBottom = new Dialog(getActivity());
        dialogBottom.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBottom.setContentView(R.layout.add_health_tracking);
        initItemDialog(dialogBottom);
        dialogBottom.show();
        customAddHealthDialog(dialogBottom);
        addNewHealthTracking();
    }

    private void initItemDialog(Dialog dialog) {
        height = dialog.findViewById(R.id.edt_height);
        weight = dialog.findViewById(R.id.edt_weight);
        bloodSugar = dialog.findViewById(R.id.edt_blood_sugar);
        bloodPressure = dialog.findViewById(R.id.edt_blood_pressure);
        heartBeat = dialog.findViewById(R.id.edt_heart_beat);
        note = dialog.findViewById(R.id.edt_note_health);
        btnAddNew = dialog.findViewById(R.id.btn_add_health_tracking);
    }

    private void customAddHealthDialog(Dialog dialog) {
        dialog.getWindow().

                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().

                setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().

                getAttributes().windowAnimations = androidx.appcompat.R.style.Base_Animation_AppCompat_DropDownUp;
        dialog.getWindow().

                setGravity(Gravity.BOTTOM);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void addNewHealthTracking() {

        btnAddNew.setOnClickListener(view -> {
            HealthTracking healthTracking = getDatFromFormInput();
            if (healthTracking != null) {
                showLoading();
                DataInjection.provideRepository().heathTracking.addTracking(healthTracking, unused -> {
                    healthyTrackingPresenter.getListTrack(this);

                    hideLoading();
                    ToastUtils.makeSuccessToast(getActivity(), Toast.LENGTH_SHORT, "Add new health tracking success !", true).show();
                }, throwable -> ToastUtils.makeErrorToast(getActivity(), Toast.LENGTH_SHORT, "Add new health tracking fail !", true).show());
            }
        });
    }

    @SuppressLint("NewApi")
    private HealthTracking getDatFromFormInput() {

        Account account = App.getApp().getAccount();
        String txtHeight = height.getText().toString().trim();
        String txtWeight = height.getText().toString().trim();
        String txtHeartBeat = heartBeat.getText().toString().trim();
        String txtBloodSugar = bloodSugar.getText().toString().trim();
        String txtBloodPressure = bloodPressure.getText().toString().trim();
        String userNote = note.getText().toString().trim();


        if (txtHeight.isEmpty() || txtWeight.isEmpty() ||
                txtBloodSugar.isEmpty() || txtHeartBeat.isEmpty() ||
                txtBloodPressure.isEmpty()) {
            hideLoading();
            ToastUtils.makeWarningToast(getActivity(), Toast.LENGTH_SHORT, "Please complete all information !", true).show();
        } else if (Float.parseFloat(txtBloodPressure) <= 0 ||
                Float.parseFloat(txtBloodSugar) <= 0 ||
                Float.parseFloat(txtWeight) <= 0 ||
                Float.parseFloat(txtHeight) <= 0 ||
                Float.parseFloat(txtHeartBeat) <= 0) {
            hideLoading();
            ToastUtils.makeWarningToast(getActivity(), Toast.LENGTH_SHORT, "Data cannot be equal to or less than 0!", true).show();
        } else {

            HealthTracking healthTracking = new HealthTracking();
            healthTracking.setId(FirebaseUtils.uniqueId());
            healthTracking.setUserId(account.getId());
            healthTracking.setHeight(Float.parseFloat(txtHeight));
            healthTracking.setWeight(Float.parseFloat(txtWeight));
            healthTracking.setHeartbeat(Float.parseFloat(txtHeartBeat));
            healthTracking.setBloodSugar(Float.parseFloat(txtBloodSugar));
            healthTracking.setBloodPressure(Float.parseFloat(txtBloodPressure));
            healthTracking.setOther(userNote);
            healthTracking.setCreateAt(System.currentTimeMillis());

            return healthTracking;
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