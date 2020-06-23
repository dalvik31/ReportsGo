package com.epacheco.reports.Model.ProductsModel.ProductsModel;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Controller.ProductsController.ProductsController.ProductsControllerClass;
import com.epacheco.reports.Pojo.Product.Product;
import com.epacheco.reports.view.productsView.productsView.ProductViewClass;
import java.util.List;

public class ProductsModelClass implements ProductsModelInterface{
  private ProductViewClass productsViewClass;
  private ProductsControllerClass productsControllerClass;
  public ProductsModelClass(ProductViewClass productsViewClass) {
    this.productsViewClass = productsViewClass;
    this.productsControllerClass = new ProductsControllerClass(this);
  }

  @Override
  public FragmentActivity getMyActivity() {
    return productsViewClass.getMyActivity();
  }

  @Override
  public void successDownloadProducts(List<Product> productList) {
    if(productsViewClass!=null){
      productsViewClass.successDownloadProducts(productList);
    }
  }

  @Override
  public void errorDownloadProducts(String error) {
    if(productsViewClass!=null){
      productsViewClass.errorDownloadProducts(error);
    }
  }

  @Override
  public void downloadPorducts(String productName, String producNumber) {
    if(productsControllerClass!=null){
      productsControllerClass.downloadPorducts(productName,producNumber);
    }
  }

}
