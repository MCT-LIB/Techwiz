package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.csupporter.techwiz.App;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.presentation.internalmodel.AppointmentDetail;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.csupporter.techwiz.presentation.presenter.authentication.HistoryAppointmentPresenter;
import com.csupporter.techwiz.presentation.view.adapter.HomeCategoryDoctorAdapter;
import com.csupporter.techwiz.presentation.view.adapter.UserHomeAppointmentsAdapter;
import com.csupporter.techwiz.presentation.view.dialog.ConfirmDialog;
import com.csupporter.techwiz.presentation.view.dialog.LoadingDialog;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseOverlayDialog;
import com.mct.components.toast.ToastUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class NavHomeFragment extends BaseNavFragment implements
        HomeCategoryDoctorAdapter.OnClickCategoryItems, View.OnClickListener, MainViewCallBack.GetAppointmentHistoryCallback, UserHomeAppointmentsAdapter.OnclickListener {

    private RecyclerView categoryDoctor;
    private RecyclerView rclAppointmentList;
    private SwipeRefreshLayout refreshLayout;
    private View llNothing;

    private HistoryAppointmentPresenter historyAppointmentPresenter;
    private UserHomeAppointmentsAdapter userHomeAppointmentsAdapter;

    private CircleImageView avatar;
    private TextView name;

    private LoadingDialog dialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        historyAppointmentPresenter = new HistoryAppointmentPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_home, container, false);
        init(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Account account = App.getApp().getAccount();
        Glide.with(this).load(account.getAvatar())
                .placeholder(R.drawable.ic_default_avatar)
                .error(R.drawable.ic_default_avatar)
                .into(avatar);
        name.setText(account.getFullName());
    }

    private void init(@NonNull View view) {
        categoryDoctor = view.findViewById(R.id.category_doctor_list);
        rclAppointmentList = view.findViewById(R.id.home_list_appointment_of_day);
        llNothing = view.findViewById(R.id.ll_nothing);
        name = view.findViewById(R.id.tv_username);

        avatar = view.findViewById(R.id.img_avatar);
        avatar.setOnClickListener(this);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(() -> historyAppointmentPresenter.requestAppointmentsDoctor(this));

        setDataCategoryDoctor();
        setDataAppointmentList();
    }

    @Override
    public void onClick(@NonNull View view) {
        if (view.getId() == R.id.img_avatar) {
            changeTap(4, false);
        }
    }

    private void setDataCategoryDoctor() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        categoryDoctor.setLayoutManager(linearLayoutManager);
        HomeCategoryDoctorAdapter homeCategoryDoctorAdapter = new HomeCategoryDoctorAdapter(this, R.layout.nav_home_category_items);
        homeCategoryDoctorAdapter.setCategoryDoctorList();
        categoryDoctor.setAdapter(homeCategoryDoctorAdapter);
    }

    private void setDataAppointmentList() {
        userHomeAppointmentsAdapter = new UserHomeAppointmentsAdapter();
        userHomeAppointmentsAdapter.setOnclickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rclAppointmentList.setLayoutManager(linearLayoutManager);
        rclAppointmentList.setAdapter(userHomeAppointmentsAdapter);
        historyAppointmentPresenter.requestAppointmentsDoctor(this);
    }

    @Override
    public void onClickCategoryItem(int typeDoctor) {
        replaceFragment(AddDoctorFragment.newInstance(typeDoctor), true, BaseActivity.Anim.TRANSIT_FADE);
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
        refreshLayout.setRefreshing(false);
        userHomeAppointmentsAdapter.setDetailList(appointmentDetails);
        if (appointmentDetails.isEmpty()) {
            rclAppointmentList.setVisibility(View.GONE);
            llNothing.setVisibility(View.VISIBLE);
        } else {
            rclAppointmentList.setVisibility(View.VISIBLE);
            llNothing.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        refreshLayout.setRefreshing(false);
        rclAppointmentList.setVisibility(View.GONE);
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
                                userHomeAppointmentsAdapter.notifyItemChanged(pos);
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
                                userHomeAppointmentsAdapter.removeItem(appointmentDetail);
                                if (userHomeAppointmentsAdapter.getItemCount() == 0) {
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

}