package com.epacheco.reports.Controller.ClientController.ClientDetailController;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.epacheco.reports.Model.ClientModel.ClientDetailModel.ClientDetailModelIterface;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;
import com.epacheco.reports.Tools.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClientDetailControllerClass implements ClientDetailControllerIterface {
  private FirebaseAuth mAuth;

  private ClientDetailModelIterface clientDetailModelIterface;

  public ClientDetailControllerClass(ClientDetailModelIterface clientDetailModelIterface) {
    this.clientDetailModelIterface = clientDetailModelIterface;
    this.mAuth = FirebaseAuth.getInstance();
  }

  @Override
  public void getClient(String id) {
    if(clientDetailModelIterface!=null && mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");

      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_TABLE_FIREBASE);
      String paramName = id!=null && !id.isEmpty() ? id : "";

      usersRef.orderByChild("id").equalTo(paramName).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

          Client myClient = null;
          for (DataSnapshot postSnapshot: snapshot.getChildren()) {
            myClient = postSnapshot.getValue(Client.class);
          }
          if(myClient!=null){
            clientDetailModelIterface.successGetClient(myClient);
          }else{
            clientDetailModelIterface.errorGetClient("");
          }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
          clientDetailModelIterface.errorGetClient(databaseError.getMessage());
        }
      });
    }
  }

  @Override
  public void addClientDetail(ClientDetail clientDetail,String id) {
    if(clientDetailModelIterface!=null && mAuth!=null&& mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");

      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_DETAIL_TABLE_FIREBASE);
      usersRef.child(id).child(clientDetail.getDatePayment()).setValue(clientDetail, new CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
          if (databaseError != null) {
            clientDetailModelIterface.errorAddClientDetail(databaseError.getMessage());

          } else {
            clientDetailModelIterface.successAddClientDetail();
          }
        }

      });
    }
  }

  @Override
  public void getClientDetail(String id) {
    if(clientDetailModelIterface!=null && mAuth.getUid()!=null) {
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");

      DatabaseReference usersRef = myRef.child(mAuth.getUid())
          .child(Constants.CLIENT_DETAIL_TABLE_FIREBASE);

      usersRef.child(id).orderByKey().limitToLast(1)
          .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

              ClientDetail myClient = null;
              for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                myClient = postSnapshot.getValue(ClientDetail.class);
              }
              if (myClient != null) {
                clientDetailModelIterface.successGetClientDetail(myClient);
              } else {
                clientDetailModelIterface.errorGetClientDetail("");
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
              clientDetailModelIterface.errorGetClientDetail(databaseError.getMessage());
            }
          });
    }
  }
/*
  @Override
  public void getClientDetailForDate(String Date) {
    if(clientDetailModelIterface!=null && mAuth.getUid()!=null) {
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");

      DatabaseReference usersRef = myRef.child(mAuth.getUid())
          .child(Constants.CLIENT_DETAIL_TABLE_FIREBASE);
      String paramName = Date != null && !Date.isEmpty() ? Date : "";

      usersRef.orderByChild("date").startAt(1).endAt(1)
          .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

              ClientDetail myClient = null;
              for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                myClient = postSnapshot.getValue(ClientDetail.class);
              }
              if (myClient != null) {
                clientDetailModelIterface.successGetClientDetail(myClient);
              } else {
                clientDetailModelIterface.errorGetClientDetail("eee");
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
              clientDetailModelIterface.errorGetClientDetail(databaseError.getMessage());
            }
          });
    }
  }*/


}
