package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import static androidx.appcompat.widget.SearchView.*;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.csupporter.techwiz.App;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.model.Appointment;
import com.csupporter.techwiz.domain.model.BaseModel;
import com.csupporter.techwiz.domain.model.Prescription;
import com.csupporter.techwiz.presentation.internalmodel.AppointmentDetail;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.csupporter.techwiz.presentation.presenter.user.NavSearchPresenter;
import com.csupporter.techwiz.presentation.view.adapter.DoctorListAdapter;
import com.csupporter.techwiz.presentation.view.adapter.HistoryAppointmentAdapter;
import com.csupporter.techwiz.presentation.view.adapter.PrescriptionAdapter;
import com.csupporter.techwiz.presentation.view.dialog.AddAppointmentDialog;
import com.csupporter.techwiz.presentation.view.dialog.AlertDialog;
import com.csupporter.techwiz.presentation.view.dialog.ConfirmDialog;
import com.csupporter.techwiz.presentation.view.dialog.LoadingDialog;
import com.csupporter.techwiz.presentation.view.fragment.main.prescription.ListPrescriptionDetailFragment;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseOverlayDialog;
import com.mct.components.baseui.BaseOverlayLifecycle;
import com.mct.components.toast.ToastUtils;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NavSearchFragment extends BaseNavFragment implements SearchView.OnQueryTextListener, AdapterView.OnItemSelectedListener, OnClickListener, DoctorListAdapter.OnItemCLickListener, PrescriptionAdapter.OnItemClickListener, HistoryAppointmentAdapter.OnItemClickListener, MainViewCallBack.SearchCallback {

    private static final int SEARCH_TYPE_DOCTOR = 0;
    private static final int SEARCH_TYPE_PRESCRIPTION = 1;
    private static final int SEARCH_TYPE_APPOINTMENT = 2;
    private int currentType;

    private Spinner spinner;
    private RecyclerView rcvSearch;
    private View layoutSearchHint, llNoData;
    private LoadingDialog dialog;
    private SearchView searchView;
    private TextView tvSearch;

    private NavSearchPresenter navSearchPresenter;

    private DoctorListAdapter doctorListAdapter;
    private PrescriptionAdapter prescriptionAdapter;
    private HistoryAppointmentAdapter appointmentAdapter;

    private final Calendar mCalendar = Calendar.getInstance();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        navSearchPresenter = new NavSearchPresenter(this, this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navSearchPresenter.release();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        doctorListAdapter = new DoctorListAdapter(this);
        prescriptionAdapter = new PrescriptionAdapter(this);
        appointmentAdapter = new HistoryAppointmentAdapter(this);
        return inflater.inflate(R.layout.fragment_nav_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initSpinner();
    }

    @SuppressLint("ResourceType")
    private void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.src_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void initView(@NonNull View view) {
        spinner = view.findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(this);
        rcvSearch = view.findViewById(R.id.rcv_search_st);
        layoutSearchHint = view.findViewById(R.id.ll_search_hint);
        llNoData = view.findViewById(R.id.ll_no_data);
        tvSearch = view.findViewById(R.id.tv_search);
        tvSearch.setOnClickListener(this);
        searchView = view.findViewById(R.id.search_bar);
        searchView.setOnClickListener(this);
        searchView.setOnQueryTextListener(this);

    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        currentType = position;
        if (currentType == SEARCH_TYPE_APPOINTMENT) {
            tvSearch.setText("Choose day");
            tvSearch.setVisibility(View.VISIBLE);
            searchView.setVisibility(GONE);
        } else {
            tvSearch.setVisibility(View.GONE);
            searchView.setVisibility(View.VISIBLE);
            searchView.onActionViewCollapsed();
        }
        layoutSearchHint.setVisibility(VISIBLE);
        llNoData.setVisibility(GONE);
        rcvSearch.setVisibility(GONE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
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
    public void onClick(@NonNull View v) {
        if (v.getId() == R.id.search_bar) {
            searchView.onActionViewExpanded();
        }
        if (v.getId() == R.id.tv_search) {
            showDatePickerDialog();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (currentType == SEARCH_TYPE_DOCTOR) {
            navSearchPresenter.searchDoctor(query.trim());
        }
        if (currentType == SEARCH_TYPE_PRESCRIPTION) {
            navSearchPresenter.searchPrescription(query.trim());
        }
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void showDatePickerDialog() {
        int mYear = mCalendar.get(Calendar.YEAR);
        int mMonth = mCalendar.get(Calendar.MONTH);
        int mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    mCalendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                    navSearchPresenter.searchAppointment(mCalendar.getTimeInMillis());
                    tvSearch.setText(DateFormat.getDateInstance().format(new Date(mCalendar.getTimeInMillis())));
                }, mYear, mMonth, mDay);
        datePickerDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.bg_border_radius_16));
        datePickerDialog.show();
    }

    @Override
    public void onItemClick(Account account) {
        if (App.getApp().getAccount().isUser()) {
            Fragment fragment = DoctorInfoFragment.newInstance(account);
            replaceFragment(fragment, true, BaseActivity.Anim.TRANSIT_FADE);
        }
    }

    @Override
    public void onClickLike(Account account, int position) {
        navSearchPresenter.createMyDoctor(account, new MainViewCallBack.AddMyDoctor() {
            @Override
            public void addMyDoctorSuccess(Account doctor) {
                showToast("Added a doctor in your favorites!", ToastUtils.SUCCESS);
            }

            @Override
            public void addMyDoctorFail() {
                showToast("Add doctor fail!", ToastUtils.ERROR);
            }
        });
    }

    @Override
    public void onItemCLick(Prescription prescription, int pos) {
        Fragment fragment = ListPrescriptionDetailFragment.newInstance(prescription);
        replaceFragment(fragment, true, BaseActivity.Anim.TRANSIT_FADE);
    }

    @Override
    public void onClickSetAgain(@NonNull AppointmentDetail detail, int position) {
        new AddAppointmentDialog(requireContext(), detail.getAcc(), (dialog, appointment, appointmentSchedule) ->
                navSearchPresenter.createAppointment(appointment, appointmentSchedule, new MainViewCallBack.CreateAppointmentCallback() {
                    @Override
                    public void onCreateSuccess() {
                        dialog.dismiss();
                        if (getContext() != null) {
                            new AlertDialog(getContext(),
                                    R.drawable.ic_check_circle,
                                    "Appointment sent to doctor successfully!",
                                    BaseOverlayLifecycle::dismiss).create(null);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        showToast("Book appointment error", ToastUtils.ERROR);
                    }
                })).create(null);
    }

    @Override
    public void onClickCancel(AppointmentDetail detail, int position) {
        new ConfirmDialog(requireContext(),
                ConfirmDialog.LAYOUT_HOLD_USER,
                R.drawable.ic_cancel_circle,
                "Are you sure to cancel this appointment?",
                new ConfirmDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm(BaseOverlayDialog overlayDialog) {
                        overlayDialog.dismiss();
                        navSearchPresenter.updateAppointment(detail.getAppointment(), false, new MainViewCallBack.UpdateAppointmentCallback() {
                            @Override
                            public void onCreateSuccess() {
                                appointmentAdapter.notifyItemChanged(position);
                                showToast("Update success!", ToastUtils.SUCCESS);
                            }

                            @Override
                            public void onError(Throwable throwable) {
                            }
                        });
                    }

                    @Override
                    public void onCancel(BaseOverlayDialog overlayDialog) {
                        overlayDialog.dismiss();
                    }
                }).create(null);
    }

    @Override
    public void onClickConfirm(AppointmentDetail detail, int position) {
        new ConfirmDialog(requireContext(),
                ConfirmDialog.LAYOUT_CONFIRM,
                R.drawable.ic_check_circle,
                "Click yes to confirm!",
                new ConfirmDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm(BaseOverlayDialog overlayDialog) {
                        overlayDialog.dismiss();
                        navSearchPresenter.updateAppointment(detail.getAppointment(), true, new MainViewCallBack.UpdateAppointmentCallback() {
                            @Override
                            public void onCreateSuccess() {
                                appointmentAdapter.notifyItemChanged(position);
                                showToast("Update success!", ToastUtils.SUCCESS);
                            }

                            @Override
                            public void onError(Throwable throwable) {
                            }
                        });
                    }

                    @Override
                    public void onCancel(BaseOverlayDialog overlayDialog) {
                        overlayDialog.dismiss();
                    }
                }).create(null);
    }

    @Override
    public void onClickItem(AppointmentDetail detail, int position) {
        if (App.getApp().getAccount().isUser()) {
            Fragment fragment = DoctorInfoFragment.newInstance(detail.getAcc());
            replaceFragment(fragment, true, BaseActivity.Anim.TRANSIT_FADE);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onSearchSuccess(List<?> result, Class<? extends BaseModel> clazz) {
        if (getContext() == null) {
            return;
        }
        if (result.isEmpty()) {
            layoutSearchHint.setVisibility(GONE);
            llNoData.setVisibility(VISIBLE);
            rcvSearch.setVisibility(GONE);
            return;
        }
        layoutSearchHint.setVisibility(GONE);
        llNoData.setVisibility(GONE);
        rcvSearch.setVisibility(VISIBLE);

        doctorListAdapter.setDoctorList(null);
        prescriptionAdapter.setPrescriptionsList(null);
        appointmentAdapter.setAppointmentDetails(null);

        if (clazz == Account.class) {
            rcvSearch.setAdapter(doctorListAdapter);
            rcvSearch.setLayoutManager(new GridLayoutManager(getContext(), 2));
            doctorListAdapter.setDoctorList((List<Account>) result);
            return;
        }
        if (clazz == Prescription.class) {
            rcvSearch.setAdapter(prescriptionAdapter);
            rcvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
            prescriptionAdapter.setPrescriptionsList((List<Prescription>) result);
            return;
        }
        if (clazz == Appointment.class) {
            rcvSearch.setAdapter(appointmentAdapter);
            rcvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
            appointmentAdapter.setAppointmentDetails((List<AppointmentDetail>) result);
        }

    }

    @Override
    public void onError(Throwable throwable) {
        rcvSearch.setVisibility(GONE);
        layoutSearchHint.setVisibility(VISIBLE);
    }
}