package com.epacheco.reports.Controller.SaleController;

import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;
import java.util.HashMap;
import java.util.List;

public interface SaleControllerInterface {
  void getCLient(String clientId);
  void getProduct(String productId);
  void addClientDetail(List<ClientDetail>  clientDetail, Client client);
  void getClientDetail(String id);
}
