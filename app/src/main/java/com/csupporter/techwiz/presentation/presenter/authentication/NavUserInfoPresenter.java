package com.csupporter.techwiz.presentation.presenter.authentication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

public class NavUserInfoPresenter extends BasePresenter {

    public NavUserInfoPresenter(BaseView baseView) {
        super(baseView);
    }

    public void logOut(){
        DataInjection.provideSettingPreferences().setToken(null);
    }


    public void requestPermissionToGallery(Context context,MainViewCallBack.NavUserInfoCallBack callBack) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            callBack.requestPermissionSuccess();
            return;
        }
        if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            callBack.requestPermissionSuccess();
        } else {
            callBack.notRequestPermission();
        }
    }
}
