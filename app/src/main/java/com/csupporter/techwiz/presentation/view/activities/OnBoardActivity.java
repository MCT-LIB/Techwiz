package com.csupporter.techwiz.presentation.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.utils.EncryptUtils;

import java.security.GeneralSecurityException;

public class OnBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);

        try {
            String s = EncryptUtils.encrypt("123456aA");
            Log.e("AESCrypt en", "-\n\n " + s + " \n\n -");
            String dc = EncryptUtils.decrypt("123456aA", s);
            Log.e("AESCrypt dc", "-\n\n " + dc + " \n\n -");
            boolean b = EncryptUtils.checkPassword("123456aA", s);
            Log.e("AESCrypt dc", "-\n\n " + b + " \n\n -");

        } catch (GeneralSecurityException e) {
            Log.e("AESCrypt", "False");
        }

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