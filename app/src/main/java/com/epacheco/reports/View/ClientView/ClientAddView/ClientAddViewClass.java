package com.epacheco.reports.View.ClientView.ClientAddView;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.epacheco.reports.Model.ClientModel.ClientAddModel.ClientAddModelClass;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.ReportsDialogGlobal;
import com.epacheco.reports.Tools.ReportsProgressDialog;
import com.epacheco.reports.Tools.ScreenManager;
import com.epacheco.reports.Tools.Tools;
import com.epacheco.reports.databinding.ActivityClientAddViewClassBinding;

public class ClientAddViewClass extends AppCompatActivity implements ClientAddViewInterface{

  public final static String CLIENT_ID = "clientId";

  private ClientAddModelClass clientAddModelClass;
  private ActivityClientAddViewClassBinding binding;
  private Client objClient;
  private String nameSendClient;
  private double limitSendClient;
  private String clientId;
  private ReportsProgressDialog progressbar;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this,R.layout.activity_client_add_view_class);
    inicializateElements();
    validateModifyClient();
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
      Tools.showToasMessage(this,getString(R.string.msg_name_empty));
    }

  }

  private void inicializateElements() {
    progressbar = ReportsProgressDialog.getInstance(this);
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
    Tools.showToasMessage(this,getString(R.string.msg_client_created));
    finish();
  }

  @Override
  public void errorAddClient(String error) {
    Tools.showToasMessage(this,error);
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
      Tools.showToasMessage(this,error);
      finish();
    }

  }

  @Override
  public void successModifyClient() {
    hideProgress();
    Tools.showToasMessage(this,getString(R.string.msg_client_modify));
    finish();
  }

  @Override
  public void errorModifyClient(String error) {
    Tools.showToasMessage(this,error);
  }

  @Override
  public void succesRemoveClient() {
    Tools.showToasMessage(this,getString(R.string.msg_client_delete));
    finish();
  }

  @Override
  public void errorRemoveClient(String error) {
    Tools.showToasMessage(this,error);
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
      Tools.showToasMessage(this,getString(R.string.msg_name_empty));
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

}
