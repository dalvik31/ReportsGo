package com.epacheco.reports.view.orderView;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Pojo.Order.OrderList;
import java.util.List;

public interface OrderViewIterface {
  FragmentActivity getMyActivity();
  void successGetOrderList(List<OrderList> orderLists);
  void errorGetOrderList(String error);

  void successCreateOrderList();
  void errorCreateOrderList(String error);

  void successRemoveOrderList();
  void errorRemoveOrderList(String error);
}
