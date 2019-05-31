package com.epacheco.reports.Controller.ClientController.ClientAddController;

import android.os.Bundle;
import com.epacheco.reports.Pojo.Client.Client;

public interface ClientAddControllerInterface {
  void addClient(Client newClient);
  void getClient(String idClient);
  void modifyClient(Client client);
  void removeClient(String idClient);

}
