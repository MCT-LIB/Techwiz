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
import com.mct.components.baseui.BaseFragment;
import com.mct.components.baseui.BaseOverlayDialog;
import com.mct.components.baseui.BaseView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class NavUserInfoFragment extends BaseFragment implements View.OnClickListener, BaseView, MainViewCallBack.NavUserInfoCallBack {


    private NavUserInfoPresenter navUserInfoPresenter;


    View view;
    TextView tvName, tvEmail;
    RelativeLayout itemLogout;

    public NavUserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
    }

    private void initView(@NonNull View view) {
        tvName = view.findViewById(R.id.tv_name);
        tvEmail = view.findViewById(R.id.tv_email);
        itemLogout = view.findViewById(R.id.item_logout);
    }

    @Override
    public void onClick(@NonNull View v) {
        switch (v.getId()) {
            case R.id.item_logout:
                if (getActivity() != null) {
                    new ConfirmDialog(getActivity(),
                            R.drawable.ic_logout,
                            getString(R.string.dialog_confirm_msg),
                            new ConfirmDialog.OnConfirmListener() {
                                @Override
                                public void onConfirm(BaseOverlayDialog confirmDialog) {
                                    confirmDialog.dismiss();
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
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onFalse(Throwable t) {

    }

}