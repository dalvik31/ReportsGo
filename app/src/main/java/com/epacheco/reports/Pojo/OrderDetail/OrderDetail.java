package com.epacheco.reports.Pojo.OrderDetail;

import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.Product.Product;

public class OrderDetail {

  private String orderListId;
  private String orderId;
  private String orderName;
  private String orderSize;
  private String orderColor;
  private String orderGender;
  private String orderDescription;
  private Client orderClient;
  private Product orderProduct;
  private boolean orderBuy;

  public String getOrderListId() {
    return orderListId;
  }

  public void setOrderListId(String orderListId) {
    this.orderListId = orderListId;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getOrderName() {
    return orderName;
  }

  public void setOrderName(String orderName) {
    this.orderName = orderName;
  }

  public String getOrderSize() {
    return orderSize;
  }

  public void setOrderSize(String orderSize) {
    this.orderSize = orderSize;
  }

  public String getOrderColor() {
    return orderColor;
  }

  public void setOrderColor(String orderColor) {
    this.orderColor = orderColor;
  }

  public String getOrderGender() {
    return orderGender;
  }

  public void setOrderGender(String orderGender) {
    this.orderGender = orderGender;
  }

  public String getOrderDescription() {
    return orderDescription;
  }

  public void setOrderDescription(String orderDescription) {
    this.orderDescription = orderDescription;
  }

  public Client getOrderClient() {
    return orderClient;
  }

  public void setOrderClient(Client orderClient) {
    this.orderClient = orderClient;
  }

  public void setOrderProduct(Product orderProduct){
    this.orderProduct = orderProduct;

  }


  public boolean isOrderBuy() {
    return orderBuy;
  }

  public void setOrderBuy(boolean orderBuy) {
    this.orderBuy = orderBuy;
  }
}
