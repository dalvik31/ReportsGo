package com.epacheco.reports.view.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.ScreenManager;
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
        if(!com.epacheco.reports.Tools.Tools.isGooglePlayServicesAvailable(SplashActivity.this)) return;
        startAnimationSplash();
    }
}
