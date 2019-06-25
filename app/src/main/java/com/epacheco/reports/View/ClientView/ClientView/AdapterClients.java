package com.epacheco.reports.View.ClientView.ClientView;

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
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.ReportsApplication;
import java.util.ArrayList;

public class AdapterClients extends RecyclerView.Adapter<AdapterClients.HolderClients> {

  private ArrayList<Client> listClients;
  private onItemClientClic onItemClientClic;

  AdapterClients(ArrayList<Client> listClients) {
    this.listClients = listClients;
  }

  @NonNull
  @Override
  public HolderClients onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    // create a new view
    View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_client, viewGroup, false);
    return new HolderClients(v);
  }

  @Override
  public void onBindViewHolder(@NonNull HolderClients holderClients, int i) {
    final Client clientSelected = listClients.get(i);
    holderClients.btnPhone.setVisibility(
        clientSelected.getPhone() != null && !clientSelected.getPhone().isEmpty() ? View.VISIBLE
            : View.GONE);
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
  }

  @Override
  public int getItemCount() {
    return listClients.size();
  }

  class HolderClients extends RecyclerView.ViewHolder {

    private TextView txtName, txtDesc;
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

    }
  }

  private com.epacheco.reports.View.ClientView.ClientView.onItemClientClic getOnItemClientClic() {
    return onItemClientClic;
  }

  void setOnItemClientClic(
      com.epacheco.reports.View.ClientView.ClientView.onItemClientClic onItemClientClic) {
    this.onItemClientClic = onItemClientClic;
  }
}
