package com.epacheco.reports.Model.OrderModel.CreateOrderModel;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;
import com.epacheco.reports.Pojo.Product.Product;

public interface OrderCreateModelInterface {
  //Methods view
  FragmentActivity getMyActivity();
  void successCreateOrder();
  void errorCreateOrder(String error);

  void successGetClient(Client client);
  void errorGetClient(String error);

  void succesGetProduct(Product product);
  void errorGetProduct(String error);

  //Methods model
  void createNewOrder( OrderDetail orderDetail);
  void getClient(String idClient);
  void getProduct(String idProduct);
}
