package com.epacheco.reports.Tools;

import android.Manifest;
import android.Manifest.permission;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Tools {
  private static final String PREFERENCE_FILE_KEY = "reportsPreference";
  private static SharedPreferences preferences = ReportsApplication.getMyApplicationContext().getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
  private static final String DATE_FORMAT_2 = "dd / MMMM / yyyy";
  private static final String DATE_FORMAT_1 = "HH:mm  -- dd / MMMM / yyyy";

  public static void showToasMessage(FragmentActivity myActivity, String message) {
    Toast.makeText(myActivity, message, Toast.LENGTH_LONG).show();
  }

  public static String getFormatDate(String epochDate) {
    if (epochDate == null || epochDate.isEmpty()) {
      return "-- ---- ----";
    }

    Calendar cal = Calendar.getInstance();
    DateFormat simple = new SimpleDateFormat(DATE_FORMAT_2, Locale.getDefault());
    cal.setTimeInMillis(Long.parseLong(epochDate));
    return simple.format(cal.getTime());
  }


  public static String getFormatDateHour(String epochDate){
    if(epochDate==null || epochDate.isEmpty()){
      return "-- ---- ----";
    }

    Calendar cal = Calendar.getInstance();
    DateFormat simple = new SimpleDateFormat(DATE_FORMAT_1,Locale.getDefault());
    cal.setTimeInMillis(Long.parseLong(epochDate));
    return simple.format(cal.getTime());
  }

  public static boolean checkPermissionsCamera(FragmentActivity myActivity){
    return ContextCompat.checkSelfPermission(myActivity, permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
  }
  public static boolean checkPermissionsGallery(FragmentActivity myActivity){
    return ContextCompat.checkSelfPermission(myActivity, permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
  }


  public static void setIntegerPreference(String name,int value){
    SharedPreferences.Editor editor = preferences.edit();
    editor.putInt(name,value);
    editor.apply();
  }
  public static int getIntegerPreference(String name){
    return preferences.getInt(name,0);
  }

  public static void setStringPreference(String name,String value){
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString(name,value);
    editor.apply();
  }
  public static String getStringPreference(String name){
    return preferences.getString(name,"");
  }


  public static void setFloatPreference(String name,float value){
    SharedPreferences.Editor editor = preferences.edit();
    editor.putFloat(name,value);
    editor.apply();
  }
  public static float getFloatPreference(String name){
    return preferences.getFloat(name,0);
  }

  public static String getFormatUrlImage(Uri photoPath){
    String photoPathCustom;
    if(photoPath!=null && !photoPath.toString().isEmpty()){
       photoPathCustom = photoPath.toString();
          //String photoPath = getFirebaseUser().getPhotoUrl().toString();
      String originalPieceOfUrl = "s96-c/photo.jpg";
      String originalPieceOfUrlTwitter = "_normal";
      String originalPieceOfUrlFacebook = "graph.facebook";
      String newPieceOfUrlToAdd = "s400-c/photo.jpg";

      if(photoPathCustom.contains(originalPieceOfUrl)){
        photoPathCustom = photoPathCustom.replace(originalPieceOfUrl, newPieceOfUrlToAdd);
      }else if(photoPathCustom.contains(originalPieceOfUrlTwitter)){
        photoPathCustom = photoPathCustom.replace(originalPieceOfUrlTwitter, "");
      }else if(photoPathCustom.contains(originalPieceOfUrlFacebook)){
        photoPathCustom+="?height=500";
      }
      Log.e("aqui","photoPath:"+photoPathCustom);
    }else{
      photoPathCustom = "";
    }

    return photoPathCustom;
  }

  //11477594
}
