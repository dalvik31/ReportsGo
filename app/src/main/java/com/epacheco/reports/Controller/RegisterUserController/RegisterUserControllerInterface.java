package com.epacheco.reports.Controller.RegisterUserController;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.twitter.sdk.android.core.TwitterSession;

public interface RegisterUserControllerInterface {
  //email register methods
  void createAccountEmailAndPassword(String email, String password);
  //email login methods
  void loginEmailAndPassword(String email, String password);

  //google login methods
  void loginGoogle(GoogleSignInAccount acct);

  //twitter login methods
  void loginTwitter(TwitterSession session);

  //facebook login methods
  void loginFacebook(AccessToken token);
}
