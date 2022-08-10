package com.csupporter.techwiz.presentation.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csupporter.techwiz.R;
import com.mct.components.baseui.BaseFragment;

public class RegisterFragment extends BaseFragment implements View.OnClickListener {


    private Toolbar toolbar;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
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
        toolbar.setNavigationOnClickListener(view1 -> popLastFragment());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

    }
}