package com.epacheco.reports.Controller.ProductsController.ProductsAddController;

import android.graphics.Bitmap;
import android.net.Uri;
import com.epacheco.reports.Pojo.Product.Product;

public interface ProductsAddControllerInterface {

  void addProduct(Product product);
  void uploadImage(Bitmap imgUpload);
  void getProduct(String idProduct);
  void modifyProduct(Product product);
  void removeProduct(String idProduct);
}
