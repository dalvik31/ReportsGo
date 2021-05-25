package com.epacheco.reports.tools;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.epacheco.reports.tools.customView.CustomProgressDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class CustomOnCompleteListener<TResult> implements OnCompleteListener<TResult> {
    private CustomProgressDialog mProgressDialog;
    Context context;

    public CustomOnCompleteListener(Context context) {
        this.context = context;
        mProgressDialog =   new CustomProgressDialog();
        mProgressDialog.showProgress(context);
    }

    @Override
    public void onComplete(@NonNull Task<TResult> task) {
        if (mProgressDialog.m_Dialog.isShowing()) {
            mProgressDialog.m_Dialog.dismiss();
            ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }
}
