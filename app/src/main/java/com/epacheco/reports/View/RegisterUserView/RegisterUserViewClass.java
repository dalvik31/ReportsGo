package com.epacheco.reports.View.RegisterUserView;

import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.os.Debug;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.epacheco.reports.BuildConfig;
import com.epacheco.reports.Model.RegisterUserModel.RegisterUserModelClass;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.Constants;
import com.epacheco.reports.Tools.ReportsDialogGlobal;
import com.epacheco.reports.Tools.ReportsProgressDialog;
import com.epacheco.reports.Tools.ScreenManager;
import com.epacheco.reports.Tools.Tools;
import com.epacheco.reports.databinding.ActivityRegisterClassBinding;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import java.util.Arrays;

public class RegisterUserViewClass extends AppCompatActivity implements RegisterUserViewInterface {
  private ActivityRegisterClassBinding binding;
  private RegisterUserModelClass registerUserModelClass;
  private GoogleSignInClient mGoogleSignInClient;
  private static final int RC_SIGN_IN = 9001;
  private TwitterAuthClient mTwitterAuthClient;
  private CallbackManager mCallbackManager;
  private ReportsProgressDialog progressbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_register_class);
    inicializateElements();
  }

  private void inicializateElements() {
    progressbar = ReportsProgressDialog.getInstance(this);
    registerUserModelClass = new RegisterUserModelClass(this);

    //Inicializamos google
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build();
    mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    //Inicializamos twitter
    mTwitterAuthClient= new TwitterAuthClient();

    //Inicializamos facebook
    mCallbackManager = CallbackManager.Factory.create();

    LoginManager.getInstance().registerCallback(mCallbackManager,
        new FacebookCallback<LoginResult>() {
          @Override
          public void onSuccess(LoginResult loginResult) {
            progressbar.hideProgress();
            registerUserModelClass.loginFacebook(loginResult.getAccessToken());
          }

          @Override
          public void onCancel() {
            progressbar.hideProgress();
          }

          @Override
          public void onError(FacebookException e) {
            progressbar.hideProgress();
            Log.e("error facebook","error: "+e.toString());
            ReportsDialogGlobal.showDialogOk(RegisterUserViewClass.this,"Error FaceBook",e.getMessage());

          }
        });

    configSwitchSaveUser();
    getSaveData();
  }

  public void registerGoogle(View v){
    progressbar.showProgress(this,getString(R.string.msg_process));
    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
    startActivityForResult(signInIntent, RC_SIGN_IN);
  }

  public void registerTwitter(View v){
    progressbar.showProgress(this,getString(R.string.msg_process));
    mTwitterAuthClient.authorize(this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {

      @Override
      public void success(Result<TwitterSession> twitterSessionResult) {
        // Success
        registerUserModelClass.loginTwitter(twitterSessionResult.data);
        progressbar.hideProgress();
      }

      @Override
      public void failure(TwitterException e) {
        progressbar.hideProgress();
        Log.e("failure","failure TWIITER: "+e.getMessage());
        if(!e.getMessage().contains("request was canceled")){
          ReportsDialogGlobal.showDialogOk(RegisterUserViewClass.this,"Error Twitter",e.getMessage());
        }
        e.printStackTrace();
      }
    });
  }

  public void forgotPassword(View v){
    ScreenManager.goForgotPasswordActivity(this);
  }

  public void registerFacebook(View v){
    progressbar.showProgress(this,getString(R.string.msg_process));
    LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

  }


  public void registerWithEmailAndPassword(View v){

    Tools.setLongPreference(Constants.TIMER_SAVED,System.currentTimeMillis());
    String email = binding.txtEmail.getText().toString();
    String password = binding.txtPassword.getText().toString();
    if(email.isEmpty()){
      Tools.showSnackMessage(binding.CoordinatorLayoutContainerLogin,getString(R.string.lbl_email));
      return;
    }else if(password.isEmpty()){
      Tools.showSnackMessage(binding.CoordinatorLayoutContainerLogin,getString(R.string.lbl_password));
      return;
    }
    progressbar.showProgress(this,getString(R.string.msg_process));
    registerUserModelClass.createAccountEmailAndPassword(email,password);
  }

  public void loginWithEmailAndPassword(View v){

    Tools.setLongPreference(Constants.TIMER_SAVED,System.currentTimeMillis());
    String email = binding.txtEmail.getText().toString();
    String password = binding.txtPassword.getText().toString();
    if(email.isEmpty()){
      Tools.showSnackMessage(binding.CoordinatorLayoutContainerLogin,getString(R.string.lbl_email));
      return;
    }else if(password.isEmpty()){
      Tools.showSnackMessage(binding.CoordinatorLayoutContainerLogin,getString(R.string.lbl_password));
      return;
    }
    progressbar.showProgress(this,getString(R.string.msg_process));
    registerUserModelClass.loginEmailAndPassword(email,password);

  }

  private void configSwitchSaveUser(){
    binding.AppCompatCheckBoxSaveUser.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String userName = binding.txtEmail.getText().toString();
        if(!userName.isEmpty()){
          if(isChecked){
            Tools.setStringPreference(Constants.USERNAME_SAVE,userName);
          }else {
            Tools.setStringPreference(Constants.USERNAME_SAVE,"");
          }
        }else{
          binding.AppCompatCheckBoxSaveUser.setChecked(false);
          Tools.showSnackMessage(binding.CoordinatorLayoutContainerLogin,getString(R.string.lbl_email));
        }

      }
    });
  }


  private void getSaveData(){
    String userName = Tools.getStringPreference(Constants.USERNAME_SAVE);
    if(!userName.isEmpty()){
      binding.txtEmail.setText(userName);
      binding.AppCompatCheckBoxSaveUser.setChecked(true);
    }
  }
  @Override
  public FragmentActivity getMyActivity() {
    return this;
  }

  @Override
  public void successRegisterUserEmail(FirebaseUser firebaseUser) {
    progressbar.hideProgress();
    ScreenManager.goMainActivity(this);
  }

  @Override
  public void errorRegisterUserEmail(String error) {
    progressbar.hideProgress();
    Tools.showToasMessage(this,error);
  }

  @Override
  public void successLoginUserEmail(FirebaseUser firebaseUser) {
    progressbar.hideProgress();
    ScreenManager.goMainActivity(this);
    finish();
  }

  @Override
  public void errorLoginUserEmail(String error) {
    progressbar.hideProgress();
    Tools.showToasMessage(this,error);
  }

  @Override
  public void successLoginUserGoogle(FirebaseUser firebaseUser) {
    progressbar.hideProgress();
    ScreenManager.goMainActivity(this);
    finish();
  }

  @Override
  public void errorLoginUserGoogle(String error) {
    progressbar.hideProgress();
  }

  @Override
  public void successLoginUserTwitter(FirebaseUser firebaseUser) {
    progressbar.hideProgress();
    ScreenManager.goMainActivity(this);
    finish();
  }

  @Override
  public void errorLoginUserTwitter(String error) {
    progressbar.hideProgress();
    Tools.showToasMessage(this,error);
  }

  @Override
  public void successLoginFacebook(FirebaseUser firebaseUser) {
    progressbar.hideProgress();
    ScreenManager.goMainActivity(this);
    finish();
  }

  @Override
  public void errorLoginFacebook(String error) {
    progressbar.hideProgress();
    Tools.showToasMessage(this,error);
  }


  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
    if(mCallbackManager.onActivityResult(requestCode, resultCode, data)) {
      return;
    }
    if (requestCode == RC_SIGN_IN) {
      progressbar.hideProgress();
      Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
      try {
        progressbar.hideProgress();
        registerUserModelClass.loginGoogle(task.getResult(ApiException.class));
      } catch (ApiException e) {
        progressbar.hideProgress();
        // Google Sign In failed, update UI appropriately
        Log.e("ERROR GOOGLE", "Google sign in failed" +e.getStatusCode());
        if(!e.getMessage().contains("12501")) ReportsDialogGlobal.showDialogOk(RegisterUserViewClass.this,"Error Google",e.getMessage()+" error code: "+e.getStatusCode());


      }
    }
  }
}
