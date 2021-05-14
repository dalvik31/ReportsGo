package com.epacheco.reports.tools;

import android.app.AlertDialog;
import android.content.Context;


import android.content.DialogInterface;
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


  public static void showDialogAcceptAnCancel(Context context, String title, String message, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onCancelListener) {

    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
    dialog.setTitle(title);
    dialog.setMessage(message);
    dialog.setPositiveButton(ReportsApplication.getMyApplicationContext().getString(R.string.btn_ok), onClickListener);
    dialog.setNegativeButton(ReportsApplication.getMyApplicationContext().getString(R.string.btn_cancel), onCancelListener);
    dialog.show();
  }


  public static void showDialogOk(Context context, String title, String message) {

    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
    dialog.setTitle(title);
    dialog.setMessage(message);
    dialog.setPositiveButton(ReportsApplication.getMyApplicationContext().getString(R.string.btn_ok), null);
    dialog.show();
  }


  public static void showDialogTitle(Context context, String title, String message, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onCancelListener){

    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
    dialog.setTitle(title);
    dialog.setMessage(message);
    final EditText titleBox = new EditText(context);
    titleBox.getText();
    dialog.setView(titleBox);
    dialog.setPositiveButton(ReportsApplication.getMyApplicationContext().getString(R.string.btn_ok), onClickListener);
    dialog.setNegativeButton(ReportsApplication.getMyApplicationContext().getString(R.string.btn_cancel), onCancelListener);
    dialog.show();
  }



}
