package com.epacheco.reports.view.productsView.productAddView;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Pojo.Product.Product;

public interface ProductAddViewInterface {

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

}
