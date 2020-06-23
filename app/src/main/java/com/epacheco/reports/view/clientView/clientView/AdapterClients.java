package com.epacheco.reports.view.clientView.clientView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.ReportsApplication;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class AdapterClients extends RecyclerView.Adapter<AdapterClients.HolderClients> {

  private ArrayList<Client> listClients;
  private onItemClientClic onItemClientClic;
  private Context context;
  AdapterClients(ArrayList<Client> listClients) {
    this.listClients = listClients;
  }

  @NonNull
  @Override
  public HolderClients onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    // create a new view
    context = viewGroup.getContext();
    View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_client, viewGroup, false);
    return new HolderClients(v);
  }

  @Override
  public void onBindViewHolder(@NonNull HolderClients holderClients, int i) {
    final Client clientSelected = listClients.get(i);

    holderClients.btnPhone.setVisibility(View.GONE);
    if( clientSelected.getPhone() != null && !clientSelected.getPhone().isEmpty() ){
      holderClients.btnPhone.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          Intent intent = new Intent(Intent.ACTION_CALL);

          intent.setData(Uri.parse("tel:" + clientSelected.getPhone()));
          context.startActivity(intent);
        }
      });
      holderClients.btnPhone.setVisibility(View.VISIBLE);
    }
    holderClients.txtName.setText(String.format(
        ReportsApplication.getMyApplicationContext().getString(R.string.txt_client_name_format),
        listClients.get(i).getName(), listClients.get(i).getLastNanme()));

    holderClients.txtDesc.setText(listClients.get(i).getDetail());
    holderClients.btnModify.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (getOnItemClientClic() != null) {
          getOnItemClientClic().onItemClientClic(v, clientSelected.getId());
        }

      }
    });
    holderClients.cardClient.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (getOnItemClientClic() != null) {
          getOnItemClientClic().onItemClientClic(v, clientSelected.getId());
        }
      }
    });

    holderClients.btnDetail.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (getOnItemClientClic() != null) {
          getOnItemClientClic().onItemClientClic(v, clientSelected.getId());
        }
      }
    });


    String clientDeb = "0";
    if(clientSelected.getClientsDetails()!=null){
      Map<String, ClientDetail> map = clientSelected.getClientsDetails();
      TreeMap<String, ClientDetail> treeMap = new TreeMap<>(map);
      if(treeMap.size()>0){
        ClientDetail myClientDetail = treeMap.lastEntry().getValue();
        if(myClientDetail!=null)
        clientDeb = String.valueOf(treeMap.lastEntry().getValue().getDebt());
      }

    }

    holderClients.txtDebt.setText(String.format(ReportsApplication.getMyApplicationContext().getString(R.string.txt_client_amount_format),clientDeb));
  }

  @Override
  public int getItemCount() {
    return listClients.size();
  }

  class HolderClients extends RecyclerView.ViewHolder {

    private TextView txtName, txtDesc, txtDebt;
    private ImageView btnPhone, btnModify, btnDetail;
    private CardView cardClient;

    HolderClients(View v) {
      super(v);
      txtName = itemView.findViewById(R.id.lbl_client_name);
      txtDesc = itemView.findViewById(R.id.lbl_client_description);
      btnPhone = itemView.findViewById(R.id.btn_phone);
      btnModify = itemView.findViewById(R.id.btn_modify);
      btnDetail = itemView.findViewById(R.id.btn_detail);
      cardClient = itemView.findViewById(R.id.card_client);
      txtDebt= itemView.findViewById(R.id.lbl_client_debt);
    }
  }

  private com.epacheco.reports.view.clientView.clientView.onItemClientClic getOnItemClientClic() {
    return onItemClientClic;
  }

  void setOnItemClientClic(
      com.epacheco.reports.view.clientView.clientView.onItemClientClic onItemClientClic) {
    this.onItemClientClic = onItemClientClic;
  }
}
