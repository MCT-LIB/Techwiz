package com.csupporter.techwiz.presentation.view.fragment.docters.nav;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.presentation.presenter.doctor.DoctorProfilePresenter;
import com.csupporter.techwiz.presentation.view.activities.AuthenticateActivity;
import com.csupporter.techwiz.presentation.view.dialog.ConfirmDialog;
import com.mct.components.baseui.BaseFragment;
import com.mct.components.baseui.BaseOverlayDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DtProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DtProfileFragment extends BaseFragment implements View.OnClickListener{

    private View view;
    private RelativeLayout itemProfile, itemLogout;
    private DoctorProfilePresenter doctorProfilePresenter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DtProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DtProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DtProfileFragment newInstance(String param1, String param2) {
        DtProfileFragment fragment = new DtProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dt_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        eventClick();
        doctorProfilePresenter = new DoctorProfilePresenter(this);
    }

    private void eventClick() {
        itemProfile.setOnClickListener(this);
        itemLogout.setOnClickListener(this);
    }

    private void initView(View view) {
        itemProfile = view.findViewById(R.id.item_profile);
        itemLogout = view.findViewById(R.id.item_logout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_profile:

                break;
            case R.id.item_logout:
                if (getActivity() != null){
                    new ConfirmDialog(getActivity(), R.drawable.ic_logout, getString(R.string.dialog_confirm_logout_msg), new ConfirmDialog.OnConfirmListener() {
                        @Override
                        public void onConfirm(BaseOverlayDialog overlayDialog) {
                            doctorProfilePresenter.logOut();
                            Intent intent = new Intent(getContext(), AuthenticateActivity.class);
                            startActivity(intent);
                            if (getActivity() != null) {
                                getActivity().finish();
                            }
                        }

                        @Override
                        public void onCancel(BaseOverlayDialog overlayDialog) {
                            overlayDialog.dismiss();
                        }
                    }).create(null);
                }
                break;
        }
    }
}