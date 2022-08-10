package com.csupporter.techwiz.presentation.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csupporter.techwiz.R;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseFragment;


public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvRegisterNow;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        tvRegisterNow = view.findViewById(R.id.tv_register_now);
        tvRegisterNow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_register_now) {
            replaceFragment(new ChooseObjectFragment(), true, BaseActivity.Anim.RIGHT_IN_LEFT_OUT);
        }else if(id == R.id.tv_forgot_password){

        }
    }
}