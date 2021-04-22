package com.epacheco.reports.Controller.OrderController.OrderCreateController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.epacheco.reports.Model.OrderModel.CreateOrderModel.OrderCreateModelClass;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;
import com.epacheco.reports.Pojo.Product.Product;
import com.epacheco.reports.Tools.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderCreateControllerClass implements OrderCreateControllerInterface {

  private OrderCreateModelClass orderCreateModelClass;
  private FirebaseAuth mAuth;

  public OrderCreateControllerClass(OrderCreateModelClass orderCreateModelClass) {
    this.orderCreateModelClass = orderCreateModelClass;
    this.mAuth = FirebaseAuth.getInstance();
  }

  @Override
  public void createNewOrder( OrderDetail orderDetail) {
    if(orderCreateModelClass!=null && mAuth!=null&& mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");

      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_ORDERS_TABLE_FIREBASE);
      usersRef.child(orderDetail.getOrderListId()).child("orderLists").child(orderDetail.getOrderId()).setValue(orderDetail, new CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
          if (databaseError != null) {
            orderCreateModelClass.errorCreateOrder(databaseError.getMessage());

          } else {
            orderCreateModelClass.successCreateOrder();
          }
        }

      });
    }
  }

  @Override
  public void getClient(String idClient) {
    if(orderCreateModelClass!=null && mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");

      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_ORDERS_TABLE_FIREBASE);
      String paramName = idClient!=null && !idClient.isEmpty() ? idClient : "";

      usersRef.orderByChild("id").equalTo(paramName).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

          Client myClient = null;
          for (DataSnapshot postSnapshot: snapshot.getChildren()) {
            myClient = postSnapshot.getValue(Client.class);
          }
          if(myClient!=null){
            orderCreateModelClass.successGetClient(myClient);
          }else{
            orderCreateModelClass.errorGetClient("");
          }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
          orderCreateModelClass.errorGetClient(databaseError.getMessage());
        }
      });
    }
  }

  @Override
  public void getProduct(String idProduct) {
    if(orderCreateModelClass!=null && mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");

      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_PRODUCTS_TABLE_FIREBASE);
      String paramName = idProduct!=null && !idProduct.isEmpty() ? idProduct : "";

      usersRef.orderByChild("id").equalTo(paramName).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

          Product myProduct = null;
          for (DataSnapshot postSnapshot: snapshot.getChildren()) {
            myProduct = postSnapshot.getValue(Product.class);
          }
          if(myProduct!=null){
            orderCreateModelClass.succesGetProduct(myProduct);
          }else{
            orderCreateModelClass.errorGetProduct("");
          }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
          orderCreateModelClass.errorGetProduct(databaseError.getMessage());
        }
      });
    }



  }


}
