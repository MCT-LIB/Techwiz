package com.csupporter.techwiz.presentation.presenter;

import com.csupporter.techwiz.domain.model.Account;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

public class ResetPasswordPresenter extends BasePresenter {

    public ResetPasswordPresenter(BaseView baseView) {
        super(baseView);
    }

    public void checkReEnterPassword(String newPass, String reNewPass, ViewCallback.ResetPasswordCallBack callBack){
        if (newPass.equals("") || reNewPass.equals("")){
            callBack.onEmptyValue();
            return;
        }
        if (newPass.equals(reNewPass)){
            callBack.onSuccess(newPass);
        }else {
            callBack.onFailure();
        }
    }
}
