package com.epacheco.reports.Model.ClientModel.ClientDetailListModel;

import android.support.v4.app.FragmentActivity;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;
import java.util.List;

public interface ClientDetailListModelInterface {
  //View methods
  FragmentActivity getMyActivity();
  void successGetDetailClientList(List<ClientDetail> clientDetails);
  void errorGetDetailClientList(String error);

  void successGetClient(Client client);
  void errorGetClient(String error);

  //Controller methods
  void getClientDetailList(String id);
  void getClient(String id);
}
