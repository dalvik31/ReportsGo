package com.epacheco.reports.View.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.ReportsApplication;
import com.epacheco.reports.Tools.ScreenManager;
import com.epacheco.reports.Tools.Tools;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    private LottieAnimationView animationView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        animationView = findViewById(R.id.lottie_anim_splash);
        mAuth = FirebaseAuth.getInstance();


    }

    private void startAnimationSplash(){
        animationView.playAnimation();
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(mAuth.getCurrentUser()!=null /*&& !Tools.compareTime()*/){
                    ScreenManager.goMainActivity(SplashActivity.this);
                    finish();
                    return;
                }
                ScreenManager.goRegisterActivity(SplashActivity.this);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!Tools.isGooglePlayServicesAvailable(SplashActivity.this)) return;
        startAnimationSplash();
    }
}
