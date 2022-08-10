package com.csupporter.techwiz.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csupporter.techwiz.R;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseFragment;

public class ChooseObjectFragment extends BaseFragment implements View.OnClickListener {

    private AppCompatButton btnDoctor;
    private AppCompatButton btnUser;
    private Toolbar toolbar;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        requireActivity().getWindow().setBackgroundDrawableResource(R.drawable.choose_object_background);
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().getWindow().setBackgroundDrawableResource(R.drawable.choose_object_background);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_object, container, false);
        init(view);
        eventOnClick();
        return view;
    }

    private void init(@NonNull View view) {
        btnDoctor = view.findViewById(R.id.btn_doctor);
        btnUser = view.findViewById(R.id.btn_user);
        toolbar = view.findViewById(R.id.toolbar);
    }

    private void eventOnClick() {
        btnUser.setOnClickListener(this);

        btnDoctor.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(view -> popLastFragment());
    }

    @Override
    public void onClick(@NonNull View view) {
        int id = view.getId();
        if (id == R.id.btn_doctor) {

        } else if (id == R.id.btn_user) {
            replaceFragment(new RegisterFragment(), true, BaseActivity.Anim.RIGHT_IN_LEFT_OUT);
        }

    }
}