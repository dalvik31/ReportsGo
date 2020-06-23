package com.epacheco.reports.Model.ClientModel.ClientAddModel;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Pojo.Client.Client;

public interface ClientAddModelInterface {
  //View methods
  FragmentActivity getMyActivity();
  void succesAddClient();
  void errorAddClient(String error);
  void successGetClient(Client client);
  void errorGetClient(String error);

  //Controller methods
  void addClient(Client newClient);
  void getClient(String idClient);
  void modifyClient(Client client);
  void removeClient(String idClient);

  void succesRemoveClient();
  void errorRemoveClient(String error);

  void successModifyClient();
  void errorModifyClient(String error);
}
