package com.epacheco.reports.Model.RegisterUserModel;

import android.support.v4.app.FragmentActivity;
import com.epacheco.reports.Controller.RegisterUserController.RegisterUserControllerClass;
import com.epacheco.reports.View.RegisterUserView.RegisterUserViewClass;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.twitter.sdk.android.core.TwitterSession;

public class RegisterUserModelClass implements RegisterUserModelInterface {

  private RegisterUserViewClass registerUserViewClass;
  private RegisterUserControllerClass registerUserControllerClass;

  public RegisterUserModelClass(RegisterUserViewClass registerUserViewClass) {
    this.registerUserViewClass = registerUserViewClass;
    this.registerUserControllerClass = new RegisterUserControllerClass(this);
  }

  @Override
  public FragmentActivity getMyActivity() {
    if(registerUserViewClass!=null){
      return registerUserViewClass.getMyActivity();
    }
    return null;
  }

  @Override
  public void successRegisterUserEmail(FirebaseUser firebaseUser) {
    if(registerUserViewClass!=null){
      registerUserViewClass.successRegisterUserEmail(firebaseUser);
    }
  }

  @Override
  public void errorRegisterUserEmail(String error) {
    if(registerUserViewClass!=null){
      registerUserViewClass.errorRegisterUserEmail(error);
    }
  }

  @Override
  public void successLoginUserEmail(FirebaseUser firebaseUser) {
    if(registerUserViewClass!=null){
      registerUserViewClass.successLoginUserEmail(firebaseUser);
    }
  }

  @Override
  public void errorLoginUserEmail(String error) {
    if(registerUserViewClass!=null){
      registerUserViewClass.errorLoginUserEmail(error);
    }
  }

  @Override
  public void createAccountEmailAndPassword(String email, String password) {
    if(registerUserControllerClass!=null){
      registerUserControllerClass.createAccountEmailAndPassword(email,password);
    }
  }

  @Override
  public void loginEmailAndPassword(String email, String password) {
    if(registerUserControllerClass!=null){
      registerUserControllerClass.loginEmailAndPassword(email,password);
    }
  }

  @Override
  public void successLoginUserGoogle(FirebaseUser firebaseUser) {
    if(registerUserViewClass!=null){
      registerUserViewClass.successLoginUserGoogle(firebaseUser);
    }
  }

  @Override
  public void errorLoginUserGoogle(String error) {
    if(registerUserViewClass!=null){
      registerUserViewClass.errorLoginUserGoogle(error);
    }
  }

  @Override
  public void loginGoogle(GoogleSignInAccount acct) {
    if(registerUserControllerClass!=null){
      registerUserControllerClass.loginGoogle(acct);
    }
  }

  @Override
  public void successLoginUserTwitter(FirebaseUser firebaseUser) {
    if(registerUserViewClass!=null){
      registerUserViewClass.successLoginUserTwitter(firebaseUser);
    }
  }

  @Override
  public void errorLoginUserTwitter(String error) {
    if(registerUserViewClass!=null){
      registerUserViewClass.errorLoginUserTwitter(error);
    }
  }

  @Override
  public void loginTwitter(TwitterSession session) {
    if(registerUserControllerClass!=null){
      registerUserControllerClass.loginTwitter(session);
    }
  }

  @Override
  public void successLoginFacebook(FirebaseUser firebaseUser) {
    if(registerUserViewClass!=null){
      registerUserViewClass.successLoginFacebook(firebaseUser);
    }
  }

  @Override
  public void errorLoginFacebook(String error) {
    if(registerUserViewClass!=null){
      registerUserViewClass.errorLoginFacebook(error);
    }
  }

  @Override
  public void loginFacebook(AccessToken token) {
    if(registerUserControllerClass!=null){
      registerUserControllerClass.loginFacebook(token);
    }
  }
}
