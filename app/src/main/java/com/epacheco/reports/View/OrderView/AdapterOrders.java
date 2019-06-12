package com.epacheco.reports.View.OrderView;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.epacheco.reports.Pojo.Order.OrderList;
import com.epacheco.reports.Pojo.Product.Product;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.ReportsApplication;
import com.epacheco.reports.View.ProductsView.ProductsView.onItemProductClic;
import java.util.List;

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
    holderOrder.txtNameOrder.setText(myOrder.getNameOrder());
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
    private TextView txtNameOrder;
    private ImageView imageViewRemoveItem;
    private CardView containerOrderItem;
    HolderOrder(@NonNull View itemView) {
      super(itemView);
      txtNameOrder = itemView.findViewById(R.id.lbl_order_name);
      imageViewRemoveItem= itemView.findViewById(R.id.ImageView_delete_item);
      containerOrderItem= itemView.findViewById(R.id.cardView_container_item_order);
    }
  }

  public com.epacheco.reports.View.OrderView.onItemOrderClic getOnItemOrderClic() {
    return onItemOrderClic;
  }

  public void setOnItemOrderClic(
      com.epacheco.reports.View.OrderView.onItemOrderClic onItemOrderClic) {
    this.onItemOrderClic = onItemOrderClic;
  }
}
