package com.epacheco.reports.View.TestPin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.epacheco.reports.R;
import com.epacheco.reports.View.TestPin.OtpEditText.PinViewEventListener;


public class TestPin extends AppCompatActivity {

  com.epacheco.reports.View.TestPin.OtpEditText inputPin;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_pin);
    inputPin = findViewById(R.id.pin);




  }
}
