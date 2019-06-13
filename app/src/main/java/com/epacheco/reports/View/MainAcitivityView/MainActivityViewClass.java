package com.epacheco.reports.View.MainAcitivityView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.Constants;
import com.epacheco.reports.Tools.ReportsApplication;
import com.epacheco.reports.Tools.ScreenManager;
import com.epacheco.reports.Tools.Tools;
import com.epacheco.reports.View.SearchElementsView.SearchElementView;

import com.epacheco.reports.View.TestNFC.TestNfcView;
import com.epacheco.reports.databinding.ActivityMainClassBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivityViewClass extends AppCompatActivity{
  private FirebaseAuth mAuth;
  private ActivityMainClassBinding binding;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this,R.layout.activity_main_class);
    mAuth = FirebaseAuth.getInstance();
  }


  @Override
  public void onStart() {
    super.onStart();

    if(mAuth.getCurrentUser()!=null && !Tools.compareTime() ){
      if( mAuth.getCurrentUser().getPhotoUrl()!=null){
        Glide.with(ReportsApplication.getMyApplicationContext()).load(Tools.getFormatUrlImage(mAuth.getCurrentUser().getPhotoUrl())).into(binding.imgProfile);
      }
      return;
    }
    ScreenManager.goRegisterActivity(this);
  }



  public void goProfileActivity(View v){
    ScreenManager.goProfileActivity(this);
  }
  public void goClientsActivity(View v){
    ScreenManager.goClientsActivity(this,false);
  }
  public void goProductsActivity(View v){
    ScreenManager.goProductsActivity(this,false);
  }
  public void goSaleActivity(View v){
    ScreenManager.goSaleActivity(this,null,null);
  }
  public void goOrderActivity(View v){
    ScreenManager.goOrderActivity(this,null);
  }


  public void goSearchElement(View view){
    switch (view.getId()){
      case R.id.fbtn_search_client:
        ScreenManager.goSearchActivity(this,SearchElementView.FROM_SEARCH_CLIENT);
        break;
      case R.id.fbtn_search_product:
        ScreenManager.goSearchActivity(this, SearchElementView.FROM_SEARCH_PRODUCT);
        break;
    }
  }

  @Override
  protected void onStop() {
    Tools.setLongPreference(Constants.TIMER_SAVED,System.currentTimeMillis());
    super.onStop();
  }
}
