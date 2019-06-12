package com.epacheco.reports.Model.OrderModel;

import android.support.v4.app.FragmentActivity;
import com.epacheco.reports.Controller.OrderController.OrderControllerClass;
import com.epacheco.reports.Pojo.Order.OrderList;
import com.epacheco.reports.View.OrderView.OrderViewClass;
import java.util.List;

public class OrderModelClass implements OrderModelInterface{

  private OrderViewClass orderViewClass;
  private OrderControllerClass orderControllerClass;

  public OrderModelClass(OrderViewClass orderViewClass) {
    this.orderViewClass = orderViewClass;
    this.orderControllerClass = new OrderControllerClass(this);
  }

  @Override
  public FragmentActivity getMyActivity() {
    return orderViewClass.getMyActivity();
  }

  @Override
  public void successGetOrderList(List<OrderList> orderLists) {
    if(orderViewClass!=null){
      orderViewClass.successGetOrderList(orderLists);
    }
  }

  @Override
  public void errorGetOrderList(String error) {
    if(orderViewClass!=null){
      orderViewClass.errorGetOrderList(error);
    }
  }

  @Override
  public void successCreateOrderList() {
    if(orderViewClass!=null){
      orderViewClass.successCreateOrderList();
    }
  }

  @Override
  public void errorCreateOrderList(String error) {
    if(orderViewClass!=null){
      orderViewClass.errorCreateOrderList(error);
    }
  }

  @Override
  public void successRemoveOrderList() {
    if(orderViewClass!=null){
      orderViewClass.successRemoveOrderList();
    }
  }

  @Override
  public void errorRemoveOrderList(String error) {
    if(orderViewClass!=null){
      orderViewClass.errorRemoveOrderList(error);
    }
  }

  @Override
  public void getOrders() {
    if(orderControllerClass!=null){
      orderControllerClass.getOrders();
    }
  }

  @Override
  public void createOrder(OrderList orderList) {
    if(orderControllerClass!=null){
      orderControllerClass.createOrder(orderList);
    }
  }

  @Override
  public void removeOrderList(String orderId) {
    if(orderControllerClass!=null){
      orderControllerClass.removeOrderList(orderId);
    }
  }
}
