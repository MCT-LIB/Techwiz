package com.csupporter.techwiz.presentation.view.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.core.util.Consumer;

import com.csupporter.techwiz.data.firebase_source.FirebaseUtils;
import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.presentation.view.fragment.LoginFragment;
import com.mct.components.baseui.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new LoginFragment());
//        Account acc= new Account();
//        acc.setId(FirebaseUtils.uniqueId());
//        acc.setEmail("nam.td.986@aptechlearning.edu.vn");
//        acc.setPassword("Nam123456");
//        acc.setUserName("Nam Tran");
//        acc.setFirstName("adasd");
//        acc.setLastName("adasd");
//        DataInjection.provideRepository().account.addAccount(acc, new Consumer<Void>() {
//            @Override
//            public void accept(Void unused) {
//                Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
//            }
//        }, null);
    }

    @Override
    protected int getContainerId() {
        return android.R.id.content;
    }

    @Override
    protected void showToastOnBackPressed() {

    }
}