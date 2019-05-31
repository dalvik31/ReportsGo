package com.epacheco.reports.Controller.ClientController.ClienDetailListController;

import android.support.annotation.NonNull;
import com.epacheco.reports.Model.ClientModel.ClientDetailListModel.ClientDetailListModelClass;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;

public class ClientDetailListControllerClass implements ClientDetailListControllerInterface {

  private ClientDetailListModelClass clientDetailListModelClass;
  private FirebaseAuth mAuth;

  public ClientDetailListControllerClass(ClientDetailListModelClass clientDetailListModelClass) {
    this.clientDetailListModelClass = clientDetailListModelClass;
    this.mAuth = FirebaseAuth.getInstance();
  }

  @Override
  public void getClientDetailList(String id) {
    if(clientDetailListModelClass!=null && mAuth.getUid()!=null){
      final ArrayList<ClientDetail> clientList = new ArrayList<>();

      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");

      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_DETAIL_TABLE_FIREBASE);
      usersRef.child(id).orderByChild("datePayment").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {
          clientList.clear();
          for (DataSnapshot postSnapshot: snapshot.getChildren()) {
            ClientDetail university = postSnapshot.getValue(ClientDetail.class);
            clientList.add(university);
          }
          Collections.reverse(clientList);
          clientDetailListModelClass.successGetDetailClientList(clientList);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
          clientDetailListModelClass.errorGetDetailClientList(databaseError.getMessage());
        }
      });
    }
  }

  @Override
  public void getClient(String id) {
    if(clientDetailListModelClass!=null && mAuth.getUid()!=null){
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
            clientDetailListModelClass.successGetClient(myClient);
          }else{
            clientDetailListModelClass.errorGetClient(clientDetailListModelClass.getMyActivity().getString(
                R.string.msg_error_sistema));
          }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
          clientDetailListModelClass.errorGetClient(databaseError.getMessage());
        }
      });
    }
  }
}
