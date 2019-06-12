package com.epacheco.reports.View.OrderView.OrderDetailView;

import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;

public interface onItemOrderBuy {
  void onItemOrderClic( String orderId,String orderItemId, OrderDetail orderDetail);
}
