package com.epacheco.reports.view.clientView.clientView;

import android.Manifest;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.widget.SearchView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.epacheco.reports.Model.ClientModel.ClientModel.ClientModelClass;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.R;
import com.epacheco.reports.tools.ReportsApplication;
import com.epacheco.reports.tools.ReportsProgressDialog;
import com.epacheco.reports.tools.ScreenManager;
import com.epacheco.reports.view.clientView.clientAddView.ClientAddViewClass;
import com.epacheco.reports.databinding.ActivityClientsViewBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;

public class ClientsViewClass extends AppCompatActivity implements ClientViewInterface, SearchView.OnQueryTextListener, onItemClientClic, AdapterClients.OnClicListener{
  public final static String IS_SEARCH = "isSearch";
  public final static int CLIENT_SELECTED = 0;
  private ClientModelClass clientModelClass;
  private ActivityClientsViewBinding binding;
  private boolean isSearch;
  private ReportsProgressDialog progressbar;
  private Handler handler;
  private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
  private String phone1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this,R.layout.activity_clients_view);
    inicializateElements();
    clientModelClass = new ClientModelClass(this);
    downloadClient(null);
  }

  private void inicializateElements() {
    progressbar = ReportsProgressDialog.getInstance();
    if(getIntent()!=null){
      setSearch(getIntent().getBooleanExtra(IS_SEARCH,false));
    }

    if( FirebaseAuth.getInstance().getCurrentUser()!=null ){
      if(  FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()!=null){
        Glide.with(ReportsApplication.getMyApplicationContext()).load(com.epacheco.reports.tools.Tools.getFormatUrlImage( FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()))  .apply(
            RequestOptions.circleCropTransform()).into(binding.imgProfile);
      }
    }
    binding.searchView.setOnQueryTextListener(this);
  }


  @Override
  public boolean onQueryTextSubmit(String query) {
    return false;
  }

  @Override
  public boolean onQueryTextChange(String newText) {
    downloadClient(newText);
    return true;
  }
  private void downloadClient(final String name){

    getHandler().removeCallbacksAndMessages(null);

    if(name!=null){
      getHandler().postDelayed(new Runnable() {
        @Override
        public void run() {
          showProgress(getString(R.string.msg_search_client));
          clientModelClass.downloadClients(name);
        }
      }, 1000);
    }else{
      showProgress(getString(R.string.msg_search_client));
      clientModelClass.downloadClients(null);
    }
  }

  @Override
  public FragmentActivity getMyActivity() {
    return this;
  }

  @Override
  public void successDownloadClients(ArrayList<Client> listCliets) {
    hideProgress();
    if(listCliets!=null && !listCliets.isEmpty()){
      binding.recyclerListClient.setHasFixedSize(true);
      binding.recyclerListClient.setLayoutManager(new GridLayoutManager(this, 2));
      AdapterClients adapterClients = new AdapterClients(listCliets,this);
      adapterClients.setOnItemClientClic(this);
      binding.recyclerListClient.setAdapter(adapterClients);
      binding.lblZeroClients.setVisibility(View.GONE);
      binding.imgEmptyClients.setVisibility(View.GONE);
      binding.recyclerListClient.setVisibility(View.VISIBLE);
    }else{
      binding.lblZeroClients.setVisibility(View.VISIBLE);
      binding.imgEmptyClients.setVisibility(View.VISIBLE);
      binding.recyclerListClient.setVisibility(View.GONE);
    }

  }

  @Override
  public void errorDownloadClients(String error) {
    hideProgress();
    binding.lblZeroClients.setVisibility(View.VISIBLE);
    binding.imgEmptyClients.setVisibility(View.VISIBLE);
    binding.recyclerListClient.setVisibility(View.GONE);
  }

  @Override
  public void onItemClientClic(View v, String clientId) {
    if(v.getId() == R.id.btn_modify){
      ScreenManager.goAddClientActivity(this,clientId);
    }else  if(v.getId() == R.id.btn_detail){
      ScreenManager.goDetailClientActivity(this,clientId);
    }else if(v.getId() == R.id.card_client) {
      if(isSearch()){
        Intent intent = new Intent();
        intent.putExtra(ClientAddViewClass.CLIENT_ID,clientId);
        setResult(RESULT_OK, intent);
        finish();
      }else{
        ScreenManager.goDetailClientActivity(this,clientId);
      }
    }

  }
  public boolean isSearch() {
    return isSearch;
  }

  public void setSearch(boolean search) {
    isSearch = search;
  }
  public void goProfileActivity(View v){
    ScreenManager.goProfileActivity(this);
  }
  public void goAddClientActivity(View v){
    ScreenManager.goAddClientActivity(getMyActivity(),null);

  }

  private void showProgress(String message){
    progressbar.showProgress(this,message);
    binding.progressDownloadclient.setVisibility(View.VISIBLE);
  }
  private void hideProgress(){
    progressbar.hideProgress();
    binding.progressDownloadclient.setVisibility(View.GONE);
  }

  public Handler getHandler() {
    if(handler==null){
      handler = new Handler();
    }
    return handler;
  }



  @Override
  public void onItemClic(String phone) {

    phone1 = phone;
    request_callphone_Permission();

  }

  private void request_callphone_Permission() {

    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {

      Snackbar.make(binding.recyclerListClient, "Requiere aceptar permiso para continuar con la llamada ", Snackbar.LENGTH_INDEFINITE).setAction("ACEPTAR", new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          ActivityCompat.requestPermissions(ClientsViewClass.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }
      }).show();
    }else{
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
    }

  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
      StrartActivity(phone1);
    }
  }

  private void StrartActivity(String Pphone){

    Intent intent = new Intent(Intent.ACTION_CALL);
    intent.setData(Uri.parse("tel:" + Pphone));
    startActivity(intent);

  }

}
