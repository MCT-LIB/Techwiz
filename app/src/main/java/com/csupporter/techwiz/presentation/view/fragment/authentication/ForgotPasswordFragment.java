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
import com.csupporter.techwiz.presentation.presenter.ForgotPasswordPresenter;
import com.csupporter.techwiz.presentation.presenter.ViewCallback;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseFragment;

public class ForgotPasswordFragment extends BaseFragment implements View.OnClickListener,ViewCallback.ForgotPasswordCallBack {
    View view;
    Button btnSubmit;
    EditText edtEnterEmail;
    TextView tvBackToLogin;

    private ForgotPasswordPresenter forgotPasswordPresenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        forgotPasswordPresenter = new ForgotPasswordPresenter(this);
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
        switch (v.getId()){
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
    public void emailExist() {
        String email = edtEnterEmail.getText().toString().trim();

        Fragment fragment = EnterOTPFragment.newInstance(email);
        replaceFragment(fragment, true, BaseActivity.Anim.RIGHT_IN_LEFT_OUT);
    }

    @Override
    public void emailNull() {
        Toast.makeText(getActivity(), "Email cannot be blank!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void emailNotExist() {
        Toast.makeText(getActivity(), "Email not exits!", Toast.LENGTH_SHORT).show();
    }
}
