package com.csupporter.techwiz.presentation.view.fragment.authentication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.presentation.presenter.authentication.ForgotPasswordPresenter;
import com.csupporter.techwiz.presentation.presenter.authentication.SendOtpPresenter;
import com.csupporter.techwiz.presentation.presenter.AuthenticationCallback;
import com.csupporter.techwiz.presentation.view.dialog.LoadingDialog;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseFragment;
import com.mct.components.toast.ToastUtils;

public class ForgotPasswordFragment extends BaseFragment implements View.OnClickListener, AuthenticationCallback.ForgotPasswordCallBack, AuthenticationCallback.EnterOTPCallBack {
    private LoadingDialog dialog;
    private Button btnSubmit;
    private EditText edtEnterEmail;
    private TextView tvBackToLogin;
    private Account account;

    private ForgotPasswordPresenter forgotPasswordPresenter;
    private SendOtpPresenter sendOtpPresenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        forgotPasswordPresenter = new ForgotPasswordPresenter(this);
        sendOtpPresenter = new SendOtpPresenter(this);
        requireActivity().getWindow().setBackgroundDrawableResource(R.drawable.forgot_password_background);
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().getWindow().setBackgroundDrawableResource(R.drawable.forgot_password_background);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        eventClick();
    }

    private void eventClick() {
        btnSubmit.setOnClickListener(this);
        tvBackToLogin.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_otp:
                hideSoftInput();
                String email = edtEnterEmail.getText().toString().trim();
                forgotPasswordPresenter.checkEmailExist(email, this);

                break;
            case R.id.tv_back_to_login:
                replaceFragment(new LoginFragment(), true, BaseActivity.Anim.LEFT_IN_RIGHT_OUT);
                break;
        }
    }

    private void initView(View view) {
        btnSubmit = view.findViewById(R.id.btn_get_otp);
        edtEnterEmail = view.findViewById(R.id.edt_enter_email);
        tvBackToLogin = view.findViewById(R.id.tv_back_to_login);
    }

    @Override
    public void emailExist(Account account) {
        this.account = account;
        sendOtpPresenter.sentForgotPassOtp(account, this);
    }

    @Override
    public void emailNull() {
        showToast("Email cannot be blank!", ToastUtils.WARNING);
    }

    @Override
    public void emailNotExist() {
        showToast("Email not exits!", ToastUtils.ERROR);
    }

    @Override
    public void onSentOTPSuccess(int OTP) {
        if (getContext() != null) {
            Fragment fragment = EnterOTPFragment.newInstance(account, OTP, EnterOTPFragment.FROM_FORGOT_PW);
            replaceFragment(fragment, true, BaseActivity.Anim.RIGHT_IN_LEFT_OUT);
        }
    }

    @Override
    public void onSentOTPFailure() {

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
