package com.epacheco.reports.tools;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;

import com.epacheco.reports.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import id.zelory.compressor.Compressor;

public class Tools {
    private final static String TAG = Tools.class.getSimpleName();
    private static final String PREFERENCE_FILE_KEY = "reportsPreference";
    private static SharedPreferences preferences = com.epacheco.reports.tools.ReportsApplication.getMyApplicationContext().getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
    private static final String DATE_FORMAT_4 = "yyyy-MM-dd";
    private static final String DATE_FORMAT_3 = "dd / MMMM";
    private static final String DATE_FORMAT_2 = "dd / MMMM / yyyy";
    private static final String DATE_FORMAT_1 = "HH:mm  -- dd / MMMM / yyyy";

    public static void showToasMessage(FragmentActivity myActivity, String message) {
        Toast.makeText(myActivity, message, Toast.LENGTH_LONG).show();
    }

    public static void showSnackMessage(View myActivity, String message) {
        Snackbar.make(myActivity, message, Snackbar.LENGTH_SHORT).show();
    }

    public static String getFormatDate(String epochDate) {
        if (epochDate == null || epochDate.isEmpty()) {
            return "-- ---- ----";
        }

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        //AQUI SE MODIFICA LA FECHA DEL DETALLE DEL CLIENTE
        DateFormat simple = new SimpleDateFormat(DATE_FORMAT_2, Locale.US);
        cal.setTimeInMillis(Long.parseLong(epochDate));
        return simple.format(cal.getTime());
    }

    public static String getFormatDateSimple(String epochDate) {
        if (epochDate == null || epochDate.isEmpty()) {
            return "-- ---- ----";
        }

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        DateFormat simple = new SimpleDateFormat(DATE_FORMAT_2, Locale.US);
        cal.setTimeInMillis(Long.parseLong(epochDate));
        return simple.format(cal.getTime());
    }

    public static String getFormatDateToDates(String firstEpochDate, String secondEpochDate) {
        if ((TextUtils.isEmpty(firstEpochDate)) || ((TextUtils.isEmpty(secondEpochDate)))) {
            return "-- ---- ----";
        }

        Calendar cal = Calendar.getInstance();
        DateFormat simple = new SimpleDateFormat(DATE_FORMAT_3, Locale.US);
        cal.setTimeInMillis(Long.parseLong(firstEpochDate));
        String firsDate = simple.format(cal.getTime());
        cal.setTimeInMillis(Long.parseLong(secondEpochDate));
        String secondDate = simple.format(cal.getTime());
        return String.format(ReportsApplication.getMyApplicationContext().getString(R.string.lbl_date_selected), firsDate, secondDate);
    }

    public static String getFormatDateHour(String epochDate) {
        if (epochDate == null || epochDate.isEmpty()) {
            return "-- ---- ----";
        }

        Calendar cal = Calendar.getInstance();
        DateFormat simple = new SimpleDateFormat(DATE_FORMAT_1, Locale.getDefault());
        cal.setTimeInMillis(Long.parseLong(epochDate));
        return simple.format(cal.getTime());
    }

    public static boolean checkPermissionsCamera(FragmentActivity myActivity) {
        return ContextCompat.checkSelfPermission(myActivity, permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkPermissionsGallery(FragmentActivity myActivity) {
        return ContextCompat.checkSelfPermission(myActivity, permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(myActivity, permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }


    public static void setIntegerPreference(String name, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(name, value);
        editor.apply();
    }

    public static int getIntegerPreference(String name) {
        return preferences.getInt(name, 0);
    }

    public static void setStringPreference(String name, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static String getStringPreference(String name) {
        return preferences.getString(name, "");
    }

    public static void setLongPreference(String name, long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(name, value);
        editor.apply();
    }

    public static long getLongPreference(String name) {
        return preferences.getLong(name, 0);
    }

    public static String getFormatUrlImage(Uri photoPath) {
        String photoPathCustom;
        if (photoPath != null && !photoPath.toString().isEmpty()) {
            photoPathCustom = photoPath.toString();
            //String photoPath = getFirebaseUser().getPhotoUrl().toString();
            String originalPieceOfUrl = "s96-c/photo.jpg";
            String originalPieceOfUrlTwitter = "_normal";
            String originalPieceOfUrlFacebook = "graph.facebook";
            String newPieceOfUrlToAdd = "s400-c/photo.jpg";

            if (photoPathCustom.contains(originalPieceOfUrl)) {
                photoPathCustom = photoPathCustom.replace(originalPieceOfUrl, newPieceOfUrlToAdd);
            } else if (photoPathCustom.contains(originalPieceOfUrlTwitter)) {
                photoPathCustom = photoPathCustom.replace(originalPieceOfUrlTwitter, "");
            } else if (photoPathCustom.contains(originalPieceOfUrlFacebook)) {
                photoPathCustom += "?height=500";
            }
        } else {
            photoPathCustom = "";
        }

        return photoPathCustom;
    }

    /**
     * Creamos este metodo para comparar el tiempo que ha transcurrido
     * la aplicacion cerrada si ha pasado mas de 5 min cerramos sesion
     */
    public static boolean compareTime() {
        long timeSave = com.epacheco.reports.tools.Tools.getLongPreference(com.epacheco.reports.tools.Constants.TIMER_SAVED);
        long time = System.currentTimeMillis();
        long timeFinish = time - timeSave;
        Log.e(TAG, "timeSave: " + timeSave);
        Log.e(TAG, "time: " + time);
        Log.e(TAG, "timeFinish: " + timeSave);
        //return timeFinish > 10000;//si el tiempo es mayor a 10 segundos
        return timeFinish > 300000;//si el tiempo es mayor a 5 minutos
    }


    public static boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();

            }
            return false;
        }
        return true;
    }

    public static long getDateTodayLong() {
        Calendar today = Calendar.getInstance();
        today.setTimeInMillis(System.currentTimeMillis());
        today.set(Calendar.HOUR, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        return today.getTimeInMillis();
    }


    /*
     * Metodo que permite comprimir imagenes y transformarlas a bitmap
     */
    public static byte[] getImage(Context ctx, String path, int width, int height) {
        // if(path == null) return null;
        final File file_thumb_path = new File(path);

        Bitmap thumb_bitmap = null;
        byte[] thumb_byte = null;
        try {
            thumb_bitmap = new Compressor(ctx)
                    .setMaxWidth(width)
                    .setMaxHeight(height)
                    .setQuality(75)
                    .compressToBitmap(file_thumb_path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //if (thumb_bitmap != null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        thumb_byte = baos.toByteArray();


        return thumb_byte;

    }
}
