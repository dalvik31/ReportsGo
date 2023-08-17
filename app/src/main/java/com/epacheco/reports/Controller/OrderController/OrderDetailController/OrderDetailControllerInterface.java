package com.epacheco.reports.Controller.OrderController.OrderDetailController;

import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;

public interface OrderDetailControllerInterface {
  void getDetailOrderList(String orderIdList);
  void moveOrder(String idOrder ,OrderDetail orderDetail);
  void removeOrderDetail(String orderIdList,String orderItemId);
  void orderItemBuy(String orderIdList,String orderItemId,OrderDetail orderDetail);
  void saveLocationOrder(OrderDetail orderDetail);
}
