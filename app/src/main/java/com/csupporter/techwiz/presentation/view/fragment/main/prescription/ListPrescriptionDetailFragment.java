package com.csupporter.techwiz.presentation.view.fragment.main.prescription;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.Prescription;
import com.csupporter.techwiz.presentation.view.dialog.AddNewPrescriptionDialog;
import com.csupporter.techwiz.presentation.view.fragment.main.nav.AddNewPrescriptionFragment;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseFragment;

public class ListPrescriptionDetailFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView imgAvatar;
    private TextView tvNamePerson, tvDateTime, tvPhoneNum;
    private RecyclerView rcvListDescription;

    private Prescription prescription;

    public static ListPrescriptionDetailFragment newInstance(Prescription prescription) {
        Bundle args = new Bundle();
        args.putSerializable("prescription", prescription);
        ListPrescriptionDetailFragment fragment = new ListPrescriptionDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            prescription = (Prescription) bundle.getSerializable("prescription");
        }
    }

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
    }


    private void initView(View view) {

        Toolbar toolbar = view.findViewById(R.id.tb_toolbar);
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        toolbar.setOnMenuItemClickListener(item -> {
            replaceFragment(AddNewPrescriptionFragment.newInstance(prescription), true, BaseActivity.Anim.RIGHT_IN_LEFT_OUT);
            return false;
        });
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
