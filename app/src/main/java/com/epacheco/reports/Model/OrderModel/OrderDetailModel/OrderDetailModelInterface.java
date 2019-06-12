package com.epacheco.reports.Model.OrderModel.OrderDetailModel;

import android.support.v4.app.FragmentActivity;
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


  //Methods controller
  void getDetailOrderList(String orderIdList);
  void removeOrderDetail(String orderIdList,String orderItemId);
  void orderItemBuy(String orderIdList,String orderItemId,OrderDetail orderDetail);
}
