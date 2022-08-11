package com.csupporter.techwiz.presentation.view.fragment.authentication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.presentation.presenter.LoginPresenter;
import com.csupporter.techwiz.presentation.presenter.ViewCallback;
import com.csupporter.techwiz.presentation.view.activities.MainActivity;
import com.csupporter.techwiz.presentation.view.dialog.LoadingDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseFragment;
import com.mct.components.toast.ToastUtils;


public class LoginFragment extends BaseFragment implements View.OnClickListener, ViewCallback.Login {

    private TextInputLayout tvUserName;
    private TextInputLayout tvPassword;
    private TextView tvRegisterNow;
    private AppCompatButton btnLogin;
    private TextView tvForgotPassword;
    private LoadingDialog dialog;

    private LoginPresenter loginPresenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        loginPresenter = new LoginPresenter(this);
        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.login_background);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.login_background);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        tvUserName = view.findViewById(R.id.login_username_layout);
        tvPassword = view.findViewById(R.id.login_password_layout);
        tvRegisterNow = view.findViewById(R.id.tv_register_now);
        tvForgotPassword = view.findViewById(R.id.tv_forgot_password);
        tvRegisterNow.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        btnLogin = view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_register_now) {
            replaceFragment(new ChooseObjectFragment(), true, BaseActivity.Anim.RIGHT_IN_LEFT_OUT);
        } else if (id == R.id.tv_forgot_password) {
            replaceFragment(new ForgotPasswordFragment(), true, BaseActivity.Anim.RIGHT_IN_LEFT_OUT);
        } else if (id == R.id.btn_login) {
            String userName = tvUserName.getEditText().getText().toString();
            String password = tvPassword.getEditText().getText().toString();
            loginPresenter.login(userName, password, this);
        }
    }

    @Override
    public void dataInvalid(String alert) {
        ToastUtils.makeWarningToast(getActivity(), Toast.LENGTH_SHORT, alert + "", true).show();
    }

    @Override
    public void loginSuccess() {
        if (getActivity() == null) {
            return;
        }

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
        ToastUtils.makeSuccessToast(getActivity(), Toast.LENGTH_SHORT, "Login Success!", true).show();
    }

    @Override
    public void loginError() {
        ToastUtils.makeErrorToast(getActivity(), Toast.LENGTH_SHORT, "Login Fail!", true).show();
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