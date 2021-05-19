package com.epacheco.reports.Model.SaleModel;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Controller.SaleController.SaleControllerClass;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;
import com.epacheco.reports.Pojo.Product.Product;
import com.epacheco.reports.view.saleView.SaleViewClass;
import java.util.List;

public class SaleModelClass implements SaleModelInterface {
  private SaleViewClass saleViewClass;
  private SaleControllerClass saleControllerClass;

  public SaleModelClass(SaleViewClass saleViewClass) {
    this.saleViewClass = saleViewClass;
    this.saleControllerClass= new SaleControllerClass(this);
  }

  @Override
  public FragmentActivity getMyActivity() {
    return saleViewClass.getMyActivity();
  }

  @Override
  public void successGetClient(Client client) {
    if(saleViewClass!=null){
      saleViewClass.successGetClient(client);
    }
  }

  @Override
  public void errrorGetClient(String error) {
    if(saleViewClass!=null){
      saleViewClass.errrorGetClient(error);
    }
  }

  @Override
  public void successGetProduct(Product product) {
    if(saleViewClass!=null){
      saleViewClass.successGetProduct(product);
    }
  }

  @Override
  public void errorGetProduct(String error) {
    if(saleViewClass!=null){
      saleViewClass.errorGetProduct(error);
    }
  }

  @Override
  public void successAddClientDetail() {
    if(saleViewClass!=null){
      saleViewClass.successAddClientDetail();
    }
  }

  @Override
  public void errorAddClientDetail(String error) {
    if(saleViewClass!=null){
      saleViewClass.errorAddClientDetail(error);
    }
  }

  @Override
  public void successGetClientDetail(ClientDetail clientDetail) {
    if(saleViewClass!=null){
      saleViewClass.successGetClientDetail(clientDetail);
    }
  }

  @Override
  public void errorGetClientDetail(String error) {
    if(saleViewClass!=null){
      saleViewClass.errorGetClientDetail(error);
    }
  }

  @Override
  public void getCLient(String clientId) {
    if(saleControllerClass!=null){
      saleControllerClass.getCLient(clientId);
    }
  }

  @Override
  public void getProduct(String productId) {
    if(saleControllerClass!=null){
      saleControllerClass.getProduct(productId);
    }
  }

  @Override
  public void addClientDetail( List<ClientDetail>  clientDetail, Client client) {
    if(saleControllerClass!=null){
      saleControllerClass.addClientDetail(clientDetail,client);
    }
  }

  @Override
  public void getClientDetail(String id) {
    if(saleControllerClass!=null){
      saleControllerClass.getClientDetail(id);
    }
  }
}
