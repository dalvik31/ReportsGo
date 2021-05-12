package com.epacheco.reports.view.orderView;

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
import com.epacheco.reports.Model.OrderModel.OrderModelClass;
import com.epacheco.reports.Pojo.Order.OrderList;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.ReportsApplication;
import com.epacheco.reports.Tools.ReportsDialogGlobal;
import com.epacheco.reports.Tools.ReportsProgressDialog;
import com.epacheco.reports.Tools.ScreenManager;
import com.epacheco.reports.view.clientView.clientAddView.ClientAddViewClass;
import com.epacheco.reports.databinding.ActivityOrderViewClassBinding;
import com.epacheco.reports.view.productsView.productAddView.ProductAddViewClass;
import com.google.firebase.auth.FirebaseAuth;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class OrderViewClass extends AppCompatActivity implements OrderViewIterface, onItemOrderClic{

  private final String TAG = OrderViewClass.class.getSimpleName();
  public static final String ORDERLIST = "ORDER_LIST";
  private OrderModelClass orderModelClass;
  private SimpleDateFormat formatter;
  private ActivityOrderViewClassBinding binding;
  private ReportsProgressDialog progressbar;
  private FirebaseAuth mAuth;
  private String idClient;
  private String idProduct;
  private boolean idListSelected;
  ArrayList<OrderList> orderList1;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this,R.layout.activity_order_view_class);
    inicializateElements();
  }

  private void inicializateElements() {
    mAuth = FirebaseAuth.getInstance();
    progressbar = ReportsProgressDialog.getInstance();
    orderModelClass = new OrderModelClass(this);
    showProgress(getString(R.string.msg_search_orders));
    orderModelClass.getOrders();

    Bundle extras = getIntent().getExtras();
    if(extras!=null && extras.containsKey(ClientAddViewClass.CLIENT_ID)){
      idClient = extras.getString(ClientAddViewClass.CLIENT_ID);
      Log.e(TAG,"idClient: "+idClient);
      selectListOrder1(getString(R.string.body_message_select_order2));
    }else
   if (extras != null && extras.containsKey(ProductAddViewClass.PRODUCT_ID)){
     idProduct = extras.getString(ProductAddViewClass.PRODUCT_ID);
     Log.e(TAG,"idProduct : "+idProduct);
     selectListOrder1(getString(R.string.body_message_select_order2));
    }
  }



  private void selectListOrder1(String string) {
    ReportsDialogGlobal.showDialogAcceptAnCancel(this, getString(R.string.title_message_select_order),
            string,
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                idListSelected = true;
              }
            },
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                finish();
              }
            }
    );
  }

  
  @Override
  public FragmentActivity getMyActivity() {
    return this;
  }

  @Override
  public void successGetOrderList(List<OrderList> orderLists) {
    hideProgress();


    if(orderLists.size()>0){
      orderList1 = (ArrayList<OrderList>) orderLists;
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
        Glide.with(ReportsApplication.getMyApplicationContext()).load(com.epacheco.reports.Tools.Tools.getFormatUrlImage(mAuth.getCurrentUser().getPhotoUrl()))  .apply(
            RequestOptions.circleCropTransform()).into(binding.imgProfile);
      }
    }
  }
  @Override
  public void errorGetOrderList(String error) {
    hideProgress();
    binding.recyclerListOrder.setAdapter(null);
    binding.recyclerListOrder.removeAllViews();
    binding.lblZeroOrders.setVisibility(View.VISIBLE);
    com.epacheco.reports.Tools.Tools.showToasMessage(this,error);
  }

  @Override
  public void successCreateOrderList() {
    com.epacheco.reports.Tools.Tools.showToasMessage(this,getString(R.string.msg_success_list_order));
  }

  @Override
  public void errorCreateOrderList(String error) {
    com.epacheco.reports.Tools.Tools.showToasMessage(this,error);
  }

  @Override
  public void successRemoveOrderList() {
    com.epacheco.reports.Tools.Tools.showSnackMessage(binding.CoordinatorLayoutContainerOrders,getString(R.string.msg_item_removed));
  }

  @Override
  public void errorRemoveOrderList(String error) {
    com.epacheco.reports.Tools.Tools.showToasMessage(this,error);
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
  public void onItemOrderClic(boolean removeElement, final String orderId,String nameOrder) {
    if(idListSelected && idClient != null){
      ScreenManager.goOrderDetailActivity(this,orderId,nameOrder,idClient,null);
      finish();
      idListSelected= false;

    }else if(idListSelected && idProduct != null){
      ScreenManager.goOrderDetailActivityProduct(this,orderId,nameOrder,idProduct);
      finish();
      idListSelected= false;
    }
    else {
      if(removeElement){
        ReportsDialogGlobal.showDialogAccept(this, getString(R.string.title_message_delete_elemnt),
            getString(R.string.body_message_delete_elemnt),
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                orderModelClass.removeOrderList(orderId);
              }
            }
        );
      }
      else{
        if (orderList1 != null ){
          ScreenManager.goOrderDetailActivity(this,orderId,nameOrder,null,orderList1);
        }

      }
    }

  }

  public void goProfileActivity(View v){
    ScreenManager.goProfileActivity(this);
  }
}
