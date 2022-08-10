package com.csupporter.techwiz.presentation.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csupporter.techwiz.R;
import com.google.android.material.textfield.TextInputLayout;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseFragment;


public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private TextInputLayout tvUserName;
    private TextInputLayout tvPassword;
    private TextView tvRegisterNow;
    private AppCompatButton btnLogin;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
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
        tvRegisterNow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_register_now) {
            replaceFragment(new ChooseObjectFragment(), true, BaseActivity.Anim.RIGHT_IN_LEFT_OUT);
        } else if (id == R.id.tv_forgot_password) {

        }
    }
}