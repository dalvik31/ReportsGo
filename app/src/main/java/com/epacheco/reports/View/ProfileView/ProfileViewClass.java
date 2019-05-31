package com.epacheco.reports.View.ProfileView;

import android.databinding.DataBindingUtil;
import android.provider.ContactsContract.RawContacts.Data;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.bumptech.glide.Glide;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.ScreenManager;
import com.epacheco.reports.Tools.Tools;
import com.epacheco.reports.databinding.ActivityProfileViewClassBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileViewClass extends AppCompatActivity implements ProfileViewInterface{
  private FirebaseAuth mAuth;
  private ActivityProfileViewClassBinding binding;
  private FirebaseUser firebaseUser;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this,R.layout.activity_profile_view_class);
    mAuth = FirebaseAuth.getInstance();
    inicializateElements();
    chargeInformation();
  }

  private void chargeInformation() {
    if(getFirebaseUser().getDisplayName()!=null && !getFirebaseUser().getDisplayName().isEmpty()){
      binding.lblUserName.setText(getFirebaseUser().getDisplayName());
    }else{
      binding.lblUserName.setText(getFirebaseUser().getEmail().split("@")[0]);
    }
    if(getFirebaseUser().getPhotoUrl()!=null && !getFirebaseUser().getPhotoUrl().toString().isEmpty()){

      Glide.with(this).load(Tools.getFormatUrlImage(mAuth.getCurrentUser().getPhotoUrl())).into(binding.imageviewAccountProfile);
      Glide.with(this).load(Tools.getFormatUrlImage(mAuth.getCurrentUser().getPhotoUrl())).into(binding.imgBackground);
    }
    binding.lblUserEmail.setText(getFirebaseUser().getEmail());

  }

  private void inicializateElements(){
    if(mAuth.getCurrentUser()==null){
      finish();
    }else{
      setFirebaseUser(mAuth.getCurrentUser());

    }
  }

  public void closeSesion(View v){
    mAuth.signOut();
    ScreenManager.goRegisterActivity(this);
    finish();
  }

  @Override
  public FragmentActivity getMyActivity() {
    return this;
  }

  @Override
  public void successGetProfile(FirebaseUser firebaseUser) {

  }

  @Override
  public void errorGetProfile(String error) {

  }

  public FirebaseUser getFirebaseUser() {
    return firebaseUser;
  }

  public void setFirebaseUser(FirebaseUser firebaseUser) {
    this.firebaseUser = firebaseUser;
  }
}
