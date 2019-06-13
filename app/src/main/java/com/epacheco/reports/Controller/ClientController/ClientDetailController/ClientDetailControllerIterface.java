package com.epacheco.reports.Controller.ClientController.ClientDetailController;

import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;

public interface ClientDetailControllerIterface {
  void getClient(String id);
  void addClientDetail(ClientDetail clientDetail,String id);
  void getClientDetail(String id);
}
