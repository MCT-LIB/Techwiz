package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.csupporter.techwiz.presentation.presenter.main.NavUserInfoPresenter;
import com.csupporter.techwiz.presentation.view.activities.AuthenticateActivity;
import com.csupporter.techwiz.presentation.view.dialog.ConfirmDialog;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseOverlayDialog;
import com.mct.components.baseui.BaseView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class NavUserInfoFragment extends BaseNavFragment implements View.OnClickListener, BaseView, MainViewCallBack.NavUserInfoCallBack {

    private NavUserInfoPresenter navUserInfoPresenter;
    View view;
    TextView tvName, tvEmail;
    RelativeLayout itemLogout, itemHealthTrack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nav_user_info, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navUserInfoPresenter = new NavUserInfoPresenter(this);
        initView(view);
        eventClick();
    }

    private void eventClick() {
        itemLogout.setOnClickListener(this);
        itemHealthTrack.setOnClickListener(this);
    }

    private void initView(@NonNull View view) {
        tvName = view.findViewById(R.id.tv_name);
        tvEmail = view.findViewById(R.id.tv_email);
        itemLogout = view.findViewById(R.id.item_logout);
        itemHealthTrack = view.findViewById(R.id.item_health_track);
    }

    @Override
    public void onClick(@NonNull View v) {
        switch (v.getId()) {
            case R.id.item_logout:
                if (getActivity() != null) {
                    new ConfirmDialog(getActivity(), R.drawable.ic_logout, getString(R.string.dialog_confirm_logout_msg),
                            new ConfirmDialog.OnConfirmListener() {
                                @Override
                                public void onConfirm(BaseOverlayDialog overlayDialog) {
                                    navUserInfoPresenter.logOut();
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
            case R.id.item_health_track:
                replaceFragment(new NavHealthyTrackingFragment(), true, BaseActivity.Anim.RIGHT_IN_LEFT_OUT);
                break;
        }
    }


}