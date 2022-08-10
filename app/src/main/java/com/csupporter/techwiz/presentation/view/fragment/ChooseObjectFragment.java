package com.csupporter.techwiz.presentation.view.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csupporter.techwiz.R;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseFragment;


public class ChooseObjectFragment extends BaseFragment implements View.OnClickListener {

    private AppCompatButton radioDoctor;
    private AppCompatButton radioUser;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_object, container, false);
        init(view);
        eventOnClick();
        return view;
    }

    private void init(View view) {
        radioDoctor = view.findViewById(R.id.btn_doctor);
        radioUser = view.findViewById(R.id.btn_user);
        toolbar = view.findViewById(R.id.toolbar);
    }

    private void eventOnClick() {
        radioUser.setOnClickListener(this);

        radioDoctor.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(view -> popLastFragment());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_doctor) {

        } else if (id == R.id.btn_user) {
            replaceFragment(new RegisterFragment(), true, BaseActivity.Anim.RIGHT_IN_LEFT_OUT);
        }

    }
}