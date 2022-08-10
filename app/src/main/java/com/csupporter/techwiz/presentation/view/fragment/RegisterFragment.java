package com.csupporter.techwiz.presentation.view.fragment;

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


    private Toolbar toolbar;

    private TextInputLayout txtUserName;
    private TextInputLayout txtFirstName;
    private TextInputLayout txtLastName;
    private TextInputLayout txtEmail;
    private TextInputLayout txtContactNumber;
    private TextInputLayout txtAge;
    private TextInputLayout txtPassword;
    private AppCompatButton btnRegister;

    private RadioButton radioMale;
    private RadioButton radioFemale;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        init(view);
        return view;
    }

    private void init(View view) {

        toolbar = view.findViewById(R.id.toolbar);
        txtUserName = view.findViewById(R.id.register_username_layout);
        txtEmail = view.findViewById(R.id.register_email_layout);
        txtFirstName = view.findViewById(R.id.register_firstname_layout);
        txtLastName = view.findViewById(R.id.register_lastname_layout);
        txtContactNumber = view.findViewById(R.id.register_contact_number_layout);
        txtPassword = view.findViewById(R.id.register_password_layout);
        txtAge = view.findViewById(R.id.register_age_layout);
        radioMale = view.findViewById(R.id.radio_male);
        radioFemale = view.findViewById(R.id.radio_female);
        btnRegister = view.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(view1 -> popLastFragment());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_register:
                Account account = getDataFromForm();
                registerPresenter.register(account, this);
                break;
        }
    }

    private Account getDataFromForm() {

        String firstName = txtFirstName.getEditText().getText().toString().trim();
        String lastName = txtLastName.getEditText().getText().toString().trim();
        String userName = txtUserName.getEditText().getText().toString().trim();
        String email = txtEmail.getEditText().getText().toString().trim();
        String contactPhone = txtContactNumber.getEditText().getText().toString().trim();
        int gender = 2;
        if (radioMale.isChecked()) {
            gender = 0;
        } else if (radioFemale.isChecked()) {
            gender = 1;
        }
        int age ;

        if(TextUtils.isEmpty(txtAge.getEditText().getText().toString())){
             age = 0;
        }else{
            age = Integer.parseInt(txtAge.getEditText().getText().toString().trim());
        }
        String password = txtPassword.getEditText().getText().toString().trim();

        Account acc = new Account();
        acc.setId(FirebaseUtils.uniqueId());
        acc.setFirstName(firstName);
        acc.setLastName(lastName);
        acc.setUserName(userName);
        acc.setEmail(email);
        acc.setPhone(contactPhone);
        acc.setGender(gender);
        acc.setAge(age);
        acc.setType(2);
        acc.setPassword(password);
        return acc;
    }

    @Override
    public void dataInvalid(String alert) {

        ToastUtils.makeErrorToast(getActivity(), Toast.LENGTH_SHORT,alert+"",true).show();
    }

    @Override
    public void registerSuccess() {
        hideLoading();
        ToastUtils.makeSuccessToast(getActivity(), Toast.LENGTH_SHORT,"Register Success!",true).show();
    }

    @Override
    public void registerError() {
        hideLoading();
        ToastUtils.makeErrorToast(getActivity(), Toast.LENGTH_SHORT,"Register Fail!",true).show();
    }

    @Override
    public void showLoading() {
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