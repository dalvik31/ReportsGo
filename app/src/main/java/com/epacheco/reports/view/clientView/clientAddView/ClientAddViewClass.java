package com.epacheco.reports.view.clientView.clientAddView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.epacheco.reports.Model.ClientModel.ClientAddModel.ClientAddModelClass;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.ReportsDialogGlobal;
import com.epacheco.reports.Tools.ReportsProgressDialog;
import com.epacheco.reports.databinding.ActivityClientAddViewClassBinding;
import com.google.android.material.snackbar.Snackbar;

public class ClientAddViewClass extends AppCompatActivity implements ClientAddViewInterface{

  public final static String CLIENT_ID = "clientId";
  static final int PICK_CONTACT_REQUEST=1;
  private static final int READ_CONTACTS_PERMISSION = 2;
  public static Uri contactUri;

  private ClientAddModelClass clientAddModelClass;
  private ActivityClientAddViewClassBinding binding;
  private Client objClient;
  private String nameSendClient;
  private double limitSendClient;
  private String clientId;
  private ReportsProgressDialog progressbar;
  private EditText txt_client_name;
  private EditText txt_client_phone;
  private RelativeLayout Rltv_Contact;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this,R.layout.activity_client_add_view_class);
    inicializateElements();
    validateModifyClient();
    Rltv_Contact = findViewById(R.id.Rltv_Contact);
  }


  private void validateModifyClient() {
    if(clientId!=null && !clientId.isEmpty()){
      binding.appBar.setTitle(R.string.lbl_modify_client);
      binding.btnCreateAccoount.setVisibility(View.GONE);
      binding.containerModify.setVisibility(View.VISIBLE);
      showProgress(getString(R.string.lbl_search_clients));
      clientAddModelClass.getClient(clientId);
      binding.btnModifyClient.setVisibility(View.VISIBLE);
      binding.btnAddClient.setVisibility(View.GONE);
    }else{
      binding.btnCreateAccoount.setVisibility(View.VISIBLE);
      binding.containerModify.setVisibility(View.GONE);
      binding.btnModifyClient.setVisibility(View.GONE);
      binding.btnAddClient.setVisibility(View.VISIBLE);
    }
  }

  public void addClient(View v){
    if(validateItems()){
      createClient(null);
      showProgress(getString(R.string.msg_client_save));
      clientAddModelClass.addClient(getObjClient());
    }else{
      com.epacheco.reports.Tools.Tools.showToasMessage(this,getString(R.string.msg_name_empty));
    }

  }

  private void inicializateElements() {
    progressbar = ReportsProgressDialog.getInstance();
    clientId = getIntent()!=null ?  getIntent().getStringExtra(CLIENT_ID) : "";
    clientAddModelClass = new ClientAddModelClass(this);
  }

  @Override
  public FragmentActivity getMyActivity() {
    return this;
  }

  @Override
  public void succesAddClient() {
    hideProgress();
    com.epacheco.reports.Tools.Tools.showToasMessage(this,getString(R.string.msg_client_created));
    finish();
  }

  @Override
  public void errorAddClient(String error) {
    com.epacheco.reports.Tools.Tools.showToasMessage(this,error);
  }

  @Override
  public void successGetClient(Client client) {
    hideProgress();
    setObjClient(client);
    binding.txtClientName.setText(client.getName());
    binding.txtClientLastname.setText(client.getLastNanme());
    binding.txtClientDetail.setText(client.getDetail());
    binding.txtClientLimit.setText(String.valueOf(client.getLimit()));
    binding.txtClientPhone.setText(client.getPhone());

  }

  @Override
  public void errorGetClient(String error) {
    if(error.isEmpty()){
      finish();
    }else{
      com.epacheco.reports.Tools.Tools.showToasMessage(this,error);
      finish();
    }

  }

  @Override
  public void successModifyClient() {
    hideProgress();
    com.epacheco.reports.Tools.Tools.showToasMessage(this,getString(R.string.msg_client_modify));
    finish();
  }

  @Override
  public void errorModifyClient(String error) {
    com.epacheco.reports.Tools.Tools.showToasMessage(this,error);
  }

  @Override
  public void succesRemoveClient() {
    com.epacheco.reports.Tools.Tools.showToasMessage(this,getString(R.string.msg_client_delete));
    finish();
  }

  @Override
  public void errorRemoveClient(String error) {
    com.epacheco.reports.Tools.Tools.showToasMessage(this,error);
  }

  public Client getObjClient() {
    if(objClient== null){
      setObjClient(new Client());
    }
    return objClient;
  }

  public void setObjClient(Client objClient) {
    this.objClient = objClient;
  }

  private void createClient(Client client){
      Client newClient = getObjClient();
      newClient.setId(client == null ? nameSendClient+System.currentTimeMillis(): client.getId());
      newClient.setDateClient(client == null ? String.valueOf(System.currentTimeMillis()): client.getDateClient());
      newClient.setName(nameSendClient);
      newClient.setLastNanme(binding.txtClientLastname.getText().toString());
      newClient.setDetail(binding.txtClientDetail.getText().toString());
      newClient.setPhone(binding.txtClientPhone.getText().toString());
      newClient.setLimit(limitSendClient);
      if(client!=null && client.getClientsDetails()!=null){
        newClient.setClientsDetails(client.getClientsDetails());
      }



  }




  private boolean validateItems(){
    boolean validateItems = false;
    if(binding.txtClientName.getText()!=null && !binding.txtClientName.getText().toString().isEmpty()){
      nameSendClient = binding.txtClientName.getText().toString();
      validateItems = true;
    }
    if(binding.txtClientLimit.getText()!=null && !binding.txtClientLimit.getText().toString().isEmpty()){
      limitSendClient = Double.parseDouble(binding.txtClientLimit.getText().toString());
    }else{
      limitSendClient = 1200;
    }

    return validateItems;
  }

  public void modifyAccount(View v){
    if(validateItems()){
      createClient(getObjClient());
      showProgress(getString(R.string.msg_client_save));
      clientAddModelClass.modifyClient(getObjClient());
    }else{
      com.epacheco.reports.Tools.Tools.showToasMessage(this,getString(R.string.msg_name_empty));
    }
  }



  public void deleteAccount(View v){
    ReportsDialogGlobal.showDialogAccept(this, getString(R.string.title_message_delete_elemnt),
        getString(R.string.body_message_delete_elemnt),
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            clientAddModelClass.removeClient(getObjClient().getId());
          }
        }
    );

  }

  private void showProgress(String message){
    progressbar.showProgress(this,message);
    binding.progressUploadProduct.setVisibility(View.VISIBLE);
  }
  private void hideProgress(){
    progressbar.hideProgress();
    binding.progressUploadProduct.setVisibility(View.GONE);
  }


  protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    super.onActivityResult(requestCode, resultCode, intent);
    if (requestCode == PICK_CONTACT_REQUEST) {
      if (resultCode == RESULT_OK) {

        contactUri = intent.getData();

        renderContact(contactUri);
      }
    }
  }

  private void renderContact(Uri uri) {

   txt_client_name = findViewById(R.id.txt_client_name);
   txt_client_phone = findViewById(R.id.txt_client_phone);


    txt_client_name.setText(getName(uri));
    txt_client_phone.setText(getPhone(uri));

  }

  private String getName(Uri uri) {

    String name = null;

    ContentResolver contentResolver = getContentResolver();

    Cursor c = contentResolver.query(
            uri, new String[]{ContactsContract.Contacts.DISPLAY_NAME}, null, null, null);

    if(c.moveToFirst()){
      name = c.getString(0);
    }

    c.close();

    return name;
  }

  private String getPhone(Uri uri) {
    String id = null;
    String phone = null;

    Cursor contactCursor = getContentResolver().query(
            uri, new String[]{ContactsContract.Contacts._ID},
            null, null, null);


    if (contactCursor.moveToFirst()) {
      id = contactCursor.getString(0);
    }
    contactCursor.close();

    String selectionArgs =
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                    ContactsContract.CommonDataKinds.Phone.TYPE+"= " +
                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;

    Cursor phoneCursor = getContentResolver().query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER },
            selectionArgs
            ,
            new String[] { id },
            null
    );
    if (phoneCursor.moveToFirst()) {
      phone = phoneCursor.getString(0);
    }
    phoneCursor.close();

    return phone;
  }

  public void Search_contact(View v){

    requestCameraPermission();

  }

  private void requestCameraPermission() {

    //shouldShowRequestPermissionRationale nos dice si el usuairo rechazo alguna vez los permisos
    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {

      //El usuario vuelve a dar clic en el boton se muestra un mensaje explicando al usuario por que es necesario conceder los permisos.
      Snackbar.make(Rltv_Contact, "Requiere aceptar permiso para acceder a contactos ", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          ActivityCompat.requestPermissions(ClientAddViewClass.this, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_PERMISSION );
        }
      }).show();
    }else{
      //El usuario entra por primera vez y da clic en el boton se muestra la ventana para aceptar  los persmisos
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_PERMISSION );
    }

  }

  private void Obtener_contacto(){

    Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
    startActivityForResult(i, PICK_CONTACT_REQUEST);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
      Obtener_contacto();
    }else{
      Toast.makeText(this,"que mal no has aceptado",Toast.LENGTH_SHORT).show();
    }
  }

}
