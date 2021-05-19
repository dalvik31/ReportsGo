package com.epacheco.reports.view.mainAcitivityView;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.epacheco.reports.R;
import com.epacheco.reports.tools.Constants;
import com.epacheco.reports.tools.ReportsApplication;
import com.epacheco.reports.tools.ScreenManager;
import com.epacheco.reports.tools.Tools;
import com.epacheco.reports.view.searchElementsView.SearchElementView;

import com.epacheco.reports.databinding.ActivityMainClassBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class MainActivityViewClass extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ActivityMainClassBinding binding;
    private ImageView img_profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_class);
        mAuth = FirebaseAuth.getInstance();
        img_profile = findViewById(R.id.img_profile);
    }



    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser().getPhotoUrl() != null) {
            Glide
                    .with(ReportsApplication.getMyApplicationContext())
                    .load(com.epacheco.reports.tools.Tools.getFormatUrlImage(mAuth.getCurrentUser().getPhotoUrl()))
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.appBarLayout.getImageView());
        }else {
            img_profile.setImageResource(R.drawable.icon_person);
        }

    }


    public void goProfileActivity(View v) {
        ScreenManager.goProfileActivity(this);
    }

    public void goClientsActivity(View v) {
        ScreenManager.goClientsActivity(this, false);
    }

    public void goProductsActivity(View v) {
        ScreenManager.goProductsActivity(this, false);
    }

    public void goSaleActivity(View v) {
        ScreenManager.goSaleActivity(this, null, null);
    }

    public void goOrderActivity(View v) {
        ScreenManager.goOrderActivity(this, null);
    }

    public void goFinanceActivity(View v) {

       ScreenManager.goFinanceActivity(this);
    }


    public void goSearchElement(View view) {
        switch (view.getId()) {
            case R.id.fbtn_search_client:
                ScreenManager.goSearchActivity(this, SearchElementView.FROM_SEARCH_CLIENT);
                break;
            case R.id.fbtn_search_product:
                ScreenManager.goSearchActivity(this, SearchElementView.FROM_SEARCH_PRODUCT);
                break;
        }
    }

    @Override
    protected void onStop() {
        com.epacheco.reports.tools.Tools.setLongPreference(Constants.TIMER_SAVED, System.currentTimeMillis());
        super.onStop();
    }
}
