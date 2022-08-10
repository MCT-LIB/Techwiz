package com.csupporter.techwiz.presentation.presenter;

import android.widget.Toast;

import androidx.core.util.Consumer;

import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.presentation.view.activities.MainActivity;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

public class LoginPresenter extends BasePresenter {

    public LoginPresenter(BaseView baseView) {
        super(baseView);
    }

    public void login(String userName, String password , ViewCallback.Login callback){
        getBaseView().showLoading();
        DataInjection.provideRepository().account.checkUserNameAndPassword(userName, password, new Consumer<Account>() {
            @Override
            public void accept(Account account) {
                getBaseView().hideLoading();
                callback.loginSuccess();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                getBaseView().hideLoading();
                callback.loginError();
            }
        });
    }
}
