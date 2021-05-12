package com.epacheco.reports.Pojo.Order;

import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;
import java.util.HashMap;
import java.util.List;

public class OrderList {
  private String dateOrder;
  private String nameOrder;
  private String msjOrder;
  private int imageStationbackground;

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

  public String getMsjOrder() {
    return msjOrder;
  }

  public void setMsjOrder(String msjOrder) {
    this.msjOrder = msjOrder;
  }

  public int getImageStationbackground() {
    return imageStationbackground;
  }

  public void setImageStationbackground(int imageStationbackground) {
    this.imageStationbackground = imageStationbackground;
  }

  public HashMap<String, OrderDetail> getOrderLists() {
    return orderLists;
  }

}
