package com.csupporter.techwiz.presentation.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;

import com.csupporter.techwiz.App;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.presentation.view.fragment.main.MainFragment;
import com.mct.components.baseui.BaseActivity;

public class MainActivity extends BaseActivity {

    private static final String KEY_ACCOUNT = "key_account";

    public static void startActivity(Context context, @NonNull Account account) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(KEY_ACCOUNT, account);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Account account = (Account) getIntent().getSerializableExtra(KEY_ACCOUNT);
        if (account == null) {
            finish();
        } else {
            App.getApp().setAccount(account);
            replaceFragment(new MainFragment());
        }
    }

    @Override
    protected int getContainerId() {
        return Window.ID_ANDROID_CONTENT;
    }

    @Override
    protected void showToastOnBackPressed() {

    }
}