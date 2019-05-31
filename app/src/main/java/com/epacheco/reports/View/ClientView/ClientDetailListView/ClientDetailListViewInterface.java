package com.epacheco.reports.View.ClientView.ClientDetailListView;

import android.support.v4.app.FragmentActivity;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;
import java.util.List;

public interface ClientDetailListViewInterface {
  FragmentActivity getMyActivity();
  void successGetDetailClientList(List<ClientDetail> clientDetail);
  void errorGetDetailClientList(String error);

  void successGetClient(Client client);
  void errorGetClient(String error);
}
