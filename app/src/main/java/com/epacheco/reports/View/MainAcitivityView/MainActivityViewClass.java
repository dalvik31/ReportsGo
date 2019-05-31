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

    //configNotificationFirebase();
    //getToken();
    //suscribirse();
    if (getIntent().getExtras() != null) {
      for (String key : getIntent().getExtras().keySet()) {
        Object value = getIntent().getExtras().get(key);
        Log.e("MainActivity: ", "Key: " + key + " Value: " + value);
      }
    }
  }


  @Override
  public void onStart() {
    super.onStart();
    if(mAuth.getCurrentUser()!=null ){
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
    ScreenManager.goOrderActivity(this);
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




  private void configNotificationFirebase(){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      // Create channel to show notifications.
      String channelId  = getString(R.string.default_notification_channel_id);
      String channelName = getString(R.string.default_notification_channel_name);
      NotificationManager notificationManager =
          getSystemService(NotificationManager.class);
      notificationManager.createNotificationChannel(new NotificationChannel(channelId,
          channelName, NotificationManager.IMPORTANCE_LOW));
    }


    if (getIntent().getExtras() != null) {
      for (String key : getIntent().getExtras().keySet()) {
        Object value = getIntent().getExtras().get(key);
        Log.d("AQUI ESTA EL KEY", "Key: " + key + " Value: " + value);
      }
    }

  }
  private void suscribirse(){
    FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.default_notification_channel_name))
        .addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
            String msg = "Suscrito";
            if (!task.isSuccessful()) {
              msg ="fallo susbscripcion";
            }
            Log.e("ERROR", msg);
            Toast.makeText(MainActivityViewClass.this, msg, Toast.LENGTH_SHORT).show();
          }
        });
  }


  private void getToken(){
    FirebaseInstanceId.getInstance().getInstanceId()
        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
          @Override
          public void onComplete(@NonNull Task<InstanceIdResult> task) {
            if (!task.isSuccessful()) {
              Log.e("ERROR", "getInstanceId EXEPTION", task.getException());
              return;
            }

            // Get new Instance ID token
            String token = task.getResult().getToken();

            // Log and toast
            String msg =token;
            Log.e("SUCCESS PRIMARY", msg);
            Toast.makeText(MainActivityViewClass.this, msg, Toast.LENGTH_SHORT).show();
          }
        });

    FirebaseApp app = FirebaseApp.getInstance("secondary");
    FirebaseInstanceId.getInstance(app).getInstanceId()
        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
          @Override
          public void onComplete(@NonNull Task<InstanceIdResult> task) {
            if (!task.isSuccessful()) {
              Log.e("ERROR", "getInstanceId EXCEPTION SECONDARY", task.getException());
              return;
            }

            // Get new Instance ID token
            String token = task.getResult().getToken();

            // Log and toast
            String msg =token;
            Log.e("SUCCESS SECONDARY", msg);
            Toast.makeText(MainActivityViewClass.this, msg, Toast.LENGTH_SHORT).show();
          }
        });
  }






  /**Read mode*/




  /*
  @Override
  public void onPause() {
    super.onPause();
    NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
    adapter.disableReaderMode(this);
  }

  @Override
  protected void onResume() {
    super.onResume();

    //1. Get the default adapter
    NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);

    if (!adapter.isEnabled()) {
      Toast.makeText(getApplicationContext(), "Please enable NFC to test this.", Toast.LENGTH_LONG).show();
    }

    Bundle options = new Bundle();
    options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 5000);
    //2. Enable reader mode
    adapter.enableReaderMode(this,
        new NfcAdapter.ReaderCallback() {
          @Override
          public void onTagDiscovered(final Tag tag) {
            //Update the view since input was recieved.
            recievedInput();
          }
        },
        NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
        options);
  }

  private void recievedInput() {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        Log.e("SUCCESS","SUCCESS");
        //Make the user able to leave for next activity
        startNewAcitivty();
      }
    });
  }

  public void startNewAcitivty() {
    //3. Disable reader mode
    NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
    adapter.disableReaderMode(this);

    //4. Send user to next activity
    Intent intent = new Intent(this, TestNfcView.class);
    startActivity(intent);
  }

*/

}
