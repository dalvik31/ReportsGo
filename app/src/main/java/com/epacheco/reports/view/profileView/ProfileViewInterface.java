package com.epacheco.reports.view.profileView;

import androidx.fragment.app.FragmentActivity;
import com.google.firebase.auth.FirebaseUser;

public interface ProfileViewInterface {
  FragmentActivity getMyActivity();
  void successGetProfile(FirebaseUser firebaseUser);
  void errorGetProfile(String error);
}
