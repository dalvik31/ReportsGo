package com.epacheco.reports.View.OrderView.OrderCreateView;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.bumptech.glide.Glide;
import com.epacheco.reports.Model.OrderModel.CreateOrderModel.OrderCreateModelClass;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.ReportsApplication;
import com.epacheco.reports.Tools.ReportsProgressDialog;
import com.epacheco.reports.Tools.ScreenManager;
import com.epacheco.reports.Tools.Tools;
import com.epacheco.reports.View.ClientView.ClientAddView.ClientAddViewClass;
import com.epacheco.reports.View.ClientView.ClientView.ClientsViewClass;
import com.epacheco.reports.View.OrderView.OrderDetailView.OrderDetailView;
import com.epacheco.reports.databinding.ActivityOrderCreateViewBinding;
import com.google.firebase.auth.FirebaseAuth;

public class OrderCreateView extends AppCompatActivity implements OrderCreateViewInterface{

  private ActivityOrderCreateViewBinding binding;
  private OrderCreateModelClass orderCreateModelClass;
  private String orderListId;
  private ReportsProgressDialog progressbar;
  private Client selectedClient;
  private String nameOrder;
  private String descrptionOrder;
  private String typeSelected = ReportsApplication.getMyApplicationContext().getString(R.string.lbl_select_product_type_empty);
  private FirebaseAuth mAuth;
  private String sizeSelected = ReportsApplication.getMyApplicationContext().getString(R.string.lbl_select_product_type_empty);
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this,R.layout.activity_order_create_view);
    initializateElements();
  }

  private void initializateElements() {
    mAuth = FirebaseAuth.getInstance();
    progressbar = ReportsProgressDialog.getInstance(this);
    binding.txtOrderSize.setText(sizeSelected);
    binding.txtOrderGender.setText(typeSelected);
    Bundle extras = getIntent().getExtras();
    orderCreateModelClass = new OrderCreateModelClass(this);
    if(extras!=null){
      orderListId = extras.getString(OrderDetailView.ORDER_ID);
      if(extras.containsKey(OrderDetailView.CLIENT_ID)){
        showProgress(getString(R.string.lbl_search_clients));
        orderCreateModelClass.getClient(extras.getString(OrderDetailView.CLIENT_ID));
        extras.remove(OrderDetailView.CLIENT_ID);
      }
    }

  }

  public void searchClient(View view){
    ScreenManager.goClientsActivity(this,true);
  }


  public void createNewOrder(View view){
    if(validateInputs()){
      OrderDetail orderDetail = new OrderDetail();
      String orderSize = binding.txtOrderSize.getText().toString();
      String orderColor = binding.txtOrderColor.getText().toString();
      String orderGender = binding.txtOrderGender.getText().toString();
      orderDetail.setOrderName(nameOrder);
      orderDetail.setOrderDescription(descrptionOrder);
      orderDetail.setOrderId(String.valueOf(System.currentTimeMillis()));
      orderDetail.setOrderListId(orderListId);
      orderDetail.setOrderClient(getSelectedClient());
      orderDetail.setOrderSize(orderSize);
      orderDetail.setOrderColor(orderColor);
      orderDetail.setOrderGender(orderGender);
      orderDetail.setOrderBuy(false);
      orderCreateModelClass.createNewOrder(orderDetail);
    }

  }
  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == ClientsViewClass.CLIENT_SELECTED && resultCode == RESULT_OK) {
      if (data == null) {
        return;
      }
      showProgress(getString(R.string.lbl_search_clients));
      orderCreateModelClass.getClient(data.getStringExtra(ClientAddViewClass.CLIENT_ID));
    }

  }

  @Override
  public FragmentActivity getMyActivity() {
    return null;
  }

  @Override
  public void successCreateOrder() {
    Tools.showToasMessage(this,"Pedido creado");
    finish();
  }

  @Override
  public void errorCreateOrder(String error) {
    Tools.showToasMessage(this,error);
  }

  @Override
  public void successGetClient(Client client) {
    setSelectedClient(client);
    hideProgress();
    binding.btnNameClient.setText(String.format(ReportsApplication.getMyApplicationContext().getString(R.string.txt_client_name_format), client.getName(), client.getLastNanme()));
    binding.txtSaleClientName.setVisibility(View.VISIBLE);

  }



  @Override
  public void onStart() {
    super.onStart();
    if(mAuth.getCurrentUser()!=null ){
      if( mAuth.getCurrentUser().getPhotoUrl()!=null){
        Glide.with(ReportsApplication.getMyApplicationContext()).load(
            Tools.getFormatUrlImage(mAuth.getCurrentUser().getPhotoUrl())).into(binding.imgProfile);
      }
    }
  }

  @Override
  public void errorGetClient(String error) {
    Tools.showToasMessage(this,error);
  }

  public Client getSelectedClient() {
    return selectedClient;
  }

  public void setSelectedClient(Client selectedClient) {
    this.selectedClient = selectedClient;
  }

  private void showProgress(String message){
    progressbar.showProgress(this,message);
    binding.progressDownloadclient.setVisibility(View.VISIBLE);
  }
  private void hideProgress(){
    progressbar.hideProgress();
    binding.progressDownloadclient.setVisibility(View.GONE);
  }

  private boolean validateInputs(){
     nameOrder = binding.txtOrderName.getText().toString();
     descrptionOrder = binding.txtOrderDescription.getText().toString();
    if(nameOrder.isEmpty()){
      Tools.showSnackMessage(binding.CoordinatorLayoutContainerCreateOrder,getString(R.string.msg_name_order_required));
      return false;
    }else  if(descrptionOrder.isEmpty()){
      Tools.showSnackMessage(binding.CoordinatorLayoutContainerCreateOrder,getString(R.string.msg_description_order_required));
      return false;
    }

    return true;
  }


  public void createSelectedDialog(View view){
    AlertDialog.Builder b = new Builder(this);
    b.setTitle(getString(R.string.lbl_select_image_title));
    String[] types = {getString(R.string.lbl_select_product_type_woman),getString(R.string.lbl_select_product_type_man),getString(R.string.lbl_select_product_type_child),getString(R.string.lbl_select_product_type_girl)};
    b.setItems(types, new OnClickListener() {

      @Override
      public void onClick(DialogInterface dialog, int which) {

        dialog.dismiss();
        switch(which){
          case 0:
            typeSelected = getString(R.string.lbl_select_product_type_woman);
            binding.txtOrderGender.setText(typeSelected);
            break;
          case 1:
            typeSelected = getString(R.string.lbl_select_product_type_man);
            binding.txtOrderGender.setText(typeSelected);
            break;
          case 2:
            typeSelected = getString(R.string.lbl_select_product_type_child);
            binding.txtOrderGender.setText(typeSelected);
            break;
          case 3:
            typeSelected = getString(R.string.lbl_select_product_type_girl);
            binding.txtOrderGender.setText(typeSelected);
            break;
          default:
            typeSelected = getString(R.string.lbl_select_product_type_empty);
            binding.txtOrderGender.setText(typeSelected);
        }
      }

    });

    b.show();
  }

  public void createSelectedSizeDialog(View view){
    AlertDialog.Builder b = new Builder(this);
    b.setTitle(getString(R.string.lbl_select_image_title));
    String[] types = {getString(R.string.lbl_select_product_size_ch),getString(R.string.lbl_select_product_size_me),getString(R.string.lbl_select_product_size_gra),getString(R.string.lbl_select_product_size_ex)};
    b.setItems(types, new OnClickListener() {

      @Override
      public void onClick(DialogInterface dialog, int which) {

        dialog.dismiss();
        switch(which){
          case 0:
            sizeSelected = getString(R.string.lbl_select_product_size_ch);
            binding.txtOrderSize.setText(sizeSelected);
            break;
          case 1:
            sizeSelected = getString(R.string.lbl_select_product_size_me);
            binding.txtOrderSize.setText(sizeSelected);
            break;
          case 2:
            sizeSelected = getString(R.string.lbl_select_product_size_gra);
            binding.txtOrderSize.setText(sizeSelected);
            break;
          case 3:
            sizeSelected = getString(R.string.lbl_select_product_size_ex);
            binding.txtOrderSize.setText(sizeSelected);
            break;
          default:
            sizeSelected = getString(R.string.lbl_select_product_type_empty);
            binding.txtOrderSize.setText(sizeSelected);
        }
      }

    });

    b.show();
  }
  public void goProfileActivity(View v){
    ScreenManager.goProfileActivity(this);
  }
}
