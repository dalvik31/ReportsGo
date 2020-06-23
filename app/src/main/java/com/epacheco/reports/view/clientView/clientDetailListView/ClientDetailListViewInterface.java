package com.epacheco.reports.view.clientView.clientDetailListView;

import androidx.fragment.app.FragmentActivity;
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
