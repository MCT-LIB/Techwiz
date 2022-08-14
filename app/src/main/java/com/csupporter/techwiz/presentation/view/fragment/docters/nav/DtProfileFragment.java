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
import com.csupporter.techwiz.presentation.view.fragment.profile.ProfileFragment;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseFragment;
import com.mct.components.baseui.BaseOverlayDialog;


public class DtProfileFragment extends BaseFragment implements View.OnClickListener{

    private View view;
    private RelativeLayout itemProfile, itemLogout;
    private DoctorProfilePresenter doctorProfilePresenter;

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
                replaceFragment(new ProfileFragment(), true, BaseActivity.Anim.RIGHT_IN_LEFT_OUT);
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