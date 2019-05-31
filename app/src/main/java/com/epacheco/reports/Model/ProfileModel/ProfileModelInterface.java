package com.epacheco.reports.Model.ProfileModel;

import android.support.v4.app.FragmentActivity;
import com.google.firebase.auth.FirebaseUser;

public interface ProfileModelInterface {
  //View methods
  FragmentActivity getMyActivity();
  void successGetProfile(FirebaseUser firebaseUser);
  void errorGetProfile(String error);

  //Controller methods
  void getProfile();
}
