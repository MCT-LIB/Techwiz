package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.csupporter.techwiz.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mct.components.baseui.BaseFragment;

public class NavHealthyTrackingFragment extends BaseFragment implements View.OnClickListener {

    private FloatingActionButton btnAddTrack;
    private EditText height;
    private EditText weight;
    private EditText heartBeat;
    private EditText bloodSugar;
    private EditText bloodPressure;
    private EditText note;
    private AppCompatButton btnAddNew;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_healthy_tracking, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        btnAddTrack = view.findViewById(R.id.btn_add_track);
        btnAddTrack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_track:
                createAddDialog();
                break;
        }
    }

    private void createAddDialog() {
        Dialog dialogBottom = new Dialog(getActivity());
        dialogBottom.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBottom.setContentView(R.layout.add_health_tracking);
        initItemDialog(dialogBottom);
        dialogBottom.show();
        customAddHealthDialog(dialogBottom);
    }

    private void initItemDialog(Dialog dialog) {
        height = dialog.findViewById(R.id.edt_height);
        weight = dialog.findViewById(R.id.edt_weight);
        bloodSugar = dialog.findViewById(R.id.blood_sugar);
        bloodPressure = dialog.findViewById(R.id.blood_pressure);
        heartBeat = dialog.findViewById(R.id.edt_heart_beat);
        btnAddNew = dialog.findViewById(R.id.btn_add_health_tracking);
    }

    private void customAddHealthDialog(Dialog dialog) {
        dialog.getWindow().

                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().

                setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().

                getAttributes().windowAnimations = androidx.appcompat.R.style.Base_Animation_AppCompat_DropDownUp;
        dialog.getWindow().

                setGravity(Gravity.BOTTOM);
    }
}