package com.epacheco.reports.View.ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.epacheco.reports.Model.ForgotPasswordModel.ForgotPasswordModel;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.Tools;
import com.epacheco.reports.databinding.ActivityForgotPasswordAcitivityBinding;

public class ForgotPasswordAcitivity extends AppCompatActivity implements ForgotPasswordInterface{

    private ForgotPasswordModel forgotPasswordModel;
    private ActivityForgotPasswordAcitivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forgot_password_acitivity);
        forgotPasswordModel = new ForgotPasswordModel(this);
    }

    @Override
    public void successSendEmail() {
        Toast.makeText(this,getString(R.string.msg_email_send_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorSendEmail(String error) {
        Toast.makeText(this,error, Toast.LENGTH_SHORT).show();
    }

    public void recoveryEmail(View v){
        String email = binding.txtEmail.getText().toString();
        if(email.isEmpty()){
            Tools.showSnackMessage(binding.CoordinatorLayoutContainerForgot,getString(R.string.lbl_email));
            return;
        }
        forgotPasswordModel.sendRecoveryPassword(email);
    }
}
