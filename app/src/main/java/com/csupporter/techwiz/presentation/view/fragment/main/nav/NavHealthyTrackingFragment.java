package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import io.github.farshidroohi.LineChart;

public class NavHealthyTrackingFragment extends BaseFragment implements View.OnClickListener
        , MainViewCallBack.HealthTrackingCallBack
        , AddNewHealthTracking.OnClickAddNewHealthTracking {

    private TextView edtStartTime;
    private TextView edtEndTime;
    private DatePickerDialog.OnDateSetListener onStartDateListener;
    private DatePickerDialog.OnDateSetListener onEndDateListener;

    private final Calendar startCalendar = Calendar.getInstance();
    private final Calendar endCalendar = Calendar.getInstance();

    private LoadingDialog dialog;
    private LineChart lineChart;

    private HealthyTrackingPresenter healthyTrackingPresenter;
    private AddNewHealthTracking dialogAddHealthTracking;

    private RecyclerView rcvListTrack;
    private HealthTrackItemAdapter healthTrackItemAdapter;
    private Date dateStart;
    private Date dateEnd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_healthy_tracking, container, false);
        healthyTrackingPresenter = new HealthyTrackingPresenter(this);
        initView(view);
        setAdapter();

        return view;
    }


    private void setAdapter() {


        rcvListTrack.setLayoutManager(new LinearLayoutManager(getActivity()));
        healthTrackItemAdapter = new HealthTrackItemAdapter(healthTracking -> {
            if (getActivity() != null) {
                new DetailHealthTracDialog(getActivity(), healthTracking, App.getApp().getAccount()).create(null);
            }
        });
        rcvListTrack.setAdapter(healthTrackItemAdapter);
        healthyTrackingPresenter.getListTrack(dateStart.getTime(), dateEnd.getTime(), this);

    }

    private void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view1 -> popLastFragment());
        rcvListTrack = view.findViewById(R.id.rcv_list_track);

        lineChart = view.findViewById(R.id.lineChart);
        edtEndTime = view.findViewById(R.id.endTime);
        edtEndTime.setOnClickListener(this);
        edtStartTime = view.findViewById(R.id.startTime);
        edtStartTime.setOnClickListener(this);

        FloatingActionButton btnAddTrack = view.findViewById(R.id.btn_add_track);
        btnAddTrack.setOnClickListener(this);

        setDefaultTimeSpace();
    }

    private void setDefaultTimeSpace() {
        Calendar someDate = GregorianCalendar.getInstance();
        someDate.add(Calendar.DAY_OF_YEAR, -7);
        edtStartTime.setText(updateLabel(someDate.getTime()));
        dateStart = someDate.getTime();
        someDate.add(Calendar.DAY_OF_YEAR, +7);
        dateEnd = someDate.getTime();
        edtEndTime.setText(updateLabel(someDate.getTime()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_track:
                dialogAddHealthTracking = new AddNewHealthTracking(getActivity(), this);
                dialogAddHealthTracking.create(null);
                break;
            case R.id.startTime:
                startDateDialog();
                DatePickerDialog startDialog = new DatePickerDialog(getActivity(), onStartDateListener, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));
                startDialog.getWindow().setBackgroundDrawableResource(R.color.white);
                startDialog.show();
                break;
            case R.id.endTime:
                endDateDialog();
                DatePickerDialog endDialog = new DatePickerDialog(getActivity(), onEndDateListener, endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH));
                endDialog.getWindow().setBackgroundDrawableResource(R.color.white);
                endDialog.show();
                break;
        }
    }

    private void startDateDialog() {
        onStartDateListener = (view, year, month, day) -> {
            startCalendar.set(Calendar.YEAR, year);
            startCalendar.set(Calendar.MONTH, month);
            startCalendar.set(Calendar.DAY_OF_MONTH, day);
            dateStart = startCalendar.getTime();
            edtStartTime.setText(updateLabel(startCalendar.getTime()));
            healthyTrackingPresenter.getListTrack(dateStart.getTime(), dateEnd.getTime(), this);
        };
    }

    private void endDateDialog() {
        onEndDateListener = (view, year, month, day) -> {
            endCalendar.set(Calendar.YEAR, year);
            endCalendar.set(Calendar.MONTH, month);
            endCalendar.set(Calendar.DAY_OF_MONTH, day);
            dateEnd = endCalendar.getTime();
            edtEndTime.setText(updateLabel(endCalendar.getTime()));
            healthyTrackingPresenter.getListTrack(dateStart.getTime(), dateEnd.getTime(), this);
        };
    }

    private String updateLabel(Date date) {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.CHINA);
        return dateFormat.format(date);
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
    public void onGetDataSuccess(List<HealthTracking> trackingList) {
        healthTrackItemAdapter.setTrackingList(trackingList);
    }

    @Override
    public void onGetDataFailure() {

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