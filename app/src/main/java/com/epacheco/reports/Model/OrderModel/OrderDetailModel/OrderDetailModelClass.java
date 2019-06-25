package com.epacheco.reports.Model.OrderModel.OrderDetailModel;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Controller.OrderController.OrderDetailController.OrderDetailControllerClass;
import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;
import com.epacheco.reports.View.OrderView.OrderDetailView.OrderDetailView;
import java.util.List;

public class OrderDetailModelClass implements OrderDetailModelInterface {

  private OrderDetailView orderDetailView;
  private OrderDetailControllerClass orderDetailControllerClass;

  public OrderDetailModelClass(OrderDetailView orderDetailView) {
    this.orderDetailView = orderDetailView;
    this.orderDetailControllerClass = new OrderDetailControllerClass(this);
  }

  @Override
  public FragmentActivity getMyActivity() {
    return orderDetailView.getMyActivity();
  }

  @Override
  public void successGetListDetail(List<OrderDetail> orderDetailList) {
    if(orderDetailView!= null){
      orderDetailView.successGetListDetail(orderDetailList);
    }
  }

  @Override
  public void errorGetListDetail(String error) {
    if(orderDetailView!= null){
      orderDetailView.errorGetListDetail(error);
    }
  }

  @Override
  public void successremoveOrderDetail() {
    if(orderDetailView!= null){
      orderDetailView.successremoveOrderDetail();
    }
  }

  @Override
  public void errorremoveOrderDetail(String error) {
    if(orderDetailView!= null){
      orderDetailView.errorremoveOrderDetail(error);
    }
  }

  @Override
  public void successOrderBuyElement() {
    if(orderDetailView!= null){
      orderDetailView.successOrderBuyElement();
    }
  }

  @Override
  public void errorOrderBuyElement(String error) {
    if(orderDetailView!= null){
      orderDetailView.errorOrderBuyElement(error);
    }
  }

  @Override
  public void getDetailOrderList(String orderIdList) {
    if(orderDetailControllerClass!=null){
      orderDetailControllerClass.getDetailOrderList(orderIdList);
    }
  }

  @Override
  public void removeOrderDetail(String orderIdList,String orderItemId) {
    if(orderDetailControllerClass!=null){
      orderDetailControllerClass.removeOrderDetail(orderIdList,orderItemId);
    }
  }

  @Override
  public void orderItemBuy(String orderIdList, String orderItemId, OrderDetail orderDetail) {
    if(orderDetailControllerClass!=null){
      orderDetailControllerClass.orderItemBuy(orderIdList,orderItemId,orderDetail);
    }
  }
}
