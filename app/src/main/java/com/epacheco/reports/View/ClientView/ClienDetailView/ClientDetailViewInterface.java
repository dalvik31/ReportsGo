package com.epacheco.reports.View.ClientView.ClienDetailView;

import android.support.v4.app.FragmentActivity;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;
import java.util.ArrayList;

public interface ClientDetailViewInterface {
  FragmentActivity getMyActivity();
  void successAddClientDetail();
  void errorAddClientDetail(String error);

  void successGetClientDetail(ClientDetail clientDetail);
  void errorGetClientDetail(String error);

  void successGetClient(Client client);
  void errorGetClient(String error);
}
