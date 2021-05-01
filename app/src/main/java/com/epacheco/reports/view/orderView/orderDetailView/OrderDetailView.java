package com.epacheco.reports.view.orderView.orderDetailView;

import android.content.DialogInterface;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.epacheco.reports.Model.OrderModel.OrderDetailModel.OrderDetailModelClass;
import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.ReportsApplication;
import com.epacheco.reports.Tools.ReportsDialogGlobal;
import com.epacheco.reports.Tools.ReportsProgressDialog;
import com.epacheco.reports.Tools.ScreenManager;
import com.epacheco.reports.databinding.ActivityOrderDetailViewBinding;
import com.google.firebase.auth.FirebaseAuth;
import java.util.List;

public class OrderDetailView extends AppCompatActivity implements OrderDetailInterface, onItemOrderDetailClic,onItemOrderBuy{

  private final String TAG = OrderDetailView.class.getSimpleName();
  public static final String ORDER_ID = "orderId";
  public static final String ORDER_NAME= "orderName";
  public static final String CLIENT_ID= "clientId";
  public static final String PRODUCT_ID= "productId";




  private ActivityOrderDetailViewBinding binding;
  private FirebaseAuth mAuth;
  private String listOrderId;
  private ReportsProgressDialog progressbar;
  private OrderDetailModelClass orderDetailModelClass;
  private String clientId;
  private String productId;
  private Bundle extras;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(OrderDetailView.this,R.layout.activity_order_detail_view);
    initElements();
  }

  private void initElements() {
    mAuth = FirebaseAuth.getInstance();
    progressbar = ReportsProgressDialog.getInstance();
    orderDetailModelClass = new OrderDetailModelClass(this);
    extras = getIntent().getExtras();
    if(extras!=null){
      binding.appBar.setTitle(extras.getString(ORDER_NAME));
      listOrderId = extras.getString(ORDER_ID);
      showProgress(getString(R.string.msg_search_orders_details));
      orderDetailModelClass.getDetailOrderList(listOrderId);
    }

  }


  @Override
  public void onStart() {
    super.onStart();
    if(mAuth.getCurrentUser()!=null ){
      if( mAuth.getCurrentUser().getPhotoUrl()!=null){
        Glide.with(ReportsApplication.getMyApplicationContext()).load(
            com.epacheco.reports.Tools.Tools.getFormatUrlImage(mAuth.getCurrentUser().getPhotoUrl())) .apply(RequestOptions.circleCropTransform()).into(binding.imgProfile);
      }
    }
  }

  public void goCreateOrder(View view){
    ScreenManager.goOrderCreateActivity(this,listOrderId,null);
  }

  @Override
  public FragmentActivity getMyActivity() {
    return this;
  }

  @Override
  public void successGetListDetail(List<OrderDetail> orderDetailList) {
    hideProgress();
    if(orderDetailList.size()>0){
      binding.lblZeroOrdersElements.setVisibility(View.GONE);
      binding.recyclerListOrderElelments.setVisibility(View.VISIBLE);
      binding.recyclerListOrderElelments.setHasFixedSize(true);
      binding.recyclerListOrderElelments.setLayoutManager(new LinearLayoutManager(this));
      AdapterOrdersDetail adapterOrders = new AdapterOrdersDetail(orderDetailList);
      adapterOrders.setOnItemOrderDetailClic(this);
      adapterOrders.setOnItemOrderBuy(this);
      binding.recyclerListOrderElelments.setAdapter(adapterOrders);
    }else{
      binding.lblZeroOrdersElements.setVisibility(View.VISIBLE);
      binding.recyclerListOrderElelments.setVisibility(View.GONE);
    }
    checkIdClient();
  }

  @Override
  public void errorGetListDetail(String error) {
    hideProgress();
    binding.recyclerListOrderElelments.removeAllViews();
    binding.lblZeroOrdersElements.setVisibility(View.VISIBLE);
    checkIdClient();
  }

  @Override
  public void successOrderBuyElement() {
    hideProgress();
    com.epacheco.reports.Tools.Tools.showToasMessage(this,getString(R.string.lbl_order_buy_update));
  }

  @Override
  public void errorOrderBuyElement(String error) {
    hideProgress();
    com.epacheco.reports.Tools.Tools.showToasMessage(this,error);
  }

  @Override
  public void successremoveOrderDetail() {
    hideProgress();
    com.epacheco.reports.Tools.Tools.showToasMessage(this,getString(R.string.msg_item_order_removed));
  }

  @Override
  public void errorremoveOrderDetail(String error) {
    hideProgress();
    com.epacheco.reports.Tools.Tools.showToasMessage(this,error);
  }

  private void showProgress(String message){
    progressbar.showProgress(this,message);
    binding.progressDownloadOrderElements.setVisibility(View.VISIBLE);
  }
  private void hideProgress(){
    progressbar.hideProgress();
    binding.progressDownloadOrderElements.setVisibility(View.GONE);
  }

  @Override
  public void onItemOrderClic(boolean removeElement, final String orderId, final String orderItemId) {
    if(removeElement){
      ReportsDialogGlobal.showDialogAccept(this, getString(R.string.title_message_delete_elemnt),
          getString(R.string.body_message_delete_elemnt),
          new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              orderDetailModelClass.removeOrderDetail(orderId,orderItemId);
            }
          }
      );

    }
  }

  @Override
  public void onItemOrderClic( String orderId,String orderItemId,OrderDetail orderDetail) {
    orderDetailModelClass.orderItemBuy(orderId,orderItemId,orderDetail);
  }
  public void goProfileActivity(View v){
    ScreenManager.goProfileActivity(this);
  }


  private void checkIdClient(){
    if(extras!=null && extras.containsKey(CLIENT_ID)){
      clientId = extras.getString(CLIENT_ID);
      Log.e(TAG,"clientId: "+clientId);
      ScreenManager.goOrderCreateActivity(this,listOrderId,clientId);
      extras.remove(CLIENT_ID);
    }else if(extras!=null && extras.containsKey(PRODUCT_ID)){
      productId = extras.getString(PRODUCT_ID);
      Log.e(TAG,"productId :  "+productId);
      ScreenManager.goOrderCreateActivityProduct(this,listOrderId,productId);
      extras.remove(PRODUCT_ID);
    }else
      {
      Log.e(TAG,"extra nulo");
    }
  }
}
