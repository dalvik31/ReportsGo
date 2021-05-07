package com.epacheco.reports.view.orderView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.epacheco.reports.Pojo.Order.OrderList;
import com.epacheco.reports.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AdapterOrders extends RecyclerView.Adapter<AdapterOrders.HolderOrder>{

  private List<OrderList> orderList;
  private onItemOrderClic onItemOrderClic;


   AdapterOrders(List<OrderList> orderList) {
    this.orderList = orderList;
  }

  @NonNull
  @Override
  public HolderOrder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_list_order, viewGroup, false);
    return new HolderOrder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull HolderOrder holderOrder, int i) {
    final OrderList myOrder = orderList.get(i);
    holderOrder.relativItemOrder.setBackgroundResource(myOrder.getImageStationbackground());
    holderOrder.txttitle.setText(myOrder.getNameOrder());
    holderOrder.txtNameOrder.setText(myOrder.getMsjOrder());
    holderOrder.imageViewRemoveItem.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        clicItemList(true,myOrder.getDateOrder(),myOrder.getNameOrder());

      }
    });
    holderOrder.containerOrderItem.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        clicItemList(false,myOrder.getDateOrder(),myOrder.getNameOrder());
      }
    });
  }


  private void clicItemList(boolean removeItem, String idOrder,String orderName){
    if(getOnItemOrderClic()!=null){
      getOnItemOrderClic().onItemOrderClic(removeItem,idOrder,orderName);
    }
  }
  @Override
  public int getItemCount() {
    return orderList.size();
  }

  class HolderOrder extends RecyclerView.ViewHolder{
    private TextView txtNameOrder , txttitle;
    private ImageView imageViewRemoveItem;
    private CardView containerOrderItem;
    private RelativeLayout relativItemOrder;
    HolderOrder(@NonNull View itemView) {
      super(itemView);
      relativItemOrder = itemView.findViewById(R.id.relativItemOrder);
      txttitle = itemView.findViewById(R.id.txttitle);
      txtNameOrder = itemView.findViewById(R.id.lbl_order_name);
      imageViewRemoveItem= itemView.findViewById(R.id.ImageView_delete_item);
      containerOrderItem= itemView.findViewById(R.id.cardView_container_item_order);
    }
  }

  public com.epacheco.reports.view.orderView.onItemOrderClic getOnItemOrderClic() {
    return onItemOrderClic;
  }

  public void setOnItemOrderClic(
      com.epacheco.reports.view.orderView.onItemOrderClic onItemOrderClic) {
    this.onItemOrderClic = onItemOrderClic;
  }




}
