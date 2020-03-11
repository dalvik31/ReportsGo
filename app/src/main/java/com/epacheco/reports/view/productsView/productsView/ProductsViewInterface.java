package com.epacheco.reports.view.productsView.productsView;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Pojo.Product.Product;
import java.util.List;

public interface ProductsViewInterface {
  FragmentActivity getMyActivity();
  void successDownloadProducts(List<Product> productList);
  void errorDownloadProducts(String error);

}
