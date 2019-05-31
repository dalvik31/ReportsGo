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

  public static void showInputDialog(Context context,String title, final com.epacheco.reports.View.OrderView.DialogInterface onClickListener){
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle(title);

// Set up the input
    final EditText input = new EditText(context);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
    input.setInputType(InputType.TYPE_CLASS_TEXT);
    input.setHint("Mi primer lista");
    input.setTextSize(20);
    input.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
    builder.setView(input);
// Set up the buttons
    builder.setPositiveButton(context.getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {

        onClickListener.OnClickListener(input.getText().toString());
      }
    });

    builder.setNegativeButton("Cancel",null);

    builder.show();
  }



}
