package com.epacheco.reports.Controller.ProfileController;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;

public interface ProfileControllerInterface {
  void getProfile();
  void updateProfile(String imgUrl, Context context);
}
