package com.csupporter.techwiz.presentation.view.fragment.authentication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

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
import com.csupporter.techwiz.presentation.presenter.ViewCallback;
import com.csupporter.techwiz.presentation.view.dialog.LoadingDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.mct.components.baseui.BaseFragment;
import com.mct.components.toast.ToastUtils;

import java.time.LocalDateTime;

public class RegisterFragment extends BaseFragment implements View.OnClickListener, ViewCallback.RegisterCallBack {

    private TextInputLayout txtFirstName;
    private TextInputLayout txtLastName;
    private TextInputLayout txtEmail;
    private TextInputLayout txtPassword;
    private TextInputLayout txtConfPassword;

    private LoadingDialog dialog;

    private RegisterPresenter registerPresenter;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        registerPresenter = new RegisterPresenter(this);
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
        init(view);
        return view;
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
        txtFirstName.setError("Please enter your first name");
    }

    @Override
    public void onClick(@NonNull View view) {
        int id = view.getId();
        if (id == R.id.btn_register) {
            Account account = getDataFromForm();
            String confPass = txtConfPassword.getEditText().getText().toString();
            registerPresenter.register(account, confPass, this);
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
        acc.setType(2);
        acc.setPassword(password);
        return acc;
    }


    @Override
    public void dataInvalid(String alert, ViewCallback.ErrorTo errorTo, boolean showToast) {
        if (showToast) {
            showToast(alert, ToastUtils.ERROR, true);
        } else {
            switch (errorTo){
                case NONE:
                    return;
                case FIRST_NAME:

            }
        }
    }

    @Override
    public void registerSuccess() {
        hideLoading();
        ToastUtils.makeSuccessToast(getActivity(), Toast.LENGTH_SHORT, "Register Success!", true).show();
    }

    @Override
    public void registerError() {
        hideLoading();
        ToastUtils.makeErrorToast(getActivity(), Toast.LENGTH_SHORT, "Register Fail!", true).show();
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