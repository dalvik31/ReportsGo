package com.epacheco.reports.Pojo.Order;

import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;
import java.util.HashMap;
import java.util.List;

public class OrderList {
  private String dateOrder;
  private String nameOrder;
  private HashMap<String,OrderDetail> orderLists;

  public String getDateOrder() {
    return dateOrder;
  }

  public void setDateOrder(String dateOrder) {
    this.dateOrder = dateOrder;
  }

  public String getNameOrder() {
    return nameOrder;
  }

  public void setNameOrder(String nameOrder) {
    this.nameOrder = nameOrder;
  }

  public HashMap<String, OrderDetail> getOrderLists() {
    return orderLists;
  }
}
