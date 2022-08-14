package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.presentation.internalmodel.AppointmentDetail;
import com.csupporter.techwiz.presentation.presenter.authentication.HistoryAppointmentPresenter;
import com.csupporter.techwiz.presentation.view.adapter.CustomSpinnerAdapter;
import com.csupporter.techwiz.presentation.view.adapter.HistoryAppointmentAdapter;
import com.mct.components.toast.ToastUtils;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

public class NavHistoryFragment extends BaseNavFragment {


    private final String[] classifyName = {"All", "Waiting", "Scheduled", "Completed", "Cancelled"};
    private final int[] classifyIcon = {
            R.drawable.ic_all_circle,
            R.drawable.ic_wait_circle,
            R.drawable.ic_schedule_circle,
            R.drawable.ic_check_circle,
            R.drawable.ic_cancel_circle};
    private String classifySelected;
    private CollapsibleCalendar collapsibleCalendar;
    private Spinner classifyList;
    private RecyclerView rcvHistoryMeet;
    private HistoryAppointmentAdapter historyAppointmentAdapter;
    private HistoryAppointmentPresenter historyAppointmentPresenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        historyAppointmentPresenter = new HistoryAppointmentPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_history, container, false);
        init(view);
        return view;
    }

    private void init(@NonNull View view) {
        collapsibleCalendar = view.findViewById(R.id.calendar_view);
        classifyList = view.findViewById(R.id.status_appointment);
        rcvHistoryMeet = view.findViewById(R.id.rcv_history_meet);
        initSpinner();
        initRecyclerView();
    }

    private void initSpinner() {
        CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(getActivity(), classifyIcon, classifyName);
        classifyList.setAdapter(customAdapter);
        classifyList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                classifySelected = classifyName[i];
                showToast(classifySelected, ToastUtils.INFO);
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
        loadAppointment();
    }

    private void onClickSetAgain(AppointmentDetail appointmentDetail, int position) {

    }

    private void loadAppointment() {

//        historyAppointmentPresenter.requestAppointments();
    }
}