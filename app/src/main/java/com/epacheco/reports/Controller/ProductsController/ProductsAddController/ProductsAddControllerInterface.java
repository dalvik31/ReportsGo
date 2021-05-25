package com.epacheco.reports.Controller.ProductsController.ProductsAddController;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import com.epacheco.reports.Pojo.Product.Product;

public interface ProductsAddControllerInterface {

  void addProduct(Product product);
  void uploadImage(Context ctx,String imgUpload);
  void getProduct(String idProduct);
  void modifyProduct(Product product);
  void removeProduct(String idProduct);
}
