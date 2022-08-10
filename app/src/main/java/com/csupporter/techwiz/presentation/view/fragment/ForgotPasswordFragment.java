package com.csupporter.techwiz.presentation.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.csupporter.techwiz.R;
import com.google.android.material.textfield.TextInputEditText;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseFragment;

public class ForgotPasswordFragment extends BaseFragment implements View.OnClickListener{
    View view;
    Button btnSubmit;
    EditText edtEnterEmail;
    TextView tvBackToLogin;

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
        switch (v.getId()){
            case R.id.btn_submit:
                showToast(edtEnterEmail.getText().toString(), 1, false);
                break;
            case R.id.tv_back_to_login:
                replaceFragment(new LoginFragment(), true, BaseActivity.Anim.LEFT_IN_RIGHT_OUT);
                break;
        }
    }

    private void initView(View view) {
        btnSubmit = view.findViewById(R.id.btn_submit);
        edtEnterEmail = view.findViewById(R.id.edt_enter_email);
        tvBackToLogin = view.findViewById(R.id.tv_back_to_login);
    }
}
