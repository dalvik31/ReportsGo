package com.epacheco.reports.view.clientView.clientDetailListView;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;
import com.epacheco.reports.Model.ClientModel.ClientDetailListModel.ClientDetailListModelClass;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;
import com.epacheco.reports.R;
import com.epacheco.reports.tools.ReportsProgressDialog;
import com.epacheco.reports.databinding.ActivityClientDetailListViewBinding;
import java.util.List;

public class ClientDetailListViewClass extends AppCompatActivity implements ClientDetailListViewInterface {
  public final static String CLIENT_ID = "clientId";
  public final static String CLIENT_DEBP = "clientDebp";
  private ActivityClientDetailListViewBinding binding;
  private ClientDetailListModelClass clientDetailListModelClass;
  private AdapterClientDetailPayments adapterClients;
  private String clientId;
  private String clientDebt;
  private Client clientSelected;
  private ReportsProgressDialog progressbar;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this,R.layout.activity_client_detail_list_view);
    inicializateElements();
  }

  private void inicializateElements() {
    progressbar = ReportsProgressDialog.getInstance();
    if(getIntent()==null){
     return;
    }
    clientId = getIntent().getStringExtra(CLIENT_ID);
    clientDebt = getIntent().getStringExtra(CLIENT_DEBP);
    clientDetailListModelClass = new ClientDetailListModelClass(this);
    binding.progressDownloadclientDetailPayments.setVisibility(View.VISIBLE);
    showProgress("Buscando detalle");
    clientDetailListModelClass.getClient(clientId);



    binding.progressDownloadclientDetailPayments.setVisibility(View.GONE);
    binding.recyclerListClientFDetailPayments.setHasFixedSize(true);
    binding.recyclerListClientFDetailPayments.setLayoutManager(new LinearLayoutManager(this));
    adapterClients = new AdapterClientDetailPayments();
    binding.recyclerListClientFDetailPayments.setAdapter(adapterClients);
  }

  @Override
  public FragmentActivity getMyActivity() {
    return this;
  }

  @Override
  public void successGetDetailClientList(List<ClientDetail> clientDetail) {
    binding.progressDownloadclientDetailPayments.setVisibility(View.GONE);
    if(clientDetail.size()>0){
      adapterClients.setList(clientDetail);
      adapterClients.notifyDataSetChanged();
    }else{
      finish();
      com.epacheco.reports.tools.Tools.showToasMessage(this,getString(R.string.msg_zero_elements));
    }
   }





  @Override
  public void errorGetDetailClientList(String error) {

  }

  @Override
  public void successGetClient(Client client) {
    hideProgress();
    setClientSelected(client);
    binding.progressDownloadclientDetailPayments.setVisibility(View.VISIBLE);
    clientDetailListModelClass.getClientDetailList(client.getId());
    binding.layoutClientInfo.lblDateName.setText(String.format(getString(R.string.txt_client_date_format), com.epacheco.reports.Tools.Tools.getFormatDate(client.getDateClient())));
    binding.layoutClientInfo.lblClientAccountAmount.setText(String.format(getString(R.string.txt_client_amount_format),String.valueOf(clientDebt)));

    binding.layoutClientInfo.lblClientName.setText(String.format(getString(R.string.txt_client_name_format),client.getName(),client.getLastNanme()));
    binding.layoutClientInfo.lblClientDetail.setText(client.getDetail());

  }

  @Override
  public void errorGetClient(String error) {
    hideProgress();
    finish();
    com.epacheco.reports.tools.Tools.showToasMessage(this,getString(R.string.msg_error_sistema));
  }

  public void shareListClienDetailPayments(View v){
    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
    intent.setType("text/plain");
    String shareBodyText = String.format(getString(R.string.lbl_body_message_detail_client),String.valueOf(clientDebt))+ adapterClients.getCadenaShare();
    intent.putExtra(android.content.Intent.EXTRA_SUBJECT, String.format(getString(R.string.lbl_subject_message_detail_client),getClientSelected().getName(),getClientSelected().getLastNanme()));
    intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
    startActivity(Intent.createChooser(intent, "Choose sharing method"));
  }

  public Client getClientSelected() {
    return clientSelected;
  }

  public void setClientSelected(Client clientSelected) {
    this.clientSelected = clientSelected;
  }

  private void showProgress(String message){
    progressbar.showProgress(this,message);
    binding.progressDownloadclientDetailPayments.setVisibility(View.VISIBLE);
  }
  private void hideProgress(){
    progressbar.hideProgress();
    binding.progressDownloadclientDetailPayments.setVisibility(View.GONE);
  }
  public void goProfileActivity(View v) {
    ScreenManager.goProfileActivity(this);
  }
}
