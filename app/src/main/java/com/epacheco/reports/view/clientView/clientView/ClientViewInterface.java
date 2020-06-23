package com.epacheco.reports.view.clientView.clientView;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Pojo.Client.Client;
import java.util.ArrayList;

public interface ClientViewInterface {

  FragmentActivity getMyActivity();
  void successDownloadClients(ArrayList<Client> listCliets);
  void errorDownloadClients(String error);


}
