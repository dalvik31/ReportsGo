package com.epacheco.reports.Tools;

import android.Manifest.permission;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Tools {
  private final static String TAG = Tools.class.getSimpleName();
  private static final String PREFERENCE_FILE_KEY = "reportsPreference";
  private static SharedPreferences preferences = ReportsApplication.getMyApplicationContext().getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
  private static final String DATE_FORMAT_2 = "dd / MMMM / yyyy";
  private static final String DATE_FORMAT_1 = "HH:mm  -- dd / MMMM / yyyy";

  public static void showToasMessage(FragmentActivity myActivity, String message) {
    Toast.makeText(myActivity, message, Toast.LENGTH_LONG).show();
  }

  public static void showSnackMessage(View myActivity, String message){
    Snackbar.make(myActivity,message,Snackbar.LENGTH_SHORT).show();
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

  public static void setLongPreference(String name,long value){
    SharedPreferences.Editor editor = preferences.edit();
    editor.putLong(name,value);
    editor.apply();
  }
  public static long getLongPreference(String name){
    return preferences.getLong(name,0);
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
    }else{
      photoPathCustom = "";
    }

    return photoPathCustom;
  }

  /**Creamos este metodo para comparar el tiempo que ha transcurrido
   * la aplicacion cerrada si ha pasado mas de 5 min cerramos sesion*/
  public static boolean compareTime(){
    long timeSave = Tools.getLongPreference(Constants.TIMER_SAVED);
    long time = System.currentTimeMillis();
    long timeFinish = time - timeSave;
    Log.e(TAG,"timeSave: "+timeSave);
    Log.e(TAG,"time: "+time);
    Log.e(TAG,"timeFinish: "+timeSave);
    //return timeFinish > 10000;//si el tiempo es mayor a 10 segundos
    return timeFinish > 300000;//si el tiempo es mayor a 5 minutos
  }


  public static boolean isGooglePlayServicesAvailable(Activity activity) {
    GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
    int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
    if(status != ConnectionResult.SUCCESS) {
      if(googleApiAvailability.isUserResolvableError(status)) {
       googleApiAvailability.getErrorDialog(activity, status, 2404).show();

      }
      return false;
    }
    return true;
  }

}
