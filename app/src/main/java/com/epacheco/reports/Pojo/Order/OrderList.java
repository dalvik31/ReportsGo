package com.epacheco.reports.Pojo.Order;

import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;
import java.util.HashMap;
import java.util.List;

public class OrderList {
  private String dateOrder;
  private String nameOrder;
  private String msjOrder;


  private HashMap<String,Object> orderLists;

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

  public String getMsjOrder() {
    return msjOrder;
  }

  public void setMsjOrder(String msjOrder) {
    this.msjOrder = msjOrder;
  }
  public HashMap<String, Object> getOrderLists() {
    return orderLists;
  }

  public void setOrderLists(HashMap<String, Object> orderLists) {
    this.orderLists = orderLists;
  }
}
