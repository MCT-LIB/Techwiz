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

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.presentation.presenter.EnterOTPPresenter;
import com.csupporter.techwiz.presentation.presenter.ViewCallback;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseFragment;

public class EnterOTPFragment extends BaseFragment implements View.OnClickListener, ViewCallback.EnterOTPCallBack {
    private static final String EMAIL_KEY = "EMAIL_KEY";
    private int otpSource;

    private String email;
    View view;
    EditText edtDigitCode_1,edtDigitCode_2,edtDigitCode_3,edtDigitCode_4,edtDigitCode_5,edtDigitCode_6;
    TextView tvResentOTP;
    Button btnVerifyCode;
    private EnterOTPPresenter enterOTPPresenter;


    public static EnterOTPFragment newInstance(String email) {
        Bundle args = new Bundle();
        args.putString(EMAIL_KEY, email);
        EnterOTPFragment fragment = new EnterOTPFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        enterOTPPresenter = new EnterOTPPresenter(this);
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
        view = inflater.inflate(R.layout.fragment_enter_otp, container, false);
        email = requireArguments().get(EMAIL_KEY).toString();
        enterOTPPresenter.sentOTP(email, this);
        Toast.makeText(getActivity(), "Sent OTP to " + email, Toast.LENGTH_SHORT).show();
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
                if (getOTPCodeUser() == otpSource)
                replaceFragment(new ResetPasswordFragment(), true, BaseActivity.Anim.RIGHT_IN_LEFT_OUT);
                break;
            case R.id.tv_resent_otp:
                enterOTPPresenter.sentOTP(email, this);
                Toast.makeText(getActivity(), "Resented OTP to : " + email, Toast.LENGTH_SHORT).show();
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

    public int getOTPCodeUser(){
        int input;

        String otp_1 = edtDigitCode_1.getText().toString().trim();
        String otp_2 = edtDigitCode_2.getText().toString().trim();
        String otp_3 = edtDigitCode_3.getText().toString().trim();
        String otp_4 = edtDigitCode_4.getText().toString().trim();
        String otp_5 = edtDigitCode_5.getText().toString().trim();
        String otp_6 = edtDigitCode_6.getText().toString().trim();

        String otp = otp_1 + otp_2 + otp_3 + otp_4 + otp_5 + otp_6;

        input = Integer.parseInt(otp);

        return input;
    }

    @Override
    public void onSentOTPSuccess(int OTP) {

        Toast.makeText(getActivity(), "sent otp success" + OTP, Toast.LENGTH_SHORT).show();
//        replaceFragment(new ResetPasswordFragment(), true, BaseActivity.Anim.RIGHT_IN_LEFT_OUT);
    }

    @Override
    public void onSentOTPFailure() {
        Toast.makeText(getActivity(), "sent otp failure", Toast.LENGTH_SHORT).show();
    }
}
