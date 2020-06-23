package com.epacheco.reports.Model.SaleModel;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;
import com.epacheco.reports.Pojo.Product.Product;
import java.util.List;

public interface SaleModelInterface {
  //View methods
  FragmentActivity getMyActivity();
  void successGetClient(Client client);
  void errrorGetClient(String error);
  void successGetProduct(Product product);
  void errorGetProduct(String error);
  void successAddClientDetail();
  void errorAddClientDetail(String error);
  void successGetClientDetail(ClientDetail clientDetail);
  void errorGetClientDetail(String error);

  //Controller methods
  void getCLient(String clientId);
  void getProduct(String productId);
  void addClientDetail( List<ClientDetail>  clientDetail,String idClient);
  void getClientDetail(String id);
}
