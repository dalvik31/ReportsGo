package com.epacheco.reports.Model.ClientModel.ClientModel;

import android.support.v4.app.FragmentActivity;
import com.epacheco.reports.Pojo.Client.Client;
import java.util.ArrayList;

public interface ClientModelInterface {
  void downloadClients(String name);

  FragmentActivity getMyActivity();
  void successDownloadClients(ArrayList<Client> listCliets);
  void errorDownloadClients(String error);
}
