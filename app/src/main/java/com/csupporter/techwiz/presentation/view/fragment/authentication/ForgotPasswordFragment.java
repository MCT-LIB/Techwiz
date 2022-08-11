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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.presentation.presenter.ForgotPasswordPresenter;
import com.csupporter.techwiz.presentation.presenter.SendOtpPresenter;
import com.csupporter.techwiz.presentation.presenter.ViewCallback;
import com.csupporter.techwiz.presentation.view.dialog.LoadingDialog;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseFragment;

public class ForgotPasswordFragment extends BaseFragment implements View.OnClickListener, ViewCallback.ForgotPasswordCallBack, ViewCallback.EnterOTPCallBack {
    private LoadingDialog dialog;
    private View view;
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
        view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        return view;
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
        sendOtpPresenter.sentOTP(account, this);

    }

    @Override
    public void emailNull() {
        Toast.makeText(getActivity(), "Email cannot be blank!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void emailNotExist() {
        Toast.makeText(getActivity(), "Email not exits!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSentOTPSuccess(int OTP) {
        Fragment fragment = EnterOTPFragment.newInstance(account, OTP, EnterOTPFragment.FROM_FORGOT_PW);
        replaceFragment(fragment, true, BaseActivity.Anim.RIGHT_IN_LEFT_OUT);
    }

    @Override
    public void onSentOTPFailure() {

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
