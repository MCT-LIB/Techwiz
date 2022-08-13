package com.csupporter.techwiz.presentation.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.HealthTracking;
import com.mct.components.baseui.BaseOverlayDialog;

public class AddNewHealthTracking extends BaseOverlayDialog {

    private EditText height;
    private EditText weight;
    private EditText heartBeat;
    private EditText bloodSugar;
    private EditText bloodPressure;
    private EditText note;
    private AppCompatButton btnAddNew;

    private OnClickAddNewHealthTracking onClickAddNewHealthTracking;

    public AddNewHealthTracking(@NonNull Context context, OnClickAddNewHealthTracking onClickAddNewHealthTracking) {
        super(context);
        this.onClickAddNewHealthTracking = onClickAddNewHealthTracking;
    }

    public interface OnClickAddNewHealthTracking {
        void onClickAddNew(String txtHeight, String txtWeight,
                           String txtHeartBeat, String txtBloodSugar,
                           String txtBloodPressure, String txtNote);
    }

    @NonNull
    @Override
    protected AlertDialog.Builder onCreateDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.add_health_tracking, null);

        init(view);
        addEventAddClick();

        return new AlertDialog.Builder(context)
                .setCancelable(false)
                .setView(view);
    }

    private void init(View view) {
        height = view.findViewById(R.id.edt_height);
        weight = view.findViewById(R.id.edt_weight);
        bloodSugar = view.findViewById(R.id.edt_blood_sugar);
        bloodPressure = view.findViewById(R.id.edt_blood_pressure);
        heartBeat = view.findViewById(R.id.edt_heart_beat);
        note = view.findViewById(R.id.edt_note_health);
        btnAddNew = view.findViewById(R.id.btn_add_health_tracking);

    }

    private void addEventAddClick() {

        btnAddNew.setOnClickListener(view -> {

            String txtHeight = height.getText().toString();
            String txtWeight = weight.getText().toString();
            String txtBloodSugar = bloodSugar.getText().toString();
            String txtBloodPressure = bloodPressure.getText().toString();
            String txtHeartBeat = heartBeat.getText().toString();
            String txtNote = note.getText().toString();

            onClickAddNewHealthTracking.onClickAddNew(txtHeight, txtWeight, txtHeartBeat, txtBloodSugar, txtBloodPressure, txtNote);
        });
    }

    @Override
    protected void onDialogCreated(@NonNull AlertDialog dialog) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.getWindow().

                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().

                setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().

                getAttributes().windowAnimations = androidx.appcompat.R.style.Base_Animation_AppCompat_DropDownUp;
        dialog.getWindow().

                setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(true);
    }


}
