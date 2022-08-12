package com.csupporter.techwiz.presentation.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.csupporter.techwiz.App;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Account;

public class OnBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);

        new Handler(getMainLooper()).postDelayed(() -> {
            if (getApplicationContext() == null) {
                return;
            }
            String id = DataInjection.provideSettingPreferences().getToken();
            if (id == null) {
                gotoLogin();
            } else {
                DataInjection.provideRepository().account.findAccountById(id, account -> {
                    if (account != null) {
                        MainActivity.startActivity(OnBoardActivity.this, account);
                    } else {
                        gotoLogin();
                    }
                }, throwable -> gotoLogin());
            }
        }, 1000);
    }

    private void gotoLogin() {
        Intent intent = new Intent(getApplicationContext(), AuthenticateActivity.class);
        startActivity(intent);
        finish();
    }
}