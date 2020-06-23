package com.epacheco.reports.Model.ProductsModel.ProductsAddModel;

import android.graphics.Bitmap;
import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Controller.ProductsController.ProductsAddController.ProductsAddControllerClass;
import com.epacheco.reports.Pojo.Product.Product;
import com.epacheco.reports.view.productsView.productAddView.ProductAddViewClass;

public class ProductsAddModelClass implements ProductsAddModelInterface {

  private ProductAddViewClass productAddViewClass;
  private ProductsAddControllerClass productsAddControllerClass;

  public ProductsAddModelClass(ProductAddViewClass productAddViewClass) {
    this.productAddViewClass = productAddViewClass;
    this.productsAddControllerClass = new ProductsAddControllerClass(this);
  }

  @Override
  public FragmentActivity getMyActivity() {
    return productAddViewClass.getMyActivity();
  }

  @Override
  public void successUploadImage(String imgUrl) {
    if(productAddViewClass!=null){
      productAddViewClass.successUploadImage(imgUrl);
    }
  }

  @Override
  public void errorUploadImage(String error) {
    if(productAddViewClass!=null){
      productAddViewClass.errorUploadImage(error);
    }
  }

  @Override
  public void successAddProduct() {
    if(productAddViewClass!=null){
      productAddViewClass.successAddProduct();
    }
  }

  @Override
  public void errorAddProduct(String error) {
    if(productAddViewClass!=null){
      productAddViewClass.errorAddProduct(error);
    }
  }

  @Override
  public void successGetProduct(Product product) {
    if(productAddViewClass!=null){
      productAddViewClass.successGetProduct(product);
    }
  }

  @Override
  public void errorGetProduct(String error) {
    if(productAddViewClass!=null){
      productAddViewClass.errorGetProduct(error);
    }
  }

  @Override
  public void successModifyProduct() {
    if(productAddViewClass!=null){
      productAddViewClass.successModifyProduct();
    }
  }

  @Override
  public void errorModifyProduct(String error) {
    if(productAddViewClass!=null){
      productAddViewClass.errorModifyProduct(error);
    }
  }

  @Override
  public void successRemoveProduct() {
    if(productAddViewClass!=null){
      productAddViewClass.successRemoveProduct();
    }
  }

  @Override
  public void errorRemoveProduct(String error) {
    if(productAddViewClass!=null){
      productAddViewClass.errorRemoveProduct(error);
    }
  }

  @Override
  public void addProduct(Product product) {
    if(productsAddControllerClass!=null){
      productsAddControllerClass.addProduct(product);
    }
  }

  @Override
  public void uploadImage(Bitmap imgUpload) {
    if(productsAddControllerClass!=null){
      productsAddControllerClass.uploadImage(imgUpload);
    }
  }

  @Override
  public void getProduct(String idProduct) {
    if(productsAddControllerClass!=null){
      productsAddControllerClass.getProduct(idProduct);
    }
  }

  @Override
  public void modifyProduct(Product product) {
    if(productsAddControllerClass!=null){
      productsAddControllerClass.modifyProduct(product);
    }
  }

  @Override
  public void removeProduct(String idProduct) {
    if(productsAddControllerClass!=null){
      productsAddControllerClass.removeProduct(idProduct);
    }
  }


}
