package com.epacheco.reports.Model.ClientModel.ClientAddModel;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Controller.ClientController.ClientAddController.ClienAddControllerClass;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.view.clientView.clientAddView.ClientAddViewClass;

public class ClientAddModelClass implements ClientAddModelInterface {

  private ClientAddViewClass clientAddViewClass;
  private ClienAddControllerClass clienAddControllerClass;

  public ClientAddModelClass(ClientAddViewClass clientAddViewClass) {
    this.clientAddViewClass = clientAddViewClass;
    this.clienAddControllerClass = new ClienAddControllerClass(this);
  }

  @Override
  public FragmentActivity getMyActivity() {
    if(clientAddViewClass!=null){
      return clientAddViewClass.getMyActivity();
    }
    return null;
  }

  @Override
  public void succesAddClient() {
    if(clientAddViewClass!=null){
       clientAddViewClass.succesAddClient();
    }
  }

  @Override
  public void errorAddClient(String error) {
    if(clientAddViewClass!=null){
      clientAddViewClass.errorAddClient(error);
    }
  }

  @Override
  public void successGetClient(Client client) {
    if(clientAddViewClass!=null){
      clientAddViewClass.successGetClient(client);
    }
  }

  @Override
  public void errorGetClient(String error) {
    if(clientAddViewClass!=null){
      clientAddViewClass.errorGetClient(error);
    }
  }

  @Override
  public void addClient(Client newClient) {
    if(clienAddControllerClass!=null){
      clienAddControllerClass.addClient(newClient);
    }
  }

  @Override
  public void getClient(String idClient) {
    if(clienAddControllerClass!=null){
      clienAddControllerClass.getClient(idClient);
    }
  }

  @Override
  public void modifyClient(Client client) {
    if(clienAddControllerClass!=null){
      clienAddControllerClass.modifyClient(client);
    }
  }


  @Override
  public void removeClient(String idClient) {
    if(clienAddControllerClass!=null){
      clienAddControllerClass.removeClient(idClient);
    }
  }

  @Override
  public void succesRemoveClient() {
    if(clientAddViewClass!=null){
      clientAddViewClass.succesRemoveClient();
    }
  }

  @Override
  public void errorRemoveClient(String error) {
    if(clientAddViewClass!=null){
      clientAddViewClass.errorRemoveClient(error);
    }
  }

  @Override
  public void successModifyClient() {
    if(clientAddViewClass!=null){
      clientAddViewClass.successModifyClient();
    }
  }

  @Override
  public void errorModifyClient(String error) {
    if(clientAddViewClass!=null){
      clientAddViewClass.errorModifyClient(error);
    }
  }

}
