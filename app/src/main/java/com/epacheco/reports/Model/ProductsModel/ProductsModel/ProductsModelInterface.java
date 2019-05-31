package com.epacheco.reports.Model.ProductsModel.ProductsModel;

import android.support.v4.app.FragmentActivity;
import com.epacheco.reports.Pojo.Product.Product;
import java.util.List;

public interface ProductsModelInterface {
  //View methods
  FragmentActivity getMyActivity();
  void successDownloadProducts(List<Product> productList);
  void errorDownloadProducts(String error);

  //Controller methods
  void downloadPorducts(String productName, String producNumber);
}
