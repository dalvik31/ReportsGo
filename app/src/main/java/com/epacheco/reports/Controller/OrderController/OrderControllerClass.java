package com.epacheco.reports.Controller.OrderController;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.epacheco.reports.Model.OrderModel.OrderModelClass;
import com.epacheco.reports.Pojo.Order.OrderList;
import com.epacheco.reports.Pojo.Product.Product;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.Constants;
import com.epacheco.reports.Tools.ReportsApplication;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class OrderControllerClass implements OrderControllerInterface{
  private FirebaseAuth mAuth;
  private OrderModelClass orderModelClass;

  public OrderControllerClass(OrderModelClass orderModelClass) {
    this.orderModelClass = orderModelClass;
    this.mAuth = FirebaseAuth.getInstance();
  }

  @Override
  public void getOrders() {
    if(orderModelClass!=null && mAuth.getUid()!=null){
      final ArrayList<OrderList> clientList = new ArrayList<>();

      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");

      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_ORDERS_TABLE_FIREBASE);


      usersRef.orderByChild("dateOrder").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
          clientList.clear();
          for (DataSnapshot postSnapshot: snapshot.getChildren()) {
            OrderList university = postSnapshot.getValue(OrderList.class);
            clientList.add(university);
          }
          if(clientList.size()>0){
            orderModelClass.successGetOrderList(clientList);
          }else{
            orderModelClass.errorGetOrderList(ReportsApplication.getMyApplicationContext().getString(
                R.string.msg_zero_products));
          }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
          orderModelClass.errorGetOrderList(databaseError.getMessage());
        }
      });
    }
  }

  @Override
  public void createOrder(OrderList orderList) {

    if(orderModelClass!=null && mAuth!=null&& mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");

      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_ORDERS_TABLE_FIREBASE);
      usersRef.child(orderList.getDateOrder()).setValue(orderList, new CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
          if (databaseError != null) {
            orderModelClass.errorCreateOrderList(databaseError.getMessage());
          } else {
            orderModelClass.successCreateOrderList();
          }
        }

      });
    }
  }
}
