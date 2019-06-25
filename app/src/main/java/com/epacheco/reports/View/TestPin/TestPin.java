package com.epacheco.reports.View.TestPin;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.epacheco.reports.R;


public class TestPin extends AppCompatActivity {

  com.epacheco.reports.View.TestPin.OtpEditText inputPin;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_pin);
    inputPin = findViewById(R.id.pin);




  }
}
