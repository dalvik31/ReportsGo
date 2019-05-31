package com.epacheco.reports.Controller.OrderController;

import com.epacheco.reports.Pojo.Order.OrderList;

public interface OrderControllerInterface {
  void getOrders();
  void createOrder(OrderList orderList);
}
