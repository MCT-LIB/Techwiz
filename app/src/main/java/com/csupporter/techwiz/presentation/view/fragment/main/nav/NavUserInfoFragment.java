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
import android.widget.Toast;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.csupporter.techwiz.presentation.presenter.main.NavUserInfoPresenter;
import com.csupporter.techwiz.presentation.view.activities.AuthenticateActivity;
import com.csupporter.techwiz.presentation.view.dialog.ConfirmDialog;
import com.csupporter.techwiz.presentation.view.dialog.LoadingDialog;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseFragment;
import com.mct.components.baseui.BaseFragment;
import com.mct.components.baseui.BaseOverlayDialog;
import com.mct.components.baseui.BaseView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class NavUserInfoFragment extends BaseFragment implements View.OnClickListener, BaseView, MainViewCallBack.NavUserInfoCallBack {


    private NavUserInfoPresenter navUserInfoPresenter;
    private ConfirmDialog dialog;



    View view;
    TextView tvName, tvEmail;
    RelativeLayout itemLogout, itemHealthTrack;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NavUserInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavUserInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NavUserInfoFragment newInstance(String param1, String param2) {
        NavUserInfoFragment fragment = new NavUserInfoFragment();
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

                    dialog = new ConfirmDialog(getActivity(), new ConfirmDialog.ShowConfirmDialog() {
                        @Override
                        public void onConfirm() {
                            navUserInfoPresenter.logOut();
                            Intent intent = new Intent(getContext(), AuthenticateActivity.class);
                            startActivity(intent);
                            if (getActivity() != null) {
                                getActivity().finish();
                            }
                        }

                        @Override
                        public void onCancel() {
                            dialog.dismiss();
                        }
                    });
                    dialog.create(null);

                }
                break;
            case R.id.item_health_track:
                replaceFragment(new HealthyTrackingFragment(), true, BaseActivity.Anim.RIGHT_IN_LEFT_OUT);
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