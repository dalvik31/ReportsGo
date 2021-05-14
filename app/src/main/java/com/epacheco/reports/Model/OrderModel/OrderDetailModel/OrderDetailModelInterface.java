package com.epacheco.reports.Model.OrderModel.OrderDetailModel;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;
import java.util.List;

public interface OrderDetailModelInterface {
  //Methods view
  FragmentActivity getMyActivity();
  void successGetListDetail(List<OrderDetail> orderDetailList);
  void errorGetListDetail(String error);

  void successremoveOrderDetail();
  void errorremoveOrderDetail(String error);

  void successOrderBuyElement();
  void errorOrderBuyElement(String error);

  void successMoveOrder();
  void errorMoveOrder(String error);
  void moveOrder(String idOrder ,OrderDetail orderDetail);

  //Methods controller
  void getDetailOrderList(String orderIdList);
  void removeOrderDetail(String orderIdList,String orderItemId);
  void orderItemBuy(String orderIdList,String orderItemId,OrderDetail orderDetail);
}
