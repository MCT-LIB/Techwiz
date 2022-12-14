package com.csupporter.techwiz.presentation.view.fragment.docters.nav;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.csupporter.techwiz.App;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.presentation.internalmodel.AppointmentDetail;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.csupporter.techwiz.presentation.presenter.user.HistoryAppointmentPresenter;
import com.csupporter.techwiz.presentation.view.adapter.UserHomeAppointmentsAdapter;
import com.csupporter.techwiz.presentation.view.dialog.ConfirmDialog;
import com.csupporter.techwiz.presentation.view.dialog.LoadingDialog;
import com.csupporter.techwiz.presentation.view.fragment.docters.DoctorFragment;
import com.mct.components.baseui.BaseFragment;
import com.mct.components.baseui.BaseOverlayDialog;
import com.mct.components.toast.ToastUtils;

import java.util.List;

public class DtHomeFragment extends BaseFragment implements View.OnClickListener, MainViewCallBack.GetAppointmentHistoryCallback, UserHomeAppointmentsAdapter.OnclickListener {

    private View view;
    private ImageView imgAvatar;
    private TextView tvUserName;

    private SwipeRefreshLayout refreshLayout;
    private View llNothing;
    private RecyclerView homeListAppointmentOfDay;
    private HistoryAppointmentPresenter historyAppointmentPresenter;
    private UserHomeAppointmentsAdapter homeCategoryDoctorAdapter;

    private LoadingDialog dialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        historyAppointmentPresenter = new HistoryAppointmentPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dt_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setDataAppointmentList();
        historyAppointmentPresenter.requestAppointmentsDoctor(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(this).load(App.getApp().getAccount().getAvatar())
                .placeholder(R.drawable.ic_default_avatar)
                .error(R.drawable.ic_default_avatar)
                .into(imgAvatar);

        tvUserName.setText(App.getApp().getAccount().getFullName());
    }

    private void initView(@NonNull View view) {
        imgAvatar = view.findViewById(R.id.img_avatar);
        imgAvatar.setOnClickListener(this);
        tvUserName = view.findViewById(R.id.tv_username);
        llNothing = view.findViewById(R.id.ll_no_data);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(() -> historyAppointmentPresenter.requestAppointmentsDoctor(this));
        homeListAppointmentOfDay = view.findViewById(R.id.home_list_appointment_of_day);
    }

    @Override
    public void onClick(@NonNull View v) {
        if (v.getId() == R.id.img_avatar) {
            changeTap(2, false);
        }
    }

    public void changeTap(int index, boolean smooth) {
        DoctorFragment doctorFragment = getMainParentFragment();
        if (doctorFragment == null) {
            return;
        }
        doctorFragment.changePage(index, smooth);
    }

    private void setDataAppointmentList() {
        homeCategoryDoctorAdapter = new UserHomeAppointmentsAdapter();
        homeCategoryDoctorAdapter.setOnclickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        homeListAppointmentOfDay.setLayoutManager(linearLayoutManager);
        homeListAppointmentOfDay.setAdapter(homeCategoryDoctorAdapter);
    }

    @Nullable
    private DoctorFragment getMainParentFragment() {
        Fragment fragment = getParentFragmentManager().findFragmentByTag(DoctorFragment.class.getName());
        if (fragment instanceof DoctorFragment) {
            return (DoctorFragment) fragment;
        }
        return null;
    }


    @Override
    public void onGetHistorySuccess(List<AppointmentDetail> appointmentDetails) {
        refreshLayout.setRefreshing(false);
        homeCategoryDoctorAdapter.setDetailList(appointmentDetails);
        if (appointmentDetails.isEmpty()) {
            homeListAppointmentOfDay.setVisibility(View.GONE);
            llNothing.setVisibility(View.VISIBLE);
        } else {
            homeListAppointmentOfDay.setVisibility(View.VISIBLE);
            llNothing.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        refreshLayout.setRefreshing(false);
        homeListAppointmentOfDay.setVisibility(View.GONE);
        llNothing.setVisibility(View.VISIBLE);
    }

    @Override
    public void onConfirm(AppointmentDetail appointmentDetail, int pos) {
        new ConfirmDialog(requireContext(),
                ConfirmDialog.LAYOUT_CONFIRM,
                R.drawable.ic_check_circle,
                "Click ok to confirm!",
                new ConfirmDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm(BaseOverlayDialog overlayDialog) {
                        overlayDialog.dismiss();
                        historyAppointmentPresenter.updateAppointment(appointmentDetail.getAppointment(), true, new MainViewCallBack.UpdateAppointmentCallback() {
                            @Override
                            public void onCreateSuccess() {
                                showToast("Update success!", ToastUtils.SUCCESS);
                                homeCategoryDoctorAdapter.notifyItemChanged(pos);
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
    public void onCancel(AppointmentDetail appointmentDetail, int pos) {
        new ConfirmDialog(requireContext(),
                ConfirmDialog.LAYOUT_HOLD_USER,
                R.drawable.ic_cancel_circle,
                "Are you sure to cancel this appointment?",
                new ConfirmDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm(BaseOverlayDialog overlayDialog) {
                        overlayDialog.dismiss();
                        historyAppointmentPresenter.updateAppointment(appointmentDetail.getAppointment(), false, new MainViewCallBack.UpdateAppointmentCallback() {
                            @Override
                            public void onCreateSuccess() {
                                showToast("Update success!", ToastUtils.SUCCESS);
                                homeCategoryDoctorAdapter.removeItem(appointmentDetail);
                                if (homeCategoryDoctorAdapter.getItemCount() == 0) {
                                    llNothing.setVisibility(View.VISIBLE);
                                }
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