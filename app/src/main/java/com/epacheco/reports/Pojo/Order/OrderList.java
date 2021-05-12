package com.epacheco.reports.Pojo.Order;

import android.os.Parcel;
import android.os.Parcelable;

import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;
import java.util.HashMap;
import java.util.List;

public class OrderList implements Parcelable {
  private String dateOrder;
  private String nameOrder;
  private String msjOrder;
  private int imageStationbackground;

  private HashMap<String,OrderDetail> orderLists;

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

  public HashMap<String, OrderDetail> getOrderLists() {
    return orderLists;
  }

  public void setOrderLists(HashMap<String, OrderDetail> orderLists) {
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

  @Override
  public String toString() {
    return  nameOrder;
  }
}
