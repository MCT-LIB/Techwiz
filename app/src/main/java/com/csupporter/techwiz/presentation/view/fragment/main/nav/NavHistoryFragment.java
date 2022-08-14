package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.presentation.internalmodel.AppointmentDetail;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.csupporter.techwiz.presentation.presenter.authentication.HistoryAppointmentPresenter;
import com.csupporter.techwiz.presentation.view.adapter.CustomSpinnerAdapter;
import com.csupporter.techwiz.presentation.view.adapter.HistoryAppointmentAdapter;
import com.csupporter.techwiz.presentation.view.dialog.LoadingDialog;
import com.mct.components.toast.ToastUtils;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class NavHistoryFragment extends BaseNavFragment implements MainViewCallBack.GetAppointmentHistoryCallback {


    private final String[] classifyName = {"All", "Waiting", "Scheduled", "Completed", "Cancelled"};
    private final int[] classifyIcon = {
            R.drawable.ic_all_circle,
            R.drawable.ic_wait_circle,
            R.drawable.ic_schedule_circle,
            R.drawable.ic_check_circle,
            R.drawable.ic_cancel_circle};
    private int classifySelected;
    private CollapsibleCalendar collapsibleCalendar;
    private Spinner classifyList;
    private RecyclerView rcvHistoryMeet;
    private LoadingDialog dialog;
    private HistoryAppointmentAdapter historyAppointmentAdapter;
    private HistoryAppointmentPresenter historyAppointmentPresenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        historyAppointmentPresenter = new HistoryAppointmentPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_history, container, false);
        init(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void init(@NonNull View view) {
        collapsibleCalendar = view.findViewById(R.id.calendar_view);
        classifyList = view.findViewById(R.id.status_appointment);
        rcvHistoryMeet = view.findViewById(R.id.rcv_history_meet);
        initRecyclerView();
        initSpinner();
    }

    private void initSpinner() {
        CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(getActivity(), classifyIcon, classifyName);
        classifyList.setAdapter(customAdapter);
        classifyList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                classifySelected = i;
                loadAppointment();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void initRecyclerView() {
        historyAppointmentAdapter = new HistoryAppointmentAdapter(this::onClickSetAgain);
        rcvHistoryMeet.setAdapter(historyAppointmentAdapter);
        rcvHistoryMeet.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void onClickSetAgain(AppointmentDetail appointmentDetail, int position) {

    }

    private void loadAppointment() {
        Day day = collapsibleCalendar.getSelectedDay();
        if (day == null) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(day.getYear(), day.getMonth(), day.getDay());
        historyAppointmentPresenter.requestAppointments(getStatus(), calendar.getTimeInMillis(), this);
    }

    private List<Integer> getStatus() {
        switch (classifySelected) {
            default:
            case 0:
                return Arrays.asList(0, 1, 2, 3, 4, 5, 6);
            case 1:
                return Collections.singletonList(0);
            case 2:
                return Collections.singletonList(1);
            case 3:
                return Arrays.asList(5, 6);
            case 4:
                return Arrays.asList(2, 3, 4);
        }
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
    public void onGetHistorySuccess(List<AppointmentDetail> appointmentDetails) {
        showToast("AP size" + appointmentDetails.size() , ToastUtils.INFO);
        historyAppointmentAdapter.setAppointmentDetails(appointmentDetails);
    }

    @Override
    public void onError(Throwable throwable) {
        Log.e("ddd", "onError: ", throwable);
    }
}