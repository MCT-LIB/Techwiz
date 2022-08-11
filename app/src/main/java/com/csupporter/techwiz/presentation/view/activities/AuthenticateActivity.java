package com.csupporter.techwiz.presentation.view.activities;

import android.os.Bundle;

import com.csupporter.techwiz.presentation.view.fragment.authentication.LoginFragment;
import com.mct.components.baseui.BaseActivity;

public class AuthenticateActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        replaceFragment(new LoginFragment());
    }

    @Override
    protected int getContainerId() {
        return android.R.id.content;
    }

    @Override
    protected void showToastOnBackPressed() {

    }

}