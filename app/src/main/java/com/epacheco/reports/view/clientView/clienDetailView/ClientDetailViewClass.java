package com.epacheco.reports.view.clientView.clienDetailView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import com.epacheco.reports.Model.ClientModel.ClientDetailModel.ClientDetailModelClass;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;
import com.epacheco.reports.R;
import com.epacheco.reports.tools.ScreenManager;
import com.epacheco.reports.databinding.ActivityClientDetailViewClassBinding;
import com.epacheco.reports.tools.Tools;

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
    com.epacheco.reports.tools.Tools.showToasMessage(this, getString(R.string.msg_client_detail_created));
  }

  @Override
  public void errorAddClientDetail(String error) {

    if(!error.isEmpty()) com.epacheco.reports.tools.Tools.showToasMessage(this, getString(R.string.msg_error_sistema));
  }


  @Override
  public void successGetClientDetail(ClientDetail clientDetail) {
    clientDebt = String.valueOf(clientDetail.getDebt());
    setClientDetail(clientDetail);
    binding.layoutClientInfo.lblClientAccountAmount.setText(String.format(getString(R.string.txt_client_amount_format),clientDebt));
  }

  @Override
  public void errorGetClientDetail(String error) {
    binding.layoutClientInfo.lblClientAccountAmount.setText(R.string.txt_client_amount_empty);
  }

  @Override
  public void successGetClient(Client client) {
    binding.layoutClientInfo.lblClientName.setText(String.format(getString(R.string.txt_client_name_format),client.getName(),client.getLastNanme()));
    binding.layoutClientInfo.lblClientDetail.setText(client.getDetail());
    binding.layoutClientInfo.lblDateName.setText(String.format(getString(R.string.txt_client_date_format), Tools.getFormatDate(client.getDateClient())));
    /**
     *PARA EL LIMITE DE CREDITO DEL CLIENTE
     */

        if(client.getLimit() > 0 && client.getLimit() < 100){
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.lblClientLimit.setTextColor(getColor(android.R.color.holo_red_dark));
            binding.lblClientLimit.setText(String.format(getString(R.string.txt_client_limit_format),String.valueOf(client.getLimit())));
          }
    }else if(client.getLimit() > 101 && client.getLimit() < 500){
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.lblClientLimit.setTextColor(getColor(android.R.color.holo_orange_light));
            binding.lblClientLimit.setText(String.format(getString(R.string.txt_client_limit_format),String.valueOf(client.getLimit())));
          }
        }else{
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.lblClientLimit.setTextColor(getColor(android.R.color.holo_green_dark));
            binding.lblClientLimit.setText(String.format(getString(R.string.txt_client_limit_format),String.valueOf(client.getLimit())));
          }

        }

    //binding.lblClientLimit.setText(String.format(getString(R.string.txt_client_limit_format),String.valueOf(client.getLimit())));
    setClientInformation(client);

    clientDetailModelClass.getClientDetail(client.getId());
  }

  @Override
  public void errorGetClient(String error) {
    finish();
    if(!error.isEmpty()) com.epacheco.reports.tools.Tools.showToasMessage(this, error);
  }


  public void addClientDetailAbono(View v) {
    if (validateValues()) {
      createClienDetail();
      clientDetailModelClass.addClientDetail(getClientDetail(),getClientInformation().getId());
    } else {
      com.epacheco.reports.tools.Tools.showToasMessage(this, getString(R.string.msg_amount_empty));
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
    clientDetail.setProductName(getClientInformation().getName());
    clientDetail.setProductPriceBuy(getClientInformation().getLimit());
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
