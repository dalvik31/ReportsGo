package com.epacheco.reports.Controller.ProfileController;

import com.epacheco.reports.Model.ProfileModel.ProfileModelClass;

public class ProfileControllerClass implements ProfileControllerInterface{

  private ProfileModelClass profileModelClass;

  public ProfileControllerClass(ProfileModelClass profileModelClass) {
    this.profileModelClass = profileModelClass;
  }

  @Override
  public void getProfile() {

  }
}
