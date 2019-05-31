package com.epacheco.reports.Model.RegisterUserModel;

import android.support.v4.app.FragmentActivity;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.twitter.sdk.android.core.TwitterSession;

public interface RegisterUserModelInterface {
  FragmentActivity getMyActivity();

  /**email methods*/
  //email login methods ***View***
  //email register methods
  void successRegisterUserEmail(FirebaseUser firebaseUser);
  void errorRegisterUserEmail(String error);
  //email login methods
  void successLoginUserEmail(FirebaseUser firebaseUser);
  void errorLoginUserEmail(String error);


  //email login methods ***Controller***
  //email register methods
  void createAccountEmailAndPassword(String email, String password);
  //email login methods
  void loginEmailAndPassword(String email, String password);
  /*** end email methods*/



  /**Google methods*/
  //google login methods ***View***
  void successLoginUserGoogle(FirebaseUser firebaseUser);
  void errorLoginUserGoogle(String error);

  //google login methods ***Controller***
  void loginGoogle(GoogleSignInAccount acct);
  /**End Google methods*/



  /**Twitter methods*/
  //twitter login methods ***View***
  void successLoginUserTwitter(FirebaseUser firebaseUser);
  void errorLoginUserTwitter(String error);

  //twitter login methods ***Controller***
  void loginTwitter(TwitterSession session);
  /**End Twitter methods*/


  /**Facebook methods*/
  //Facebook login methods ***View***
  void successLoginFacebook(FirebaseUser firebaseUser);
  void errorLoginFacebook(String error);

  //Facebook login methods
  void loginFacebook(AccessToken token);
  /**End facebook methods*/
}
