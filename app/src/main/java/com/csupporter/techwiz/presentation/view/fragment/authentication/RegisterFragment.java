package com.csupporter.techwiz.presentation.view.fragment.authentication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.data.firebase_source.FirebaseUtils;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.presentation.presenter.RegisterPresenter;
import com.csupporter.techwiz.presentation.presenter.AuthenticationCallback;
import com.csupporter.techwiz.presentation.presenter.authentication.SendOtpPresenter;
import com.csupporter.techwiz.presentation.view.dialog.LoadingDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseFragment;
import com.mct.components.toast.ToastUtils;

import java.time.LocalDateTime;

public class RegisterFragment extends BaseFragment implements View.OnClickListener, AuthenticationCallback.EnterOTPCallBack, AuthenticationCallback.VerifyAccountCallBack {

    public static final String KEY_TYPE = "key_type";
    private TextInputLayout txtFirstName;
    private TextInputLayout txtLastName;
    private TextInputLayout txtEmail;
    private TextInputLayout txtPassword;
    private TextInputLayout txtConfPassword;

    private LoadingDialog dialog;
    private Account account;
    private int type;

    private RegisterPresenter registerPresenter;
    private SendOtpPresenter sendOtpPresenter;
    private boolean isDispose;

    @NonNull
    public static RegisterFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(KEY_TYPE, type);
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        registerPresenter = new RegisterPresenter(this);
        sendOtpPresenter = new SendOtpPresenter(this);
        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.register_background);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.register_background);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        type = requireArguments().getInt(KEY_TYPE);
        init(view);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        isDispose = true;
    }

    private void init(@NonNull View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view1 -> popLastFragment());
        txtEmail = view.findViewById(R.id.register_email_layout);
        txtFirstName = view.findViewById(R.id.register_firstname_layout);
        txtLastName = view.findViewById(R.id.register_lastname_layout);
        txtPassword = view.findViewById(R.id.register_password_layout);
        txtConfPassword = view.findViewById(R.id.register_cf_password_layout);
        AppCompatButton btnRegister = view.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(@NonNull View view) {
        int id = view.getId();
        if (id == R.id.btn_register) {
            account = getDataFromForm();
            String confPass = txtConfPassword.getEditText().getText().toString();
            isDispose = false;
            registerPresenter.verifyAccount(account, confPass, this);
        }
    }

    @NonNull
    private Account getDataFromForm() {
        String firstName = txtFirstName.getEditText().getText().toString().trim();
        String lastName = txtLastName.getEditText().getText().toString().trim();
        String email = txtEmail.getEditText().getText().toString().trim();
        String password = txtPassword.getEditText().getText().toString().trim();
        Account acc = new Account();
        acc.setId(FirebaseUtils.uniqueId());
        acc.setFirstName(firstName);
        acc.setLastName(lastName);
        acc.setEmail(email);
        acc.setType(type);
        acc.setPassword(password);
        return acc;
    }

    @Override
    public boolean isDispose() {
        return isDispose;
    }

    @Override
    public void dataInvalid(String alert, @NonNull AuthenticationCallback.ErrorTo errorTo) {
        if (getContext() == null) return;
        switch (errorTo) {
            case NONE:
                break;
            case FIRST_NAME:
                showSoftInput(txtFirstName.getEditText());
                break;
            case EMAIL:
                showSoftInput(txtEmail.getEditText());
                break;
            case PASSWORD:
                showSoftInput(txtPassword.getEditText());
                break;
            case CF_PASSWORD:
                showSoftInput(txtConfPassword.getEditText());
                break;
        }
        showToast(alert, ToastUtils.ERROR, true);
    }

    @Override
    public void verified() {
        if (getContext() != null) {
            hideSoftInput();
            sendOtpPresenter.sendVerificationOtp(account, this);
        }
    }

    @Override
    public void onSentOTPSuccess(int OTP) {
        if (getContext() != null) {
            showToast("An otp had been sent. Please check your email!", ToastUtils.INFO);
            Fragment fragment = EnterOTPFragment.newInstance(account, OTP, EnterOTPFragment.FROM_REGISTER);
            replaceFragment(fragment, true, BaseActivity.Anim.TRANSIT_FADE);
        }
    }

    @Override
    public void onSentOTPFailure() {
        showToast("Sent otp false", ToastUtils.ERROR);
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