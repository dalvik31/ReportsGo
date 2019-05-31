package com.epacheco.reports.View.ClientView.ClientView;

import android.support.v4.app.FragmentActivity;
import com.epacheco.reports.Pojo.Client.Client;
import java.util.ArrayList;

public interface ClientViewInterface {

  FragmentActivity getMyActivity();
  void successDownloadClients(ArrayList<Client> listCliets);
  void errorDownloadClients(String error);


}
