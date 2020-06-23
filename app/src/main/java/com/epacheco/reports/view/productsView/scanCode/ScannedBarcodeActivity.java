package com.epacheco.reports.view.productsView.scanCode;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.epacheco.reports.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScannedBarcodeActivity extends AppCompatActivity {
  public final static String CODE_SCANNER = "codeScanner";
  public final static int SCANBAR_ACTIVITY = 0;
  private SurfaceView surfaceView;
  private BarcodeDetector barcodeDetector;
  private CameraSource cameraSource;
  private String intentData = "";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scan_barcode);

    initViews();
  }

  private void initViews() {
    surfaceView = findViewById(R.id.surfaceView);
  }

  private void initialiseDetectorsAndSources() {

    Toast.makeText(getApplicationContext(), getString(R.string.msg_camera_scan), Toast.LENGTH_SHORT).show();

    barcodeDetector = new BarcodeDetector.Builder(this)
        .setBarcodeFormats(Barcode.ALL_FORMATS)
        .build();

    cameraSource = new CameraSource.Builder(this, barcodeDetector)
        .setRequestedPreviewSize(1920, 1080)
        .setAutoFocusEnabled(true) //you should add this feature
        .build();

    surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
      @SuppressLint("MissingPermission")
      @Override
      public void surfaceCreated(SurfaceHolder holder) {
        try {
          cameraSource.start(surfaceView.getHolder());
        } catch (IOException e) {
          e.printStackTrace();
        }


      }

      @Override
      public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
      }

      @Override
      public void surfaceDestroyed(SurfaceHolder holder) {
        cameraSource.stop();
      }
    });

    barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
      @Override
      public void release() {

      }

      @Override
      public void receiveDetections(Detector.Detections<Barcode> detections) {
        final SparseArray<Barcode> barcodes = detections.getDetectedItems();
        if (barcodes.size() != 0) {
          intentData = barcodes.valueAt(0).displayValue;
          if(intentData!=null && !intentData.isEmpty()){
            Intent intent = new Intent();
            intent.putExtra(CODE_SCANNER,intentData);
            setResult(RESULT_OK, intent);
            finish();
          }else{
            com.epacheco.reports.Tools.Tools.showToasMessage(ScannedBarcodeActivity.this,getString(R.string.msg_error_sistema));
          }
        }
      }
    });
  }


  @Override
  protected void onPause() {
    super.onPause();
    cameraSource.release();
  }

  @Override
  protected void onResume() {
    super.onResume();
    initialiseDetectorsAndSources();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
  }
}
