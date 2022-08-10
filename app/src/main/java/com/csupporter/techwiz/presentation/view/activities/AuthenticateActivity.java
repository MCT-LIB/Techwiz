package com.csupporter.techwiz.presentation.view.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.csupporter.techwiz.presentation.view.fragment.LoginFragment;
import com.csupporter.techwiz.utils.ScreenUtils;
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