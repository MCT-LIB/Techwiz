package com.csupporter.techwiz.presentation.view.fragment;

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

import com.csupporter.techwiz.R;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseFragment;

import org.w3c.dom.Text;

public class EnterOTPFragment extends BaseFragment implements View.OnClickListener{
    View view;
    EditText edtDigitCode_1,edtDigitCode_2,edtDigitCode_3,edtDigitCode_4,edtDigitCode_5,edtDigitCode_6;
    TextView tvResentOTP;
    Button btnVerifyCode;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.forgot_password_background);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.forgot_password_background);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_enter_otp, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intiView(view);
        eventClick();
    }

    private void eventClick() {
        btnVerifyCode.setOnClickListener(this);
        tvResentOTP.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_verify_code:
                showToast("verify code", 1, false);
                break;
            case R.id.tv_resent_otp:
                showToast("resent code", 1, false);
                break;
        }
    }

    private void intiView(View view) {
        edtDigitCode_1 = view.findViewById(R.id.edt_digit_code_1);
        edtDigitCode_2 = view.findViewById(R.id.edt_digit_code_2);
        edtDigitCode_3 = view.findViewById(R.id.edt_digit_code_3);
        edtDigitCode_4 = view.findViewById(R.id.edt_digit_code_4);
        edtDigitCode_5 = view.findViewById(R.id.edt_digit_code_5);
        edtDigitCode_6 = view.findViewById(R.id.edt_digit_code_6);

        tvResentOTP = view.findViewById(R.id.tv_resent_otp);
        btnVerifyCode = view.findViewById(R.id.btn_verify_code);
    }
}
