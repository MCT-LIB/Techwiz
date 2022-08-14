package com.csupporter.techwiz.presentation.view.fragment.docters.nav;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.csupporter.techwiz.App;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.presentation.view.fragment.profile.ProfileFragment;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.baseui.BaseFragment;

public class DtHomeFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView imgAvatar;
    private TextView tvUserName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dt_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        eventClick();
        setData();
    }

    private void setData() {
        Glide.with(this).load(App.getApp().getAccount().getAvatar())
                .placeholder(R.drawable.ic_default_avatar)
                .error(R.drawable.ic_default_avatar)
                .into(imgAvatar);

        tvUserName.setText(App.getApp().getAccount().getFullName());
    }

    private void eventClick() {
        imgAvatar.setOnClickListener(this);
    }

    private void initView(View view) {
        imgAvatar = view.findViewById(R.id.img_avatar);
        tvUserName = view.findViewById(R.id.tv_username);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_avatar){
            Fragment fragment = new DtProfileFragment();
            replaceFragment(fragment , true, BaseActivity.Anim.RIGHT_IN_LEFT_OUT);
        }
    }
}