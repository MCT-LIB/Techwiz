package com.csupporter.techwiz.presentation.view.fragment.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.csupporter.techwiz.App;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.csupporter.techwiz.presentation.presenter.authentication.ProfilePresenter;
import com.csupporter.techwiz.presentation.view.dialog.ChooseGenderDialog;
import com.csupporter.techwiz.presentation.view.dialog.ConfirmDialog;
import com.csupporter.techwiz.presentation.view.dialog.EnterChangePasswordDialog;
import com.csupporter.techwiz.presentation.view.dialog.EnterNameDialog;
import com.csupporter.techwiz.presentation.view.dialog.EnterPhoneDialog;
import com.csupporter.techwiz.presentation.view.dialog.LoadingDialog;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseFragment;
import com.mct.components.baseui.BaseOverlayDialog;
import com.mct.components.toast.ToastUtils;

import java.util.Objects;

public class ProfileFragment extends BaseFragment implements View.OnClickListener, BaseActivity.OnBackPressed, MainViewCallBack.UpdateProfileCallback {

    private Account tmpAccount;
    private View mView;
    private TextView tvName, tvPhone, tvGender;
    private ProfilePresenter profilePresenter;
    private LoadingDialog dialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        profilePresenter = new ProfilePresenter(this);
        tmpAccount = App.getApp().getAccount().copy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile, container, false);

        initUi();
        return mView;
    }

    private void initUi() {

        Toolbar toolbar = mView.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_confirm);
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
        toolbar.setOnMenuItemClickListener(item -> {
            if (isDataChange()) {
                App.getApp().setAccount(tmpAccount);
                profilePresenter.updateProfile(tmpAccount, this);
            }
            return false;
        });

        mView.findViewById(R.id.cv_name).setOnClickListener(this);
        mView.findViewById(R.id.cv_phone).setOnClickListener(this);
        mView.findViewById(R.id.cv_password).setOnClickListener(this);
        mView.findViewById(R.id.cv_gender).setOnClickListener(this);
        tvName = mView.findViewById(R.id.tv_name);
        tvPhone = mView.findViewById(R.id.tv_phone);
        tvGender = mView.findViewById(R.id.tv_gender);

        setData();
    }

    @Override
    @SuppressLint("NonConstantResourceId")
    public void onClick(@NonNull View v) {
        if (tmpAccount == null) {
            tmpAccount = App.getApp().getAccount().copy();
        }
        switch (v.getId()) {
            case R.id.cv_name:
                new EnterNameDialog(requireContext(), tmpAccount, (firstName, lastName) -> {
                    tmpAccount.setFirstName(firstName);
                    tmpAccount.setLastName(lastName);
                    setData();
                }).create(null);
                break;
            case R.id.cv_phone:
                new EnterPhoneDialog(requireContext(), tmpAccount, phone -> {
                    tmpAccount.setPhone(phone);
                    setData();
                }).create(null);
                break;
            case R.id.cv_password:
                new EnterChangePasswordDialog(requireContext(), tmpAccount, tmpAccount::setPassword).create(null);
                break;
            case R.id.cv_gender:
                new ChooseGenderDialog(requireContext(), gender -> {
                    tmpAccount.setGender(gender);
                    setData();
                }).create(null);
                break;
        }
    }

    private void setData() {
        tvName.setText(tmpAccount.getFirstName() + " " + tmpAccount.getLastName());
        tvPhone.setText(TextUtils.isEmpty(tmpAccount.getPhone()) ? "- - - - - -" : tmpAccount.getPhone());
        tvGender.setText(tmpAccount.getGender() == 0 ? "Male" : "Female");
    }

    private boolean isDataChange() {
        if (tmpAccount == null) {
            return false;
        }
        Account realAcc = App.getApp().getAccount();
        if (!Objects.equals(realAcc.getFirstName(), tmpAccount.getFirstName())) {
            return true;
        }
        if (!Objects.equals(realAcc.getLastName(), tmpAccount.getLastName())) {
            return true;
        }
        if (!Objects.equals(realAcc.getPhone(), tmpAccount.getPhone())) {
            return true;
        }
        if (!Objects.equals(realAcc.getPassword(), tmpAccount.getPassword())) {
            return true;
        }
        return !Objects.equals(realAcc.getGender(), tmpAccount.getGender());
    }

    @Override
    public boolean onBackPressed() {
        showToast(""+isDataChange(), ToastUtils.INFO);
        if (isDataChange()) {
            new ConfirmDialog(requireContext(),
                    R.drawable.ic_discard,
                    "Do you want to discard change?",
                    new ConfirmDialog.OnConfirmListener() {
                        @Override
                        public void onConfirm(BaseOverlayDialog overlayDialog) {
                            overlayDialog.dismiss();
                            popLastFragment();
                        }

                        @Override
                        public void onCancel(BaseOverlayDialog overlayDialog) {
                            overlayDialog.dismiss();
                        }
                    }).create(null);
            return true;
        }
        return false;
    }

    @Override
    public void onSuccess() {
        tmpAccount = null;
        showToast("Saved!", ToastUtils.SUCCESS, true);
    }

    @Override
    public void onError(Throwable throwable) {
        showToast("Save error!", ToastUtils.ERROR, true);
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
