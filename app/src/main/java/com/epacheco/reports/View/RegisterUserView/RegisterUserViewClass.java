package com.epacheco.reports.View.RegisterUserView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.epacheco.reports.Model.RegisterUserModel.RegisterUserModelClass;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.Constants;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
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


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_register_class);
    inicializateElements();
  }

  private void inicializateElements() {
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
            registerUserModelClass.loginFacebook(loginResult.getAccessToken());
          }

          @Override
          public void onCancel() {
          }

          @Override
          public void onError(FacebookException exception) {
            Log.e("error facebook","error: "+exception.toString());
          }
        });

    configSwitchSaveUser();
    getSaveData();
  }

  public void registerGoogle(View v){
      Intent signInIntent = mGoogleSignInClient.getSignInIntent();
      startActivityForResult(signInIntent, RC_SIGN_IN);
  }

  public void registerTwitter(View v){
    mTwitterAuthClient.authorize(this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {

      @Override
      public void success(Result<TwitterSession> twitterSessionResult) {
        // Success
        registerUserModelClass.loginTwitter(twitterSessionResult.data);
      }

      @Override
      public void failure(TwitterException e) {
        Log.e("failure","failure TWIITER: "+e.getMessage());
        e.printStackTrace();
      }
    });
  }

  public void registerFacebook(View v){
    LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));

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
    ScreenManager.goMainActivity(this);
  }

  @Override
  public void errorRegisterUserEmail(String error) {
    Tools.showToasMessage(this,error);
  }

  @Override
  public void successLoginUserEmail(FirebaseUser firebaseUser) {
    ScreenManager.goMainActivity(this);
    finish();
  }

  @Override
  public void errorLoginUserEmail(String error) {
    Tools.showToasMessage(this,error);
  }

  @Override
  public void successLoginUserGoogle(FirebaseUser firebaseUser) {
    ScreenManager.goMainActivity(this);
    finish();
  }

  @Override
  public void errorLoginUserGoogle(String error) {

  }

  @Override
  public void successLoginUserTwitter(FirebaseUser firebaseUser) {
    ScreenManager.goMainActivity(this);
    finish();
  }

  @Override
  public void errorLoginUserTwitter(String error) {
    Tools.showToasMessage(this,error);
  }

  @Override
  public void successLoginFacebook(FirebaseUser firebaseUser) {
    ScreenManager.goMainActivity(this);
    finish();
  }

  @Override
  public void errorLoginFacebook(String error) {
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
      Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
      try {
        registerUserModelClass.loginGoogle(task.getResult(ApiException.class));
      } catch (ApiException e) {
        // Google Sign In failed, update UI appropriately
        Log.e("ERROR GOOGLE", "Google sign in failed" +e.getStatusCode());

      }
    }
  }
}

//viernes creando el servicio para
