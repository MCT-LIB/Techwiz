package com.csupporter.techwiz.presentation.presenter;

import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

public class ResetPasswordPresenter extends BasePresenter {

    public ResetPasswordPresenter(BaseView baseView) {
        super(baseView);
    }

    public void checkOldPassword(String old_pw, String current_pw, ViewCallback.ResetPasswordCallBack callBack){
        if (old_pw.equals("")){
            callBack.onEmptyValue();
            return;
        }
        if (old_pw.equals(current_pw)){
            callBack.onSuccess();
        }else {
            callBack.onFailure();
        }
    }

    public void checkReEnterPassword(String newPass, String reNewPass, ViewCallback.ResetPasswordCallBack callBack){
        if (newPass.equals("") || reNewPass.equals("")){
            callBack.onEmptyValue();
            return;
        }
        if (newPass.equals(reNewPass)){
            callBack.onSuccess();
        }else {
            callBack.onFailure();
        }
    }
}
