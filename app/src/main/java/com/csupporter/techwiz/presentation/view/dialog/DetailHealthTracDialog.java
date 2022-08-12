package com.csupporter.techwiz.presentation.view.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.csupporter.techwiz.R;
import com.mct.components.baseui.BaseOverlayDialog;

public class DetailHealthTracDialog extends BaseOverlayDialog {
    private View view;
    private TextView tvName, tvAge, tvHeight, tvWeight, tvGender, tv_heart_beat, tv_blood_pressure, tv_blood_sugar, tvOther;


    public DetailHealthTracDialog(@NonNull Context context) {
        super(context);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    protected AlertDialog.Builder onCreateDialog() {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_detail_health_track, null);
        return new AlertDialog.Builder(context)
                .setCancelable(false)
                .setView(view);
    }

    @Override
    protected int getCornerRadius() {
        return 16;
    }


    @Override
    protected void onDialogCreated(@NonNull AlertDialog dialog) {
        initView(view);
    }

    private void initView(View view) {
        tvName = view.findViewById(R.id.tv_name);
        tvAge = view.findViewById(R.id.tv_age);
        tvHeight = view.findViewById(R.id.tv_height);
        tvWeight = view.findViewById(R.id.tv_weight);
        tvGender = view.findViewById(R.id.tv_gender);
        tv_heart_beat = view.findViewById(R.id.tv_heart_beat);
        tv_blood_pressure = view.findViewById(R.id.tv_blood_pressure);
        tv_blood_sugar = view.findViewById(R.id.tv_blood_sugar);
        tvOther = view.findViewById(R.id.tv_other);
    }
}
