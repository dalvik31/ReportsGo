package com.epacheco.reports.Controller.ClientController.ClientAddController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.epacheco.reports.Model.ClientModel.ClientAddModel.ClientAddModelClass;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.tools.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClienAddControllerClass implements ClientAddControllerInterface{
  private FirebaseAuth mAuth;
  private ClientAddModelClass clienAddControllerClass;

  public ClienAddControllerClass(ClientAddModelClass clienAddControllerClass) {
    this.clienAddControllerClass = clienAddControllerClass;
    this.mAuth = FirebaseAuth.getInstance();
  }

  @Override
  public void addClient(Client newClient) {

    if(clienAddControllerClass!=null && mAuth!=null&& mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");

      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_TABLE_FIREBASE);
      usersRef.child(newClient.getId()).setValue(newClient, new CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
          if (databaseError != null) {
            clienAddControllerClass.errorAddClient(databaseError.getMessage());

          } else {
            clienAddControllerClass.succesAddClient();
          }
        }

      });
    }

  }

  @Override
  public void getClient(String idClient) {

    if(clienAddControllerClass!=null && mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");

      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_TABLE_FIREBASE);
      String paramName = idClient!=null && !idClient.isEmpty() ? idClient : "";

      usersRef.orderByChild("id").equalTo(paramName).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

          Client myClient = null;
          for (DataSnapshot postSnapshot: snapshot.getChildren()) {
            myClient = postSnapshot.getValue(Client.class);
          }
          if(myClient!=null){
            clienAddControllerClass.successGetClient(myClient);
          }else{
            clienAddControllerClass.errorGetClient("");
          }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
          clienAddControllerClass.errorGetClient(databaseError.getMessage());
        }
      });
    }
  }

  @Override
  public void modifyClient(Client client) {
    if(clienAddControllerClass!=null && mAuth!=null&& mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");
      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_TABLE_FIREBASE);
      usersRef.child(client.getId()).setValue(client, new CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
          if (databaseError != null) {
            clienAddControllerClass.errorModifyClient(databaseError.getMessage());

          } else {
            clienAddControllerClass.successModifyClient();
          }
        }

      });
    }
  }

  @Override
  public void removeClient(String idClient) {
    if(clienAddControllerClass!=null && mAuth!=null&& mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");
      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_TABLE_FIREBASE);
      usersRef.child(idClient).removeValue()
          .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
             clienAddControllerClass.succesRemoveClient();
            }
          })
          .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              clienAddControllerClass.errorRemoveClient(e.getMessage());
            }
          });
    }
  }
}
