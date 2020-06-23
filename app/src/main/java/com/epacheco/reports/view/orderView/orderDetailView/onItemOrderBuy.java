package com.epacheco.reports.view.orderView.orderDetailView;

import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;

public interface onItemOrderBuy {
  void onItemOrderClic( String orderId,String orderItemId, OrderDetail orderDetail);
}
