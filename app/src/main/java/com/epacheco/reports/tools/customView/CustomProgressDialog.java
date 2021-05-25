package com.epacheco.reports.tools.customView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.epacheco.reports.R;

public class CustomProgressDialog {

    public Dialog m_Dialog;

    public Dialog showProgress(Context myActivity) {
        m_Dialog = new Dialog(myActivity);
        if(!((Activity) myActivity).isFinishing()) {
            hideProgress();
            m_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            m_Dialog.setContentView(R.layout.custom_progress_dialog);
            m_Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            m_Dialog.setCancelable(false);
            m_Dialog.setCanceledOnTouchOutside(false);
            m_Dialog.show();
        }
        return m_Dialog;
    }
    public void hideProgress() {
        if(m_Dialog!=null && m_Dialog.isShowing()){
            m_Dialog.dismiss();
        }
    }
}

