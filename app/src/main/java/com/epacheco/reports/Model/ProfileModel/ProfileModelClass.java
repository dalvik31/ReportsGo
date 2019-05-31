package com.epacheco.reports.Model.ProfileModel;

import android.support.v4.app.FragmentActivity;
import com.epacheco.reports.Controller.ProfileController.ProfileControllerClass;
import com.epacheco.reports.View.ProfileView.ProfileViewClass;
import com.google.firebase.auth.FirebaseUser;

public class ProfileModelClass implements ProfileModelInterface {
  private ProfileViewClass profileViewClass;
  private ProfileControllerClass profileControllerClass;

  public ProfileModelClass(ProfileViewClass profileViewClass) {
    this.profileViewClass = profileViewClass;
    this.profileControllerClass = new ProfileControllerClass(this);
  }

  @Override
  public FragmentActivity getMyActivity() {
    return profileViewClass.getMyActivity();
  }

  @Override
  public void successGetProfile(FirebaseUser firebaseUser) {
    if(profileViewClass!=null){
      profileViewClass.successGetProfile(firebaseUser);
    }
  }

  @Override
  public void errorGetProfile(String error) {
    if(profileViewClass!=null){
      profileViewClass.errorGetProfile(error);
    }
  }

  @Override
  public void getProfile() {
    if(profileControllerClass!=null){
      profileControllerClass.getProfile();
    }
  }
}
