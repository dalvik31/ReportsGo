package com.epacheco.reports.Model.ClientModel.ClientDetailListModel;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Controller.ClientController.ClienDetailListController.ClientDetailListControllerClass;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;
import com.epacheco.reports.view.clientView.clientDetailListView.ClientDetailListViewClass;
import java.util.List;

public class ClientDetailListModelClass implements ClientDetailListModelInterface {
  private ClientDetailListViewClass clientDetailListViewClass;
  private ClientDetailListControllerClass clientDetailListControllerClass;

  public ClientDetailListModelClass(ClientDetailListViewClass clientDetailListViewClass) {
    this.clientDetailListViewClass = clientDetailListViewClass;
    this.clientDetailListControllerClass = new ClientDetailListControllerClass(this);
  }

  @Override
  public FragmentActivity getMyActivity() {
    return clientDetailListViewClass.getMyActivity();
  }

  @Override
  public void successGetDetailClientList(List<ClientDetail> clientDetail) {
    if(clientDetailListViewClass!=null){
      clientDetailListViewClass.successGetDetailClientList(clientDetail);
    }
  }

  @Override
  public void errorGetDetailClientList(String error) {
    if(clientDetailListViewClass!=null){
      clientDetailListViewClass.errorGetDetailClientList(error);
    }
  }

  @Override
  public void successGetClient(Client client) {
    if(clientDetailListViewClass!=null){
      clientDetailListViewClass.successGetClient(client);
    }
  }

  @Override
  public void errorGetClient(String error) {
    if(clientDetailListViewClass!=null){
      clientDetailListViewClass.errorGetClient(error);
    }
  }

  @Override
  public void getClientDetailList(String id) {
    if(clientDetailListControllerClass!=null){
      clientDetailListControllerClass.getClientDetailList(id);
    }
  }

  @Override
  public void getClient(String id) {
    if(clientDetailListControllerClass!=null){
      clientDetailListControllerClass.getClient(id);
    }
  }
}
