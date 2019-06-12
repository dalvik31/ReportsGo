package com.epacheco.reports.Model.OrderModel.CreateOrderModel;

import android.support.v4.app.FragmentActivity;
import com.epacheco.reports.Controller.OrderController.OrderCreateController.OrderCreateControllerClass;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;
import com.epacheco.reports.View.OrderView.OrderCreateView.OrderCreateView;

public class OrderCreateModelClass implements  com.epacheco.reports.Model.OrderModel.CreateOrderModel.OrderCreateModelInterface {

  private OrderCreateView orderCreateView;
  private OrderCreateControllerClass orderCreateControllerClass;

  public OrderCreateModelClass(OrderCreateView orderCreateView) {
    this.orderCreateView = orderCreateView;
    this.orderCreateControllerClass = new OrderCreateControllerClass(this);
  }

  @Override
  public FragmentActivity getMyActivity() {
    return orderCreateView.getMyActivity();
  }

  @Override
  public void successCreateOrder() {
    if(orderCreateView!=null){
      orderCreateView.successCreateOrder();
    }
  }

  @Override
  public void errorCreateOrder(String error) {
    if(orderCreateView!=null){
      orderCreateView.errorCreateOrder(error);
    }
  }

  @Override
  public void successGetClient(Client client) {
    if(orderCreateView!=null){
      orderCreateView.successGetClient(client);
    }
  }

  @Override
  public void errorGetClient(String error) {
    if(orderCreateView!=null){
      orderCreateView.errorGetClient(error);
    }
  }

  @Override
  public void createNewOrder(OrderDetail orderDetail) {
    if(orderCreateControllerClass!=null){
      orderCreateControllerClass.createNewOrder(orderDetail);
    }
  }

  @Override
  public void getClient(String idClient) {
    if(orderCreateControllerClass!=null){
      orderCreateControllerClass.getClient(idClient);
    }
  }
}
