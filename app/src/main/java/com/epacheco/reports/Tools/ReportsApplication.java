package com.epacheco.reports.Tools;

import android.content.Context;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;
import android.util.Log;
import com.epacheco.reports.R;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.common.GoogleApiAvailability;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

public class ReportsApplication  extends MultiDexApplication {
  private static Context myApplicationContext;
  private static ReportsApplication instance;


  @Override
  public void onCreate() {
    super.onCreate();
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    myApplicationContext = this;
    inicializateConfigurationTwitter();
    inicializateConfigurationFacebook();

  }

  public static ReportsApplication getInstance() {
    if (instance== null) {
      synchronized(ReportsApplication.class) {
        if (instance == null)
          instance = new ReportsApplication();
      }
    }
    return instance;
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


  public GoogleApiAvailability getGoogleAvalability(){
    return    GoogleApiAvailability.getInstance();
  }

}
