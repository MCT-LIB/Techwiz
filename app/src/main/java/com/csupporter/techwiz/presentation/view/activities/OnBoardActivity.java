package com.csupporter.techwiz.presentation.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.utils.AESCrypt;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

public class OnBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);

//        PasswordEncryption passwordEncryption = new PasswordEncryption();
//        String hash = passwordEncryption.hash(new String("Hello"));
//        Log.e("ddd", "onCreate: " + hash);
//
//        boolean isValid =passwordEncryption.authenticate("Hello", hash);
//        Log.e("ddd", "onCreate: " + isValid);

//        try {
//            String s = AESCrypt.encrypt("Hello", "alo");
//            Log.e("AESCrypt", "-\n\n\n -" );
//            AESCrypt.decrypt("Hello", s);
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//        }

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