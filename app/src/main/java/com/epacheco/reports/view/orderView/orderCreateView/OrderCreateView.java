package com.epacheco.reports.view.orderView.orderCreateView;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.epacheco.reports.Model.OrderModel.CreateOrderModel.OrderCreateModelClass;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.ReportsApplication;
import com.epacheco.reports.Tools.ReportsProgressDialog;
import com.epacheco.reports.Tools.ScreenManager;
import com.epacheco.reports.view.clientView.clientAddView.ClientAddViewClass;
import com.epacheco.reports.view.clientView.clientView.ClientsViewClass;
import com.epacheco.reports.view.orderView.orderDetailView.OrderDetailView;
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
  private boolean sizeNumeric;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this,R.layout.activity_order_create_view);
    initializateElements();
  }

  private void initializateElements() {
    mAuth = FirebaseAuth.getInstance();
    progressbar = ReportsProgressDialog.getInstance();
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
    binding.AppCompatCheckBoxNumeric.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        sizeNumeric = isChecked;
      }
    });


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
    com.epacheco.reports.Tools.Tools.showToasMessage(this,"Pedido creado");
    finish();
  }

  @Override
  public void errorCreateOrder(String error) {
    com.epacheco.reports.Tools.Tools.showToasMessage(this,error);
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
            com.epacheco.reports.Tools.Tools.getFormatUrlImage(mAuth.getCurrentUser().getPhotoUrl()))  .apply(RequestOptions.circleCropTransform()).into(binding.imgProfile);
      }
    }
  }

  @Override
  public void errorGetClient(String error) {
    com.epacheco.reports.Tools.Tools.showToasMessage(this,error);
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
      com.epacheco.reports.Tools.Tools.showSnackMessage(binding.CoordinatorLayoutContainerCreateOrder,getString(R.string.msg_name_order_required));
      return false;
    }else  if(descrptionOrder.isEmpty()){
      com.epacheco.reports.Tools.Tools.showSnackMessage(binding.CoordinatorLayoutContainerCreateOrder,getString(R.string.msg_description_order_required));
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
    if(sizeNumeric){
      AlertDialog.Builder b = new Builder(this);
      b.setTitle(getString(R.string.lbl_select_image_title));
      String[] types = {
          getString(R.string.lbl_select_product_size_2),
          getString(R.string.lbl_select_product_size_4),
          getString(R.string.lbl_select_product_size_6),
          getString(R.string.lbl_select_product_size_8),
          getString(R.string.lbl_select_product_size_10),
          getString(R.string.lbl_select_product_size_12),
          getString(R.string.lbl_select_product_size_14),
          getString(R.string.lbl_select_product_size_16),
          getString(R.string.lbl_select_product_size_28),
          getString(R.string.lbl_select_product_size_30),
          getString(R.string.lbl_select_product_size_32),
          getString(R.string.lbl_select_product_size_34),
          getString(R.string.lbl_select_product_size_36),
          getString(R.string.lbl_select_product_size_38),
          getString(R.string.lbl_select_product_size_40),
          getString(R.string.lbl_select_product_size_42),
          getString(R.string.lbl_select_product_size_44),
      };
      b.setItems(types, new OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {

          dialog.dismiss();
          switch(which){
            case 0:
              sizeSelected = getString(R.string.lbl_select_product_size_2);
              binding.txtOrderSize.setText(sizeSelected);
              break;
            case 1:
              sizeSelected = getString(R.string.lbl_select_product_size_4);
              binding.txtOrderSize.setText(sizeSelected);
              break;
            case 2:
              sizeSelected = getString(R.string.lbl_select_product_size_6);
              binding.txtOrderSize.setText(sizeSelected);
              break;
            case 3:
              sizeSelected = getString(R.string.lbl_select_product_size_8);
              binding.txtOrderSize.setText(sizeSelected);
              break;
            case 4:
              sizeSelected = getString(R.string.lbl_select_product_size_10);
              binding.txtOrderSize.setText(sizeSelected);
              break;
            case 5:
              sizeSelected = getString(R.string.lbl_select_product_size_12);
              binding.txtOrderSize.setText(sizeSelected);
              break;
            case 6:
              sizeSelected = getString(R.string.lbl_select_product_size_14);
              binding.txtOrderSize.setText(sizeSelected);
              break;
            case 7:
              sizeSelected = getString(R.string.lbl_select_product_size_16);
              binding.txtOrderSize.setText(sizeSelected);
              break;
            case 8:
              sizeSelected = getString(R.string.lbl_select_product_size_28);
              binding.txtOrderSize.setText(sizeSelected);
              break;
            case 9:
              sizeSelected = getString(R.string.lbl_select_product_size_30);
              binding.txtOrderSize.setText(sizeSelected);
              break;
            case 10:
              sizeSelected = getString(R.string.lbl_select_product_size_32);
              binding.txtOrderSize.setText(sizeSelected);
              break;
            case 11:
              sizeSelected = getString(R.string.lbl_select_product_size_34);
              binding.txtOrderSize.setText(sizeSelected);
              break;
            case 12:
              sizeSelected = getString(R.string.lbl_select_product_size_36);
              binding.txtOrderSize.setText(sizeSelected);
              break;
            case 13:
              sizeSelected = getString(R.string.lbl_select_product_size_38);
              binding.txtOrderSize.setText(sizeSelected);
              break;
            case 14:
              sizeSelected = getString(R.string.lbl_select_product_size_40);
              binding.txtOrderSize.setText(sizeSelected);
              break;
            case 15:
              sizeSelected = getString(R.string.lbl_select_product_size_42);
              binding.txtOrderSize.setText(sizeSelected);
              break;
            case 16:
              sizeSelected = getString(R.string.lbl_select_product_size_44);
              binding.txtOrderSize.setText(sizeSelected);
              break;
            default:
              sizeSelected = getString(R.string.lbl_select_product_type_empty);
              binding.txtOrderSize.setText(sizeSelected);
          }
        }

      });
      b.show();
    }else{
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

  }
  public void goProfileActivity(View v){
    ScreenManager.goProfileActivity(this);
  }
}
