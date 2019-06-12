package com.epacheco.reports.Controller.OrderController.OrderCreateController;

import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;

public interface OrderCreateControllerInterface {
  void createNewOrder( OrderDetail orderDetail);
  void getClient(String idClient);
}
