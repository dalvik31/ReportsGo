package com.epacheco.reports.View.TestNFC;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Parcelable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.epacheco.reports.R;
import java.nio.charset.Charset;


/**
 *
 * EN ESTA CLASE SE ENCUENTRA LO NECESARIO PARA PODER LEER DATOS POR MEDIO DE NFC
 * FUNCIONA SE TIENE QUE MODIFICAR EL MANIFEST:
 * <uses-permission android:name="android.permission.NFC"/>
 *
 * Y SE AGREGA UN FILTER A LA ACTIVIDAD QUE QUEREMOS QUE LEA LA INFORMACION:
 *
 * <intent-filter>
 *         <action android:name="android.nfc.action.NDEF_DISCOVERED"/>
 *
 *         <category android:name="android.intent.category.DEFAULT"/>
 *
 *         <data android:mimeType="application/com.epacheco.reports"/>
 *       </intent-filter>
 *
 *  OJO: ESTE NOMBRE DE PAQUETE: application/com.epacheco.reports
 *  DEBE SER EL MISMO QUE SE OCUPE EN ESTE METODO: createNdefMessage
 *
 * */
public class TestNfcView extends AppCompatActivity implements NfcAdapter.OnNdefPushCompleteCallback, NfcAdapter.CreateNdefMessageCallback{
  NfcAdapter myNfcAdapter;
  String textoaenviar="";
  EditText tex;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_nfc_view);
    tex = findViewById(R.id.textView);
    setUpAndroidBeam();
  }


  private void setUpAndroidBeam() {
    PackageManager pm = getPackageManager();
    // Check whether NFC is available on device
    if (!pm.hasSystemFeature(PackageManager.FEATURE_NFC)) {
      // NFC is not available on the device.
      Toast.makeText(this,"nfc no disponible",Toast.LENGTH_LONG).show();

    }
    // Check whether device is running Android 4.1 or higher
    else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
      // Android Beam feature is not supported.
      Toast.makeText(this,"nfc no soportado",Toast.LENGTH_LONG).show();
    }
  }

  public void manageNfc(View view) {
    myNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    if (myNfcAdapter != null) {
      textoaenviar = tex.getText().toString();
      myNfcAdapter.setNdefPushMessageCallback(this,this);
      myNfcAdapter.setOnNdefPushCompleteCallback(this,this);
    }
  }

  @Override
  public NdefMessage createNdefMessage(NfcEvent event) {
    Time time = new Time();
    time.setToNow();
    String text = textoaenviar;
    NdefMessage msg = new NdefMessage(
        new NdefRecord[]{createMimeRecord(
            "application/com.epacheco.reports", text.getBytes())});
    return msg;
  }

  public NdefRecord createMimeRecord(String mimeType, byte[] payload) {
    byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
    NdefRecord mimeRecord = new NdefRecord(
        NdefRecord.TNF_MIME_MEDIA, mimeBytes, new byte[0], payload);
    return mimeRecord;
  }

  @Override
  public void onNdefPushComplete(NfcEvent event) {

  }



  @Override
  protected void onResume() {
    super.onResume();
    if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
      processIntent(getIntent());
    }
  }


  void processIntent(Intent intent) {

    Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
        NfcAdapter.EXTRA_NDEF_MESSAGES);
    // only one message sent during the beam
    NdefMessage msg = (NdefMessage) rawMsgs[0];
    // record 0 contains the MIME type, record 1 is the AAR, if present
    String temp = new String(msg.getRecords()[0].getPayload());
    Log.e("ya salio","aqui est: "+temp);
    //String[] arrrayTemp = temp.split("//");

  }

  @Override
  public void onNewIntent(Intent intent) {
    // onResume gets called after this to handle the intent
    setIntent(intent);
  }

}
