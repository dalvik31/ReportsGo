package com.epacheco.reports.View.RegisterUserView;

import androidx.fragment.app.FragmentActivity;
import com.google.firebase.auth.FirebaseUser;

public interface RegisterUserViewInterface {
  FragmentActivity getMyActivity();

  //email register methods
  void successRegisterUserEmail(FirebaseUser firebaseUser);
  void errorRegisterUserEmail(String error);
  //email login methods
  void successLoginUserEmail(FirebaseUser firebaseUser);
  void errorLoginUserEmail(String error);

  //google login methods
  void successLoginUserGoogle(FirebaseUser firebaseUser);
  void errorLoginUserGoogle(String error);


  //twitter login methods
  void successLoginUserTwitter(FirebaseUser firebaseUser);
  void errorLoginUserTwitter(String error);

  //facebook login methods
  void successLoginFacebook(FirebaseUser firebaseUser);
  void errorLoginFacebook(String error);
}
