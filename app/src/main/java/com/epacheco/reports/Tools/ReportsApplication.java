package com.epacheco.reports.Tools;

import android.content.Context;
import androidx.multidex.MultiDexApplication;
import android.util.Log;
import com.epacheco.reports.R;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

public class ReportsApplication  extends MultiDexApplication {
  private static Context myApplicationContext;

  @Override
  public void onCreate() {
    super.onCreate();
    myApplicationContext = this;
    inicializateTwoProjectsFireBase();
    inicializateConfigurationTwitter();
    inicializateConfigurationFacebook();

  }

  private void inicializateTwoProjectsFireBase() {
   /* FirebaseOptions options2 = new FirebaseOptions.Builder()
        .setApplicationId("1:948414846887:android:f012459a22be93bb") // Required for Analytics.
        .setApiKey("AIzaSyDnfPEgKbwzhK3R9tJXFpjBumgk4lWo1hY") // Required for Auth.
        .setDatabaseUrl("https://reports-go.firebaseio.com") // Required for RTDB.
        .setProjectId("reports-go")
        .build();
    FirebaseApp.initializeApp(this , options2);*/
    // Manually configure Firebase Options
    FirebaseOptions options = new FirebaseOptions.Builder()
        .setApplicationId("1:1033289891829:android:2a18e3a9d8c59d87") // Required for Analytics.
        .setApiKey("AIzaSyB6r9RWSUKPZyZapBbDeii0AGDS_JciRls") // Required for Auth.
        .setDatabaseUrl("https://superheroapp1.firebaseio.com") // Required for RTDB.
        .setProjectId("superheroapp1")
        .build();
    FirebaseApp.initializeApp(this /* Context */, options, "secondary");
  }


  public static Context getMyApplicationContext(){
   return myApplicationContext;
  }

  private void inicializateConfigurationTwitter(){
    TwitterConfig config = new TwitterConfig.Builder(this)
        .logger(new DefaultLogger(Log.DEBUG))//enable logging when app is in debug mode
        .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.TWITTER_CONSUMER_KEY), getResources().getString(R.string.TWITTER_CONSUMER_SECRET)))//pass the created app Consumer KEY and Secret also called API Key and Secret
        .debug(true)//enable debug mode
        .build();

    //finally initialize twitter with created configs
    Twitter.initialize(config);
  }

  private void inicializateConfigurationFacebook(){
    FacebookSdk.sdkInitialize(getApplicationContext());
    AppEventsLogger.activateApp(this);
  }


}
