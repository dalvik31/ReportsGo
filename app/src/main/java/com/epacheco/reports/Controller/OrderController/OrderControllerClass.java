package com.epacheco.reports.Controller.OrderController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.epacheco.reports.Model.OrderModel.OrderModelClass;
import com.epacheco.reports.Pojo.Order.OrderList;
import com.epacheco.reports.R;
import com.epacheco.reports.tools.Constants;
import com.epacheco.reports.tools.ReportsApplication;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;

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
            Collections.reverse(clientList);
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
  public void createOrder(final OrderList orderList) {

    if(orderModelClass!=null && mAuth!=null&& mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");

      final DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_ORDERS_TABLE_FIREBASE);

      usersRef.orderByChild("nameOrder").equalTo(orderList.getNameOrder()).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

          OrderList myClient = null;
          for (DataSnapshot postSnapshot: snapshot.getChildren()) {
            myClient = postSnapshot.getValue(OrderList.class);
          }
          if(myClient!=null){
            orderModelClass.errorCreateOrderList(ReportsApplication.getMyApplicationContext().getString(R.string.item_list_repeat));
          }else{

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

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
          orderModelClass.errorCreateOrderList(databaseError.getMessage());
        }
      });





    }
  }

  @Override
  public void removeOrderList(String orderId) {
    if(orderModelClass!=null && mAuth!=null&& mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");
      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_ORDERS_TABLE_FIREBASE);
      usersRef.child(orderId).removeValue()
          .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
              orderModelClass.successRemoveOrderList();
            }
          })
          .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              orderModelClass.errorRemoveOrderList(e.getMessage());
            }
          });
    }
  }
}
