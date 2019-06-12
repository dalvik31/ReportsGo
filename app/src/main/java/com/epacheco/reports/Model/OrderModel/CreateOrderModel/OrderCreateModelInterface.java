package com.epacheco.reports.Model.OrderModel.CreateOrderModel;

import android.support.v4.app.FragmentActivity;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;

public interface OrderCreateModelInterface {
  //Methods view
  FragmentActivity getMyActivity();
  void successCreateOrder();
  void errorCreateOrder(String error);

  void successGetClient(Client client);
  void errorGetClient(String error);

  //Methods model
  void createNewOrder( OrderDetail orderDetail);
  void getClient(String idClient);
}
