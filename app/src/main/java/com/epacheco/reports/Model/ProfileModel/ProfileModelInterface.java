package com.epacheco.reports.Model.ProfileModel;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import com.google.firebase.auth.FirebaseUser;

public interface ProfileModelInterface {
  //View methods
  FragmentActivity getMyActivity();
  void successGetProfile(FirebaseUser firebaseUser);
  void errorGetProfile(String error);
  void successUpdateProfile();
  void errorUpdateProfile(String error);

  //Controller methods
  void getProfile();
  void updateProfile(String imgUrl, Context context);
}
