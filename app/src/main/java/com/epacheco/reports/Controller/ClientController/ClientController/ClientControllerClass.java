package com.epacheco.reports.Controller.ClientController.ClientController;

import android.support.annotation.NonNull;
import com.epacheco.reports.Model.ClientModel.ClientModel.ClientModelInterface;
import com.epacheco.reports.Pojo.Client.Client;
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

public class ClientControllerClass implements ClientControllerInterface {
  private FirebaseAuth mAuth;
  private ClientModelInterface clientModelInterface;

  public ClientControllerClass(ClientModelInterface clientModelInterface) {
    this.clientModelInterface = clientModelInterface;
    this.mAuth = FirebaseAuth.getInstance();
  }

  @Override
  public void downloadClients(String name) {
    if(clientModelInterface!=null && mAuth.getUid()!=null){
      final ArrayList<Client> clientList = new ArrayList<>();

      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");

      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_TABLE_FIREBASE);

      String paramName = name!=null && !name.isEmpty() ? name : "";

      usersRef.orderByChild("name").startAt(paramName)
          .endAt( paramName + "\uf8ff").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
          clientList.clear();
          for (DataSnapshot postSnapshot: snapshot.getChildren()) {
            Client university = postSnapshot.getValue(Client.class);
            clientList.add(university);
          }
          if(clientList.size()>0){
            clientModelInterface.successDownloadClients(clientList);
          }else{
            clientModelInterface.errorDownloadClients(ReportsApplication.getMyApplicationContext().getString(R.string.msg_zero_clients));
          }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
          clientModelInterface.errorDownloadClients(databaseError.getMessage());
        }
      });
    }
  }
}
