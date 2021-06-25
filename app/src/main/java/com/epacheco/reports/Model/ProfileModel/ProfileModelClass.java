package com.epacheco.reports.Model.ProfileModel;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Controller.ProfileController.ProfileControllerClass;
import com.epacheco.reports.view.profileView.ProfileViewClass;
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
  public void successUpdateProfile() {
    if(profileViewClass!=null){
      profileViewClass.successUpdateProfile();
    }
  }

  @Override
  public void errorUpdateProfile(String error) {
    if(profileViewClass!=null){
      profileViewClass.errorUpdateProfile(error);
    }
  }

  @Override
  public void getProfile() {
    if(profileControllerClass!=null){
      profileControllerClass.getProfile();
    }
  }

  @Override
  public void updateProfile(String imgUrl, Context context) {
    if(profileControllerClass!=null){
      profileControllerClass.updateProfile(imgUrl,context);
    }
  }
}
