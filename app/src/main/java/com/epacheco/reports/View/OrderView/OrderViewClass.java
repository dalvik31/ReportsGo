package com.epacheco.reports.View.OrderView;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import com.bumptech.glide.Glide;
import com.epacheco.reports.Model.OrderModel.OrderModelClass;
import com.epacheco.reports.Model.OrderModel.OrderModelInterface;
import com.epacheco.reports.Pojo.Order.OrderList;
import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.ReportsApplication;
import com.epacheco.reports.Tools.ReportsDialogGlobal;
import com.epacheco.reports.Tools.ReportsProgressDialog;
import com.epacheco.reports.Tools.ScreenManager;
import com.epacheco.reports.Tools.Tools;
import com.epacheco.reports.View.ProductsView.ProductsView.AdapterProducts;
import com.epacheco.reports.databinding.ActivityOrderViewClassBinding;
import com.google.firebase.auth.FirebaseAuth;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class OrderViewClass extends AppCompatActivity implements OrderViewIterface, onItemOrderClic{

  private final String TAG = OrderViewClass.class.getSimpleName();
  private OrderModelClass orderModelClass;
  private SimpleDateFormat formatter;
  private ActivityOrderViewClassBinding binding;
  private ReportsProgressDialog progressbar;
  private FirebaseAuth mAuth;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this,R.layout.activity_order_view_class);
    inicializateElements();
  }

  private void inicializateElements() {
    mAuth = FirebaseAuth.getInstance();
    progressbar = ReportsProgressDialog.getInstance(this);
    orderModelClass = new OrderModelClass(this);
    showProgress(getString(R.string.msg_search_orders));
    orderModelClass.getOrders();
  }

  @Override
  public FragmentActivity getMyActivity() {
    return this;
  }

  @Override
  public void successGetOrderList(List<OrderList> orderLists) {
    hideProgress();
    if(orderLists.size()>0){
      binding.lblZeroOrders.setVisibility(View.GONE);
      binding.recyclerListOrder.setVisibility(View.VISIBLE);
      binding.recyclerListOrder.setHasFixedSize(true);
      binding.recyclerListOrder.setLayoutManager(new LinearLayoutManager(this));
      AdapterOrders adapterOrders = new AdapterOrders(orderLists);
      adapterOrders.setOnItemOrderClic(this);
      binding.recyclerListOrder.setAdapter(adapterOrders);
    }else{
      binding.lblZeroOrders.setVisibility(View.VISIBLE);
      binding.recyclerListOrder.setVisibility(View.GONE);
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    if(mAuth.getCurrentUser()!=null ){
      if( mAuth.getCurrentUser().getPhotoUrl()!=null){
        Glide.with(ReportsApplication.getMyApplicationContext()).load(Tools.getFormatUrlImage(mAuth.getCurrentUser().getPhotoUrl())).into(binding.imgProfile);
      }
    }
  }
  @Override
  public void errorGetOrderList(String error) {
    hideProgress();
    binding.recyclerListOrder.setAdapter(null);
    binding.recyclerListOrder.removeAllViews();
    binding.lblZeroOrders.setVisibility(View.VISIBLE);
    Tools.showToasMessage(this,error);
  }

  @Override
  public void successCreateOrderList() {
    Tools.showToasMessage(this,getString(R.string.msg_success_list_order));
  }

  @Override
  public void errorCreateOrderList(String error) {
    Tools.showToasMessage(this,error);
  }

  @Override
  public void successRemoveOrderList() {
    Tools.showSnackMessage(binding.CoordinatorLayoutContainerOrders,getString(R.string.msg_item_removed));
  }

  @Override
  public void errorRemoveOrderList(String error) {
    Tools.showToasMessage(this,error);
  }

  public void createListOrder(View view){
    Calendar currentDate = Calendar.getInstance();
    String dateFormat = getFormatter().format(currentDate.getTime());
    OrderList orderList= new OrderList();
    orderList.setNameOrder(dateFormat);
    orderList.setDateOrder(String.valueOf(System.currentTimeMillis()));
    orderModelClass.createOrder(orderList);
  }



  public SimpleDateFormat getFormatter() {
    if(formatter==null){
      formatter = new SimpleDateFormat("EEEE dd / MMMM / yyyy", Locale.getDefault());
    }
    return formatter;
  }

  private void showProgress(String message){
    progressbar.showProgress(this,message);
    binding.progressDownloadOrders.setVisibility(View.VISIBLE);
  }
  private void hideProgress(){
    progressbar.hideProgress();
    binding.progressDownloadOrders.setVisibility(View.GONE);
  }

  @Override
  public void onItemOrderClic(boolean removeElement,String orderId,String nameOrder) {
    if(removeElement)
    orderModelClass.removeOrderList(orderId);
    else
      ScreenManager.goOrderDetailActivity(this,orderId,nameOrder);
  }

  public void goProfileActivity(View v){
    ScreenManager.goProfileActivity(this);
  }
}
