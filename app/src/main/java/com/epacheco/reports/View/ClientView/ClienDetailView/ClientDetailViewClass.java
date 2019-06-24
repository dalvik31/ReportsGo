package com.epacheco.reports.View.ClientView.ClienDetailView;

import android.databinding.DataBindingUtil;
import android.renderscript.ScriptGroup.Binding;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.epacheco.reports.Model.ClientModel.ClientDetailModel.ClientDetailModelClass;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.ScreenManager;
import com.epacheco.reports.Tools.Tools;
import com.epacheco.reports.databinding.ActivityClientDetailViewClassBinding;
import java.util.ArrayList;

public class ClientDetailViewClass extends AppCompatActivity implements ClientDetailViewInterface {

  public final static String CLIENT_ID = "clientId";
  private ClientDetailModelClass clientDetailModelClass;
  private String clientId;
  private Client clientInformation;
  private ActivityClientDetailViewClassBinding binding;
  private String paymentConcept;
  private ClientDetail clientDetail;
  private String clientDebt;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_client_detail_view_class);
    inicializateElements();
  }

  private void inicializateElements() {
    clientDetailModelClass = new ClientDetailModelClass(this);
    if (getIntent() != null) {
      clientId = getIntent().getStringExtra(CLIENT_ID);
    }
    clientDetailModelClass.getClient(clientId);
  }

  @Override
  public FragmentActivity getMyActivity() {
    return this;
  }

  @Override
  public void successAddClientDetail() {
    binding.txtClientAmount.setText("");
    Tools.showToasMessage(this, getString(R.string.msg_client_detail_created));
  }

  @Override
  public void errorAddClientDetail(String error) {

    if(!error.isEmpty()) Tools.showToasMessage(this, getString(R.string.msg_error_sistema));
  }


  @Override
  public void successGetClientDetail(ClientDetail clientDetail) {
    clientDebt = String.valueOf(clientDetail.getDebt());
    setClientDetail(clientDetail);
    binding.lblClientAccountAmount.setText(String.format(getString(R.string.txt_client_amount_format),clientDebt));
  }

  @Override
  public void errorGetClientDetail(String error) {
    binding.lblClientAccountAmount.setText(R.string.txt_client_amount_empty);
  }

  @Override
  public void successGetClient(Client client) {
    binding.lblClientName.setText(String.format(getString(R.string.txt_client_name_format),client.getName(),client.getLastNanme()));
    binding.lblClientDetail.setText(client.getDetail());
    binding.lblDateName.setText(String.format(getString(R.string.txt_client_date_format),Tools.getFormatDate(client.getDateClient())));
    binding.lblClientLimit.setText(String.format(getString(R.string.txt_client_limit_format),String.valueOf(client.getLimit())));
    setClientInformation(client);

    clientDetailModelClass.getClientDetail(client.getId());
  }

  @Override
  public void errorGetClient(String error) {
    finish();
    if(!error.isEmpty())Tools.showToasMessage(this, error);
  }


  public void addClientDetailAbono(View v) {
    if (validateValues()) {
      createClienDetail();
      clientDetailModelClass.addClientDetail(getClientDetail(),getClientInformation().getId());
    } else {
      Tools.showToasMessage(this, getString(R.string.msg_amount_empty));
    }
  }

  public Client getClientInformation() {
    return clientInformation;
  }

  public void setClientInformation(Client clientInformation) {
    this.clientInformation = clientInformation;
  }

  public ClientDetail getClientDetail() {
    if (clientDetail == null) {
      setClientDetail(new ClientDetail());
    }
    return clientDetail;
  }

  public void setClientDetail(ClientDetail clientDetail) {
    this.clientDetail = clientDetail;
  }

  private boolean validateValues() {
    boolean validValues = false;
    if (binding.txtClientAmount.getText() != null && !binding.txtClientAmount.getText().toString()
        .isEmpty()) {
      validValues = true;

    }

    if (binding.txtClientConcept.getText() != null && !binding.txtClientConcept.getText().toString().isEmpty()) {
      paymentConcept = binding.txtClientConcept.getText().toString();
    }else{
      paymentConcept = getString(R.string.txt_client_concept_payment);
    }

    return validValues;
  }


  private void createClienDetail() {
    ClientDetail clientDetail = getClientDetail();
    double amountPayment = Double.parseDouble(binding.txtClientAmount.getText().toString());
    clientDetail.setDatePayment(String.valueOf(System.currentTimeMillis()));
    clientDetail.setAmount(amountPayment);
    clientDetail.setConcept(paymentConcept);
    clientDetail.setPay(true);
    clientDetail.setUrlImage(null);
    clientDetail.setCantProduct(0);
    clientDetail.setDebt(clientDetail.getDebt() - amountPayment);
  }

  public void goDetailPayments(View v){
    ScreenManager.goDetailListClientActivity(this,getClientInformation().getId(),clientDebt);
  }

  public void goSaleActivity(View view){
    ScreenManager.goSaleActivity(this,clientId,null);
  }

  public void goNewOrder(View view){
    ScreenManager.goOrderActivity(this,clientId);
  }
}
