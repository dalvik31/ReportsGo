package com.epacheco.reports.View.OrderView;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.epacheco.reports.Model.OrderModel.OrderModelClass;
import com.epacheco.reports.Model.OrderModel.OrderModelInterface;
import com.epacheco.reports.Pojo.Order.OrderList;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.ReportsDialogGlobal;
import com.epacheco.reports.Tools.Tools;
import java.util.List;

public class OrderViewClass extends AppCompatActivity implements OrderModelInterface {

  private OrderModelClass orderModelClass;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_order_view_class);
    inicializateElements();
  }

  private void inicializateElements() {
    orderModelClass = new OrderModelClass(this);
    orderModelClass.getOrders();
  }

  @Override
  public FragmentActivity getMyActivity() {
    return this;
  }

  @Override
  public void successGetOrderList(List<OrderList> orderLists) {
    Log.e("lista","lista size: "+orderLists.size());
  }

  @Override
  public void errorGetOrderList(String error) {
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
  public void getOrders() {

  }

  @Override
  public void createOrder(OrderList orderList) {

  }

  public void createListOrder(View view){
    ReportsDialogGlobal.showInputDialog(this, "DALE UN NOMBRE A TU LISTA",
        new DialogInterface() {
          @Override
          public void OnClickListener(String nameList) {
            createList(nameList);
          }
        });
  }

  private void createList(String nameList){
    if(nameList!=null && !nameList.isEmpty()){
      OrderList orderList= new OrderList();
      orderList.setNameOrder(nameList);
      orderList.setDateOrder(String.valueOf(System.currentTimeMillis()));
      orderModelClass.createOrder(orderList);
    }else{
      Tools.showToasMessage(this,getString(R.string.msg_name_list_required));
    }
  }
}
