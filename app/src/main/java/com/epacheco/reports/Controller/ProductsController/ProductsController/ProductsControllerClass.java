package com.epacheco.reports.Controller.ProductsController.ProductsController;

import androidx.annotation.NonNull;
import com.epacheco.reports.Model.ProductsModel.ProductsModel.ProductsModelClass;
import com.epacheco.reports.Pojo.Product.Product;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.Constants;
import com.epacheco.reports.Tools.ReportsApplication;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class ProductsControllerClass implements ProductsControllerInterface{
  private FirebaseAuth mAuth;
  private ProductsModelClass productsModelClass;

  public ProductsControllerClass(ProductsModelClass productsModelClass) {
    this.productsModelClass = productsModelClass;
    this.mAuth = FirebaseAuth.getInstance();
  }

  @Override
  public void downloadPorducts(String productName, String producNumber) {
    if(productsModelClass!=null && mAuth.getUid()!=null){
      final ArrayList<Product> clientList = new ArrayList<>();

      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");

      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_PRODUCTS_TABLE_FIREBASE);
      String paramName;
      String orderName;
      if(productName!=null && !productName.isEmpty()){
         paramName = productName;
        orderName = "productName";
      }else  if(producNumber!=null && !producNumber.isEmpty()){
        paramName = producNumber;
        orderName = "productId";
      }else{
        paramName = "";
        orderName = "productName";
      }


      usersRef.orderByChild(orderName).startAt(paramName)
          .endAt( paramName + "\uf8ff").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
          clientList.clear();
          for (DataSnapshot postSnapshot: snapshot.getChildren()) {
            Product university = postSnapshot.getValue(Product.class);
            clientList.add(university);
          }
          if(clientList.size()>0){
            productsModelClass.successDownloadProducts(clientList);
          }else{
            productsModelClass.errorDownloadProducts(ReportsApplication.getMyApplicationContext().getString(
                R.string.msg_zero_products));
          }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
          productsModelClass.errorDownloadProducts(databaseError.getMessage());
        }
      });
    }
  }
}
