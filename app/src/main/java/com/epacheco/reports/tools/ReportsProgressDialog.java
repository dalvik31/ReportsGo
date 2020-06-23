package com.epacheco.reports.tools;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import androidx.fragment.app.FragmentActivity;
import android.view.Window;

import com.airbnb.lottie.LottieAnimationView;
import com.epacheco.reports.R;

public class ReportsProgressDialog {
  private static ReportsProgressDialog s_m_oCShowProgress;
  private Dialog m_Dialog;
  private LottieAnimationView progressLottie;

  private ReportsProgressDialog() {

  }

  public static ReportsProgressDialog getInstance() {
    if (s_m_oCShowProgress == null) {
      s_m_oCShowProgress = new ReportsProgressDialog();
    }
    return s_m_oCShowProgress;
  }

  public void showProgress(FragmentActivity myActivity,String message) {
    hideProgress();
    m_Dialog = new Dialog(myActivity);
    m_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    m_Dialog.setContentView(R.layout.custom_progress_dialog);
    progressLottie = m_Dialog.findViewById(R.id.lottie_anim_view);
    m_Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    m_Dialog.setCancelable(false);
    m_Dialog.setCanceledOnTouchOutside(false);
    m_Dialog.show();
  }
  public void hideProgress() {
    if(m_Dialog!=null && m_Dialog.isShowing()){
      m_Dialog.dismiss();
      m_Dialog.cancel();
    }
  }
}
