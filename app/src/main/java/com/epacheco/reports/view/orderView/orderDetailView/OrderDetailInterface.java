package com.epacheco.reports.view.orderView.orderDetailView;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;
import java.util.List;

public interface OrderDetailInterface {
  FragmentActivity getMyActivity();
  void successGetListDetail(List<OrderDetail> orderDetailList);
  void errorGetListDetail(String error);
  void successOrderBuyElement();
  void errorOrderBuyElement(String error);

  void successremoveOrderDetail();
  void errorremoveOrderDetail(String error);

  void successMoveOrder();
  void errorMoveOrder(String error);

}
