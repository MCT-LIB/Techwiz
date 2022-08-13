package com.csupporter.techwiz.presentation.view.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Window;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.csupporter.techwiz.App;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.presentation.view.fragment.docters.DoctorFragment;
import com.csupporter.techwiz.presentation.view.fragment.main.MainFragment;
import com.csupporter.techwiz.presentation.view.fragment.main.nav.NavUserInfoFragment;
import com.csupporter.techwiz.utils.OpenGallery;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.toast.ToastUtils;

import java.io.File;
import java.io.IOException;

public class MainActivity extends BaseActivity {

    private static final String KEY_ACCOUNT = "key_account";
    private static final int MY_REQUEST_CODE = 10;

    public static void startActivity(Context context, @NonNull Account account) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(KEY_ACCOUNT, account);
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
            if (account.getType() == Account.TYPE_USER) {
                replaceFragment(new MainFragment());
//                Toast.makeText(this, "user", Toast.LENGTH_SHORT).show();
            } else if (account.getType() == Account.TYPE_DOCTOR) {
                replaceFragment(new DoctorFragment());
//                Toast.makeText(this, "doctor", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                OpenGallery.getInstance().selectImage(mActivityResultLauncher);
            }
        }
    }

    ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data == null) {
                        return;
                    }
                    Uri uri = data.getData();
                }
            }
    );

    @Override
    protected int getContainerId() {
        return Window.ID_ANDROID_CONTENT;
    }

    @Override
    protected void showToastOnBackPressed() {
        ToastUtils.makeWarningToast(this, Toast.LENGTH_SHORT, getString(R.string.toast_back_press), false).show();
    }

}