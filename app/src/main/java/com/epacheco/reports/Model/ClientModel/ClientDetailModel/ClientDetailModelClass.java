package com.epacheco.reports.Model.ClientModel.ClientDetailModel;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Controller.ClientController.ClientDetailController.ClientDetailControllerClass;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;
import com.epacheco.reports.view.clientView.clienDetailView.ClientDetailViewClass;

public class ClientDetailModelClass implements ClientDetailModelIterface {

  private ClientDetailViewClass clientDetailViewClass;
  private ClientDetailControllerClass clientDetailControllerClass;

  public ClientDetailModelClass(ClientDetailViewClass clientDetailViewClass) {
    this.clientDetailViewClass = clientDetailViewClass;
    this.clientDetailControllerClass = new ClientDetailControllerClass(this);
  }

  @Override
  public FragmentActivity getMyActivity() {
    if(clientDetailViewClass!=null){
      return clientDetailViewClass.getMyActivity();
    }
    return null;
  }

  @Override
  public void successAddClientDetail() {
    if(clientDetailViewClass!=null){
      clientDetailViewClass.successAddClientDetail();
    }
  }

  @Override
  public void errorAddClientDetail(String error) {
    if(clientDetailViewClass!=null){
      clientDetailViewClass.errorAddClientDetail(error);
    }
  }

  @Override
  public void successGetClientDetail(ClientDetail clientDetail) {
    if(clientDetailViewClass!=null){
      clientDetailViewClass.successGetClientDetail(clientDetail);
    }
  }

  @Override
  public void errorGetClientDetail(String error) {
    if(clientDetailViewClass!=null){
      clientDetailViewClass.errorGetClientDetail(error);
    }
  }

  @Override
  public void addClientDetail(ClientDetail clientDetail,String id) {
    if(clientDetailControllerClass!=null){
      clientDetailControllerClass.addClientDetail(clientDetail,id);
    }
  }

  @Override
  public void getClientDetail(String id) {
    if(clientDetailControllerClass!=null){
      clientDetailControllerClass.getClientDetail(id);
    }
  }


  @Override
  public void getClient(String id) {
    if(clientDetailControllerClass!=null){
      clientDetailControllerClass.getClient(id);
    }
  }

  @Override
  public void successGetClient(Client client) {
    if(clientDetailViewClass!=null){
      clientDetailViewClass.successGetClient(client);
    }
  }

  @Override
  public void errorGetClient(String error) {
    if(clientDetailViewClass!=null){
      clientDetailViewClass.errorGetClient(error);
    }
  }
}
