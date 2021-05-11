package com.epacheco.reports.Controller.ForgotPasswordController;

import androidx.annotation.NonNull;

import com.epacheco.reports.Model.ForgotPasswordModel.ForgotPasswordModel;
import com.epacheco.reports.R;
import com.epacheco.reports.tools.ReportsApplication;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordController implements ForgotPasswordControllerInterface {

    private ForgotPasswordModel forgotPasswordModel;
    private FirebaseAuth mAuth;

    public ForgotPasswordController(ForgotPasswordModel forgotPasswordModel) {
        this.forgotPasswordModel = forgotPasswordModel;
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void sendRecoveryPassword(String newEmail) {
        if (forgotPasswordModel != null) {
            mAuth.sendPasswordResetEmail(newEmail.trim()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    forgotPasswordModel.successSendEmail();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    forgotPasswordModel.errorSendEmail(getMessageFireBase(e.getMessage()));
                }
            });

        }

    }
    private String getMessageFireBase(String exception){
        if(exception.contains("The user may have been deleted")){
            return ReportsApplication.getMyApplicationContext().getString(R.string.msg_firebase_delete_user);
        }else if(exception.contains("The password is invalid or the user does not have a password")){
            return ReportsApplication.getMyApplicationContext().getString(R.string.msg_firebase_without_password);
        }else if(exception.contains("The email address is already in use by another account")){
            return ReportsApplication.getMyApplicationContext().getString(R.string.msg_firebase_used_email);
        }else{
            return exception;
        }
    }
}


