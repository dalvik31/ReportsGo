package com.epacheco.reports.Model.ClientModel.ClientDetailModel;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;

public interface ClientDetailModelIterface {
  //View methods
  FragmentActivity getMyActivity();
  void successAddClientDetail();
  void errorAddClientDetail(String error);

  void successGetClientDetail(ClientDetail clientDetail);
  void errorGetClientDetail(String error);

  void successGetClient(Client client);
  void errorGetClient(String error);


  //Controller methods
  void getClient(String id);
  void addClientDetail(ClientDetail clientDetail,String id);
  void getClientDetail(String id);
}
