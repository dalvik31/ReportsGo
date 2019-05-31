package com.epacheco.reports.View.SaleView;

import android.support.v4.app.FragmentActivity;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;
import com.epacheco.reports.Pojo.Product.Product;

public interface SaleViewInterface {
  FragmentActivity getMyActivity();
  void successGetClient(Client client);
  void errrorGetClient(String error);
  void successGetProduct(Product product);
  void errorGetProduct(String error);
  void successAddClientDetail();
  void errorAddClientDetail(String error);
  void successGetClientDetail(ClientDetail clientDetail);
  void errorGetClientDetail(String error);
}
