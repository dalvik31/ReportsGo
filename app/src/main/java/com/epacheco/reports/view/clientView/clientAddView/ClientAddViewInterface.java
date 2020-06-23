package com.epacheco.reports.view.clientView.clientAddView;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Pojo.Client.Client;

public interface ClientAddViewInterface {
  FragmentActivity getMyActivity();
  void succesAddClient();
  void errorAddClient(String error);

  void successGetClient(Client client);
  void errorGetClient(String error);

  void successModifyClient();
  void errorModifyClient(String error);
  void succesRemoveClient();
  void errorRemoveClient(String error);
}
