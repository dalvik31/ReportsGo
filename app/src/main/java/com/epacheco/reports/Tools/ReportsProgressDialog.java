package com.epacheco.reports.Tools;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.epacheco.reports.R;

public class ReportsProgressDialog {
  public static ReportsProgressDialog s_m_oCShowProgress;

  public Dialog m_Dialog;
  private ProgressBar m_ProgressBar;
  private Context context;

  private ReportsProgressDialog(Context context) {
    this.context = context;

  }

  public static ReportsProgressDialog getInstance(Context context) {
    if (s_m_oCShowProgress == null) {
      s_m_oCShowProgress = new ReportsProgressDialog(context);
    }
    return s_m_oCShowProgress;
  }

  public void showProgress(FragmentActivity myActivity,String message) {
    hideProgress();
    m_Dialog = new Dialog(myActivity);
    m_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    m_Dialog.setContentView(R.layout.custom_progress_dialog);
    m_Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    m_ProgressBar =  m_Dialog.findViewById(R.id.progress_custom);
    TextView progressText = m_Dialog.findViewById(R.id.lbl_message_progress);
    progressText.setText(message);
    progressText.setVisibility(View.VISIBLE);
    m_ProgressBar.setVisibility(View.VISIBLE);
    m_ProgressBar.setIndeterminate(true);
    m_Dialog.setCancelable(false);
    m_Dialog.setCanceledOnTouchOutside(false);
    m_Dialog.show();
  }
  public void hideProgress() {
    if(m_Dialog!=null && m_Dialog.isShowing()){
      m_Dialog.dismiss();
    }
  }
}
