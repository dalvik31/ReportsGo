package com.epacheco.reports.Model.ClientModel.ClientModel;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Controller.ClientController.ClientController.ClientControllerClass;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.view.clientView.clientView.ClientsViewClass;
import java.util.ArrayList;

public class ClientModelClass implements  ClientModelInterface{

  private ClientsViewClass clientViewClass;
  private ClientControllerClass clientControllerClass;

  public ClientModelClass(ClientsViewClass clientViewClass) {
    this.clientViewClass = clientViewClass;
    this.clientControllerClass = new ClientControllerClass(this);
  }

  @Override
  public void downloadClients(String name) {
    if(clientControllerClass!=null){
      clientControllerClass.downloadClients(name);
    }
  }

  @Override
  public FragmentActivity getMyActivity() {
    if(clientViewClass!=null){
      return clientViewClass.getMyActivity();
    }
    return null;
  }

  @Override
  public void successDownloadClients(ArrayList<Client> listCliets) {
    if(clientViewClass!=null){
      clientViewClass.successDownloadClients(listCliets);
    }
  }

  @Override
  public void errorDownloadClients(String error) {
    if(clientViewClass!=null){
      clientViewClass.errorDownloadClients(error);
    }
  }
}
