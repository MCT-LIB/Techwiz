package com.csupporter.techwiz.presentation.view.fragment.main.prescription;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.csupporter.techwiz.R;
import com.mct.components.baseui.BaseFragment;

public class ListPrescriptionDetailFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView imgAvatar;
    private TextView tvNamePerson, tvDateTime, tvPhoneNum;
    private RecyclerView rcvListDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_prescription_detail, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        evenClick();
        setData();
    }

    private void setData() {

    }

    private void evenClick() {

    }

    private void initView(View view) {
        imgAvatar = view.findViewById(R.id.img_avatar);
        tvNamePerson = view.findViewById(R.id.tv_name_person);
        tvDateTime = view.findViewById(R.id.tv_date_time);
        tvPhoneNum = view.findViewById(R.id.tv_phone_num);
        rcvListDescription = view.findViewById(R.id.rcv_list_description_detail);
    }

    @Override
    public void onClick(View v) {

    }
}
