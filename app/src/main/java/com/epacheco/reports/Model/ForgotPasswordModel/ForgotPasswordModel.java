package com.epacheco.reports.Model.ForgotPasswordModel;

import com.epacheco.reports.Controller.ForgotPasswordController.ForgotPasswordController;
import com.epacheco.reports.View.ForgotPassword.ForgotPasswordAcitivity;

public class ForgotPasswordModel implements ForgotPasswordModelInterface{

    private ForgotPasswordController forgotPasswordController;
    private ForgotPasswordAcitivity forgotPasswordAcitivity;

    public ForgotPasswordModel(ForgotPasswordAcitivity forgotPasswordAcitivity) {
        this.forgotPasswordAcitivity = forgotPasswordAcitivity;
        this.forgotPasswordController = new ForgotPasswordController(this);
    }

    @Override
    public void successSendEmail() {
        if(forgotPasswordAcitivity!=null){
            forgotPasswordAcitivity.successSendEmail();
        }
    }

    @Override
    public void errorSendEmail(String error) {
        if(forgotPasswordAcitivity!=null){
            forgotPasswordAcitivity.errorSendEmail(error);
        }
    }

    @Override
    public void sendRecoveryPassword(String newEmail) {
        if(forgotPasswordController!=null){
            forgotPasswordController.sendRecoveryPassword(newEmail);
        }
    }
}
