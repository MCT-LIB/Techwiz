package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.csupporter.techwiz.presentation.presenter.authentication.NavUserInfoPresenter;
import com.csupporter.techwiz.presentation.view.activities.AuthenticateActivity;
import com.csupporter.techwiz.presentation.view.dialog.ConfirmDialog;
import com.csupporter.techwiz.presentation.view.dialog.LoadingDialog;
import com.csupporter.techwiz.utils.OpenGallery;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseOverlayDialog;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


public class NavUserInfoFragment extends BaseNavFragment implements View.OnClickListener, MainViewCallBack.NavUserInfoCallBack {


    private View view;
    private TextView tvName, tvEmail;
    private RelativeLayout itemLogout, itemHealthTrack;
    private CircleImageView btnOpenGallery;
    private CircleImageView avatar;
    private LoadingDialog dialog;

    private NavUserInfoPresenter navUserInfoPresenter;
    private static final int MY_REQUEST_CODE = 10;
    private Uri mUri;


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
        btnOpenGallery.setOnClickListener(this);
    }

    private void initView(@NonNull View view) {
        tvName = view.findViewById(R.id.tv_name);
        tvEmail = view.findViewById(R.id.tv_email);
        itemLogout = view.findViewById(R.id.item_logout);
        itemHealthTrack = view.findViewById(R.id.item_health_track);
        btnOpenGallery = view.findViewById(R.id.crl_open_gallery);
        avatar = view.findViewById(R.id.avatar_personal);
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
            case R.id.crl_open_gallery:
                navUserInfoPresenter.requestPermissionToGallery(getActivity(), this);
                break;
        }
    }

    @Override
    public void requestPermissionSuccess() {
        showLoading();
        OpenGallery.getInstance().selectImage(mActivityResultLauncher);
    }

    ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    hideLoading();
                    Intent data = result.getData();
                    if (data == null) {
                        return;
                    }
                    Uri uri = data.getData();
                    mUri = uri;
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                        avatar.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void notRequestPermission() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
        getActivity().requestPermissions(permissions, MY_REQUEST_CODE);
    }

    @Override
    public void showLoading() {
        if (getContext() == null) return;
        if (dialog != null) {
            dialog.dismiss();
        }
        dialog = new LoadingDialog(getContext());
        dialog.create(null);
    }

    @Override
    public void hideLoading() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}