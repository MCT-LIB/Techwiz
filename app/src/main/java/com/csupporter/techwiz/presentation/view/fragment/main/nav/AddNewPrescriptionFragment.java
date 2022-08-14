package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.presentation.view.fragment.main.nav.BaseNavFragment;


public class AddNewPrescriptionFragment extends BaseNavFragment {

    private EditText edtTimePerDay, edtTimePerWeek, edtQuantity, edtMedicineName;

    private TextView tvChooseDoctor;
    private ImageView imgMedicine;
    private AppCompatButton btnChooseImage;
    private AppCompatButton btnCreateNewPrescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_prescription, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        edtTimePerWeek = view.findViewById(R.id.edt_time_per_a_week);
        edtTimePerDay = view.findViewById(R.id.edt_time_per_a_day);
        edtQuantity = view.findViewById(R.id.edt_quantity);
        edtMedicineName = view.findViewById(R.id.edt_medicine_name);
        tvChooseDoctor = view.findViewById(R.id.choose_doctor);
        imgMedicine = view.findViewById(R.id.img_medicine);
        btnChooseImage = view.findViewById(R.id.btnChooseImage);
        btnCreateNewPrescription = view.findViewById(R.id.btn_create_new_prescription);
    }
}