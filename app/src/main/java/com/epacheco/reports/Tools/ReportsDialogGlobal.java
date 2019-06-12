package com.epacheco.reports.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;


import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.widget.EditText;
import com.epacheco.reports.R;

public class ReportsDialogGlobal {

  public static void showDialogAccept(Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {

    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
    dialog.setTitle(title);
    dialog.setMessage(message);
    dialog.setPositiveButton(ReportsApplication.getMyApplicationContext().getString(R.string.btn_ok), onClickListener);
    dialog.setNegativeButton(ReportsApplication.getMyApplicationContext().getString(R.string.btn_cancel), null);
    dialog.show();
  }

  public static void showDialogOk(Context context, String title, String message) {

    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
    dialog.setTitle(title);
    dialog.setMessage(message);
    dialog.setPositiveButton(ReportsApplication.getMyApplicationContext().getString(R.string.btn_ok), null);
    dialog.show();
  }





}
