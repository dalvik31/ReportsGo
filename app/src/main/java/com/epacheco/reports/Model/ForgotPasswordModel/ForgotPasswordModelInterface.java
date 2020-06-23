package com.epacheco.reports.Model.ForgotPasswordModel;

public interface ForgotPasswordModelInterface {

    //View methods
    void successSendEmail();
    void errorSendEmail(String error);

    //Controller methods
    void sendRecoveryPassword(String newEmail);

}
