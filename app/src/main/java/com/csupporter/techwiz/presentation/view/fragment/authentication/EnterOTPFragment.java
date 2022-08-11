package com.csupporter.techwiz.presentation.view.fragment.authentication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.csupporter.techwiz.presentation.presenter.RegisterPresenter;
import com.csupporter.techwiz.presentation.presenter.SendOtpPresenter;
import com.csupporter.techwiz.presentation.presenter.ViewCallback;
import com.csupporter.techwiz.presentation.view.dialog.LoadingDialog;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseFragment;
import com.mct.components.toast.ToastUtils;

public class EnterOTPFragment extends BaseFragment implements View.OnClickListener, ViewCallback.EnterOTPCallBack, ViewCallback.RegisterCallBack {
    private static final String KEY_ACCOUNT = "KEY_ACCOUNT";
    private static final String KEY_OTP = "KEY_OTP";
    private static final String KEY_FROM = "KEY_FROM";

    public static final int FROM_FORGOT_PW = 1;
    public static final int FROM_REGISTER = 2;

    private EditText edtDigitCode_1, edtDigitCode_2, edtDigitCode_3, edtDigitCode_4, edtDigitCode_5, edtDigitCode_6;
    private Button btnResentOTP;
    private TextView tvDurationOfOtp;
    private Button btnVerifyCode;
    private SendOtpPresenter sendOTPPresenter;
    private RegisterPresenter registerPresenter;
    private LoadingDialog dialog;
    private Account account;
    private int otp;
    private int from;
    private int timeless;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable r = new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            tvDurationOfOtp.setText(--timeless + "s");
            if (timeless <= 0) {
                ((View) btnResentOTP.getParent()).setVisibility(View.VISIBLE);
                btnVerifyCode.setEnabled(false);
                handler.removeCallbacks(this);
            } else {
                handler.postDelayed(this, 1000);
            }
        }
    };

    @NonNull
    public static EnterOTPFragment newInstance(Account account, int otp, int from) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_ACCOUNT, account);
        args.putInt(KEY_OTP, otp);
        args.putInt(KEY_FROM, from);
        EnterOTPFragment fragment = new EnterOTPFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sendOTPPresenter = new SendOtpPresenter(this);
        registerPresenter = new RegisterPresenter(this);
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
        View view = inflater.inflate(R.layout.fragment_enter_otp, container, false);
        account = (Account) requireArguments().getSerializable(KEY_ACCOUNT);
        otp = requireArguments().getInt(KEY_OTP);
        from = requireArguments().getInt(KEY_FROM);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intiView(view);
        eventClick();
        startTimer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    private void eventClick() {
        btnVerifyCode.setOnClickListener(this);
        btnResentOTP.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(@NonNull View v) {
        switch (v.getId()) {
            case R.id.btn_verify_code:
                if (getOTPCodeUser() == otp) {
                    if (from == FROM_FORGOT_PW) {
                        Fragment fragment = ResetPasswordFragment.newInstance(account);
                        replaceFragment(fragment, false, BaseActivity.Anim.RIGHT_IN_LEFT_OUT);
                    }
                    if (from == FROM_REGISTER) {
                        registerPresenter.register(account, this);
                    }
                } else {
                    showToast("OTP is invalid!", ToastUtils.ERROR, true);
                }
                break;
            case R.id.btn_resent_otp:
                if (from == FROM_FORGOT_PW) {
                    sendOTPPresenter.sentForgotPassOtp(account, this);
                }
                if (from == FROM_REGISTER) {
                    sendOTPPresenter.sendVerificationOtp(account, this);
                }
                break;
        }
    }

    private void intiView(@NonNull View view) {
        edtDigitCode_1 = view.findViewById(R.id.edt_digit_code_1);
        edtDigitCode_2 = view.findViewById(R.id.edt_digit_code_2);
        edtDigitCode_3 = view.findViewById(R.id.edt_digit_code_3);
        edtDigitCode_4 = view.findViewById(R.id.edt_digit_code_4);
        edtDigitCode_5 = view.findViewById(R.id.edt_digit_code_5);
        edtDigitCode_6 = view.findViewById(R.id.edt_digit_code_6);

        btnResentOTP = view.findViewById(R.id.btn_resent_otp);
        tvDurationOfOtp = view.findViewById(R.id.tv_duration_of_otp);
        btnVerifyCode = view.findViewById(R.id.btn_verify_code);
    }

    public int getOTPCodeUser() {
        String otp_1 = edtDigitCode_1.getText().toString().trim();
        String otp_2 = edtDigitCode_2.getText().toString().trim();
        String otp_3 = edtDigitCode_3.getText().toString().trim();
        String otp_4 = edtDigitCode_4.getText().toString().trim();
        String otp_5 = edtDigitCode_5.getText().toString().trim();
        String otp_6 = edtDigitCode_6.getText().toString().trim();

        String otp = otp_1 + otp_2 + otp_3 + otp_4 + otp_5 + otp_6;

        return Integer.parseInt(otp);
    }

    private void startTimer() {
        timeless = 60;
        ((View) btnResentOTP.getParent()).setVisibility(View.INVISIBLE);
        btnVerifyCode.setEnabled(true);
        handler.post(r);
    }

    private void stopTimer() {
        handler.removeCallbacks(r);
    }

    @Override
    public void onSentOTPSuccess(int otp) {
        this.otp = otp;
        startTimer();
    }

    @Override
    public void onSentOTPFailure() {
        showToast("Send false!", ToastUtils.ERROR, true);
    }

    @Override
    public void registerSuccess() {
        showToast("Register Success!", ToastUtils.SUCCESS, true);
        popFragmentToPosition(0);
    }

    @Override
    public void registerError() {
        showToast("Register false!", ToastUtils.ERROR, true);
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
