package com.csupporter.techwiz.presentation.view.fragment.main.prescriotion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csupporter.techwiz.R;


public class AllPrescriptionFragment extends Fragment implements View.OnClickListener {

    private View view;
    private RecyclerView rcvListPrescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_prescriotion, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        eventCLick();
        setData();
    }

    private void setData() {
    }


    private void eventCLick() {
    }

    private void initView(View view) {
        rcvListPrescription = view.findViewById(R.id.rcv_list_prescription);
    }

    @Override
    public void onClick(View v) {

    }
}