package com.epacheco.reports.Controller.OrderController.OrderDetailController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.epacheco.reports.Model.OrderModel.OrderDetailModel.OrderDetailModelClass;
import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.Constants;
import com.epacheco.reports.Tools.ReportsApplication;
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

public class OrderDetailControllerClass implements OrderDetailControllerInterface {
  private FirebaseAuth mAuth;
  private OrderDetailModelClass orderDetailModelClass;
  private boolean isMoveOrder;

  public OrderDetailControllerClass(OrderDetailModelClass orderDetailModelClass) {
    this.orderDetailModelClass = orderDetailModelClass;
    this.mAuth = FirebaseAuth.getInstance();
  }

  @Override
  public void getDetailOrderList(String orderIdList) {
    if(orderDetailModelClass!=null && mAuth.getUid()!=null){
      final ArrayList<OrderDetail> orderDetailsList = new ArrayList<>();

      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");

      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_ORDERS_TABLE_FIREBASE);

      usersRef.child(orderIdList).child("orderLists").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {
          orderDetailsList.clear();
          for (DataSnapshot postSnapshot: snapshot.getChildren()) {
            OrderDetail university = postSnapshot.getValue(OrderDetail.class);
            orderDetailsList.add(university);
          }
          if(orderDetailsList.size()>0){
            Collections.reverse(orderDetailsList);
            orderDetailModelClass.successGetListDetail(orderDetailsList);
          }else{
            orderDetailModelClass.errorGetListDetail(ReportsApplication.getMyApplicationContext().getString(
                R.string.msg_zero_orders_details));
          }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
          orderDetailModelClass.errorGetListDetail(databaseError.getMessage());
        }
      });
    }
  }

  @Override
  public void moveOrder(String idOrder, OrderDetail orderDetail) {

    if(orderDetailModelClass!=null && mAuth!=null&& mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");
      String idRemove = orderDetail.getOrderListId();
      orderDetail.setOrderListId(idOrder);
      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_ORDERS_TABLE_FIREBASE);
      usersRef.child(orderDetail.getOrderListId()).child("orderLists").child(orderDetail.getOrderId()).setValue(orderDetail, new CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
          if (databaseError != null) {
            orderDetailModelClass.errorMoveOrder(databaseError.getMessage());
          } else {
            isMoveOrder = true;
            removeOrderDetail(idRemove, orderDetail.getOrderId());
          }
        }

      });
    }

  }


  @Override
  public void removeOrderDetail(String orderIdList,String orderItemId) {
    if(orderDetailModelClass!=null && mAuth!=null&& mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");
      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_ORDERS_TABLE_FIREBASE);
      usersRef.child(orderIdList).child("orderLists").child(orderItemId).removeValue()
          .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
              if(isMoveOrder)
              orderDetailModelClass.successMoveOrder();
              else
                orderDetailModelClass.successremoveOrderDetail();
            }
          })
          .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              orderDetailModelClass.errorremoveOrderDetail(e.getMessage());
            }
          });
    }
  }

  @Override
  public void orderItemBuy(String orderIdList,String orderItemId,OrderDetail orderDetail) {
    if(orderDetailModelClass!=null && mAuth!=null&& mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");
      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_ORDERS_TABLE_FIREBASE);
      usersRef.child(orderIdList).child("orderLists").child(orderItemId).setValue(orderDetail, new CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
          if (databaseError != null) {
            orderDetailModelClass.errorOrderBuyElement(databaseError.getMessage());

          } else {
            orderDetailModelClass.successOrderBuyElement();
          }
        }

      });
    }
  }
}
