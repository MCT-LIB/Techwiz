package com.csupporter.techwiz.presentation.view.fragment.authentication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.repository.AccountRepository;
import com.csupporter.techwiz.presentation.presenter.ResetPasswordPresenter;
import com.csupporter.techwiz.presentation.presenter.ViewCallback;
import com.mct.components.baseui.BaseFragment;

public class ResetPasswordFragment extends BaseFragment implements View.OnClickListener, ViewCallback.ResetPasswordCallBack {
    private static final String KEY_ACCOUNT = "KEY_ACCOUNT";

    private ResetPasswordPresenter resetPasswordPresenter;

    private Account account;
    EditText  edtEnterNewPw, edtReenterNewPw;
    Button btnConfirm;

    View view;

    public static ResetPasswordFragment newInstance(Account account) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_ACCOUNT, account);
        ResetPasswordFragment fragment = new ResetPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        resetPasswordPresenter = new ResetPasswordPresenter(this);
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
        view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        account = (Account) requireArguments().getSerializable(KEY_ACCOUNT);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intiView(view);
        eventClick();

    }

    private void eventClick() {
        btnConfirm.setOnClickListener(this);
    }

    private void intiView(View view) {
        edtEnterNewPw = view.findViewById(R.id.edt_enter_new_pw);
        edtReenterNewPw = view.findViewById(R.id.edt_reenter_new_pw);
        btnConfirm = view.findViewById(R.id.btn_confirm);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_confirm) {

            String newPass = edtEnterNewPw.getText().toString().trim();
            String reNewPass = edtReenterNewPw.getText().toString().trim();
            resetPasswordPresenter.checkReEnterPassword(newPass, reNewPass, this);
        }
    }

    @Override
    public void onSuccess(String pw) {
        account.setPassword(pw);
        DataInjection.provideRepository().account.updateAccount(account, new Consumer<Void>() {
            @Override
            public void accept(Void unused) {
                showToast("Change password successfully", 1);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                showToast("Some thing wrong!! Let's try again! ", 1);
            }
        });
    }

    @Override
    public void onFailure() {
        showToast("Some thing wrong !! Can't change password" , 1);
    }

    @Override
    public void onEmptyValue() {
        showToast("You can't leave it blank" , 1);
    }
}
