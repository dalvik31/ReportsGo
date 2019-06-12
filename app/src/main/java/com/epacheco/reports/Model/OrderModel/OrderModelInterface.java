package com.epacheco.reports.Model.OrderModel;

import android.support.v4.app.FragmentActivity;
import com.epacheco.reports.Pojo.Order.OrderList;
import java.util.List;

public interface OrderModelInterface {
  //View Methods
  FragmentActivity getMyActivity();
  void successGetOrderList(List<OrderList> orderLists);
  void errorGetOrderList(String error);

  void successCreateOrderList();
  void errorCreateOrderList(String error);

  void successRemoveOrderList();
  void errorRemoveOrderList(String error);


  //Controller Methods
  void getOrders();
  void createOrder(OrderList orderList);
  void removeOrderList(String orderId);
}
