package com.csupporter.techwiz.presentation.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.csupporter.techwiz.App;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Account;

public class OnBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getApplicationContext() == null) {
                    return;
                }
                Intent intent;
                String id = DataInjection.provideSettingPreferences().getToken();
                if (id == null) {
                    intent = new Intent(getApplicationContext(), AuthenticateActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}