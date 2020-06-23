package com.epacheco.reports.view.clientView.clienDetailView;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;

public interface ClientDetailViewInterface {
  FragmentActivity getMyActivity();
  void successAddClientDetail();
  void errorAddClientDetail(String error);

  void successGetClientDetail(ClientDetail clientDetail);
  void errorGetClientDetail(String error);

  void successGetClient(Client client);
  void errorGetClient(String error);
}
