package com.epacheco.reports.view.clientView.clientDetailListView;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;
import com.epacheco.reports.R;
import com.epacheco.reports.tools.ReportsApplication;
import com.epacheco.reports.tools.Tools;

import java.util.List;

public class AdapterClientDetailPayments extends  RecyclerView.Adapter<AdapterClientDetailPayments.HolderClientDetailPayments>{

  private List<ClientDetail> clientDetailsList;

  public void setList(List<ClientDetail> clientDetailsList){
    this.clientDetailsList= clientDetailsList;
  }
  public String getCadenaShare(){
    StringBuilder cadena= new StringBuilder();
    for(ClientDetail clientDetail : clientDetailsList){
      cadena.append( clientDetail.toString());

    }
    return cadena.toString();
  }

  @NonNull
  @Override
  public HolderClientDetailPayments onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

    return getViewHolder(viewGroup, inflater);

  }

  @NonNull
  private HolderClientDetailPayments getViewHolder(ViewGroup parent, LayoutInflater inflater) {
    HolderClientDetailPayments viewHolder;
    View v1 = inflater.inflate(R.layout.item_client_detail_payment, parent, false);
    viewHolder = new HolderClientDetailPayments(v1);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull HolderClientDetailPayments holderClientDetailPayments, int i) {

        ClientDetail clientDetail = clientDetailsList.get(i);
        double amount = clientDetail.getAmount();
        holderClientDetailPayments.txt_amount.setText(String.format(ReportsApplication.getMyApplicationContext().getString(clientDetail.isPay() ? R.string.txt_client_amount_format_minum:R.string.txt_client_amount_format_plus), String.valueOf(amount)));

        holderClientDetailPayments.txtDetailName.setText(clientDetail.getCantProduct()>0 ? String.format(ReportsApplication.getMyApplicationContext().getString(R.string.lbl_detail_name_client),String.valueOf(clientDetail.getCantProduct()),clientDetail.getConcept()) : clientDetail.getConcept());

        if(clientDetail.getUrlImage()!=null && !clientDetail.getUrlImage().isEmpty()){
          holderClientDetailPayments.img_product.setVisibility(View.VISIBLE);
          Glide.with(ReportsApplication.getMyApplicationContext()).load(clientDetail.getUrlImage()).into(holderClientDetailPayments.img_product);
        }else{
          holderClientDetailPayments.img_product.setVisibility(View.GONE);
        }

        holderClientDetailPayments.txtDetailName.setTextColor(clientDetail.isPay() ? ContextCompat.getColor(ReportsApplication.getMyApplicationContext(), android.R.color.holo_green_dark): ContextCompat.getColor(ReportsApplication.getMyApplicationContext(), android.R.color.holo_red_dark));
        holderClientDetailPayments.txt_amount.setTextColor(clientDetail.isPay() ? ContextCompat.getColor(ReportsApplication.getMyApplicationContext(), android.R.color.holo_green_dark): ContextCompat.getColor(ReportsApplication.getMyApplicationContext(), android.R.color.holo_red_dark));
        holderClientDetailPayments.txt_date.setText(Tools.getFormatDate(clientDetail.getDatePayment()));




  }

  @Override
  public int getItemCount() {
    return clientDetailsList==null ? 0 : clientDetailsList.size();
  }

  static class HolderClientDetailPayments extends RecyclerView.ViewHolder{
    private TextView txt_amount,txt_date,txtDetailName;
    private ImageView img_product;
    public HolderClientDetailPayments(@NonNull View itemView) {
      super(itemView);
      txt_amount = itemView.findViewById(R.id.lbl_client_amount);
      txt_date = itemView.findViewById(R.id.lbl_client_date);
      txtDetailName = itemView.findViewById(R.id.lbl_client_name);
      img_product = itemView.findViewById(R.id.img_concept);
    }
  }
}
