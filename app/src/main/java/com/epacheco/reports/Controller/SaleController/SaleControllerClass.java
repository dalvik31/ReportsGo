package com.epacheco.reports.Controller.SaleController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.epacheco.reports.Model.SaleModel.SaleModelClass;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;
import com.epacheco.reports.Pojo.Product.Product;
import com.epacheco.reports.Tools.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;
import java.util.Random;

public class SaleControllerClass implements SaleControllerInterface{

  private SaleModelClass saleModelClass;
  private FirebaseAuth mAuth;

  public SaleControllerClass(SaleModelClass saleModelClass) {
    this.saleModelClass = saleModelClass;
    this.mAuth = FirebaseAuth.getInstance();
  }

  @Override
  public void getCLient(String clientId) {
    if(saleModelClass!=null && mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");

      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_TABLE_FIREBASE);
      String paramName = clientId!=null && !clientId.isEmpty() ? clientId : "";

      usersRef.orderByChild("id").equalTo(paramName).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

          Client myClient = null;
          for (DataSnapshot postSnapshot: snapshot.getChildren()) {
            myClient = postSnapshot.getValue(Client.class);
          }
          if(myClient!=null){
            saleModelClass.successGetClient(myClient);
          }else{
            saleModelClass.errrorGetClient("");
          }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
          saleModelClass.errrorGetClient(databaseError.getMessage());
        }
      });
    }
  }

  @Override
  public void getProduct(String productId) {
    if(saleModelClass!=null && mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");

      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_PRODUCTS_TABLE_FIREBASE);
      String paramName = productId!=null && !productId.isEmpty() ? productId : "";

      usersRef.orderByChild("productId").equalTo(paramName).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

          Product myProduct = null;
          for (DataSnapshot postSnapshot: snapshot.getChildren()) {
            myProduct = postSnapshot.getValue(Product.class);
          }
          if(myProduct!=null){
            saleModelClass.successGetProduct(myProduct);
          }else{
            saleModelClass.errorGetProduct("");
          }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
          saleModelClass.errorGetProduct(databaseError.getMessage());
        }
      });
    }
  }

  @Override
  public void addClientDetail(final List<ClientDetail> clientDetail, String idClient) {
    if(saleModelClass!=null && mAuth!=null&& mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      final DatabaseReference myRef = database.getReference("Reports");

      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_TABLE_FIREBASE);

      for(final ClientDetail clientDetail1: clientDetail){
        usersRef.child(idClient).child(Constants.CLIENT_DETAIL_TABLE_FIREBASE).child(clientDetail1.getDatePayment() + new Random().nextInt()).setValue(clientDetail1, new CompletionListener() {
          @Override
          public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
            if (databaseError != null) {

              saleModelClass.errorAddClientDetail(databaseError.getMessage());
            }
            DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_PRODUCTS_TABLE_FIREBASE).child(clientDetail1.getProductId()).child("inStock");
            usersRef.setValue(clientDetail1.getUpdateStock());
          }

        });
      }
      saleModelClass.successAddClientDetail();

    /*  usersRef.child(idClient).setValue(clientDetail, new CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
          if (databaseError != null) {
            saleModelClass.errorAddClientDetail(databaseError.getMessage());

          } else {
            saleModelClass.successAddClientDetail();
          }
        }

      });*/
    }
  }

  @Override
  public void getClientDetail(String id) {
    if(saleModelClass!=null && mAuth.getUid()!=null) {
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");
      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_TABLE_FIREBASE);


      usersRef.child(id).child(Constants.CLIENT_DETAIL_TABLE_FIREBASE).orderByChild("datePayment").limitToLast(1)
          .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

              ClientDetail myClient = null;
              for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                myClient = postSnapshot.getValue(ClientDetail.class);
              }
              if (myClient != null) {
                saleModelClass.successGetClientDetail(myClient);
              } else {
                saleModelClass.errorGetClientDetail("");
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
              saleModelClass.errorGetClientDetail(databaseError.getMessage());
            }
          });
    }
  }
}
