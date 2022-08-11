package com.csupporter.techwiz.presentation.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;

import com.csupporter.techwiz.presentation.view.fragment.UserHomeFragment;
import com.mct.components.baseui.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new UserHomeFragment());
    }

    @Override
    protected int getContainerId() {
        return Window.ID_ANDROID_CONTENT;
    }

    @Override
    protected void showToastOnBackPressed() {

    }
}