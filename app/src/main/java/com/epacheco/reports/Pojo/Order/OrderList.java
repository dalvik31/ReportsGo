package com.epacheco.reports.Pojo.Order;

import android.os.Parcel;
import android.os.Parcelable;

import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;
import java.util.HashMap;
import java.util.List;

public class OrderList implements Parcelable {
  private String dateOrder;
  private String nameOrder;
  private HashMap<String,Object> orderLists;

  public OrderList(Parcel in) {
    dateOrder = in.readString();
    nameOrder = in.readString();
  }

  public static final Creator<OrderList> CREATOR = new Creator<OrderList>() {
    @Override
    public OrderList createFromParcel(Parcel in) {
      return new OrderList(in);
    }

    @Override
    public OrderList[] newArray(int size) {
      return new OrderList[size];
    }
  };

  public OrderList(String dateOrder, String nameOrder) {

  }

  public OrderList() {

  }

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

  public HashMap<String, Object> getOrderLists() {
    return orderLists;
  }

  public void setOrderLists(HashMap<String, Object> orderLists) {
    this.orderLists = orderLists;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(dateOrder);
    dest.writeString(nameOrder);
  }

  @Override
  public String toString() {
    return  nameOrder;
  }
}
