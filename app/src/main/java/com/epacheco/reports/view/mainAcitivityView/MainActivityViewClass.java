package com.epacheco.reports.view.mainAcitivityView;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

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

public class MainActivityViewClass extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ActivityMainClassBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_class);
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser().getPhotoUrl() != null) {
            Glide
                    .with(ReportsApplication.getMyApplicationContext())
                    .load(Tools.getFormatUrlImage(mAuth.getCurrentUser().getPhotoUrl()))
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.imgProfile);
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
        Tools.setLongPreference(Constants.TIMER_SAVED, System.currentTimeMillis());
        super.onStop();
    }
}
