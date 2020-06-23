package com.epacheco.reports.Model.ProductsModel.ProductsAddModel;

import android.graphics.Bitmap;
import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Pojo.Product.Product;

public interface ProductsAddModelInterface {

  //View methods
  FragmentActivity getMyActivity();
  void successUploadImage(String imgUrl);
  void errorUploadImage(String error);

  void successAddProduct();
  void errorAddProduct(String error);

  void successGetProduct(Product product);
  void errorGetProduct(String error);

  void successModifyProduct();
  void errorModifyProduct(String error);

  void successRemoveProduct();
  void errorRemoveProduct(String error);

  //Controller methods
  void addProduct(Product product);
  void uploadImage(Bitmap imgUpload);
  void getProduct(String idProduct);
  void modifyProduct(Product product);
  void removeProduct(String idProduct);
}
