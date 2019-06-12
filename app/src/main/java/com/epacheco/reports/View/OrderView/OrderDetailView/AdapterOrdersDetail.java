package com.epacheco.reports.View.OrderView.OrderDetailView;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.epacheco.reports.Pojo.Order.OrderList;
import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.ReportsApplication;
import com.epacheco.reports.View.OrderView.onItemOrderClic;
import java.util.List;

public class AdapterOrdersDetail extends RecyclerView.Adapter<AdapterOrdersDetail.HolderOrder>{

  private List<OrderDetail> orderList;
  private onItemOrderDetailClic onItemOrderDetailClic;
  private onItemOrderBuy onItemOrderBuy;

   AdapterOrdersDetail(List<OrderDetail> orderList) {
    this.orderList = orderList;
  }

  @NonNull
  @Override
  public HolderOrder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_list_order_detail, viewGroup, false);
    return new HolderOrder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull HolderOrder holderOrder, int i) {
    final OrderDetail myOrder = orderList.get(i);
    if(myOrder.isOrderBuy()) holderOrder.containerOrderItem.setCardBackgroundColor(ContextCompat.getColor(ReportsApplication.getMyApplicationContext(),R.color.colorGreenTransparent));
    holderOrder.txtNameOrder.setText(myOrder.getOrderName());
    holderOrder.txtSizeOrder.setText(myOrder.getOrderSize());
    holderOrder.txtGenderOrder.setText(myOrder.getOrderGender());
    holderOrder.txtClientOrder.setText(myOrder.getOrderClient()!=null ? myOrder.getOrderClient().getName():"Desconocido");
    holderOrder.txtDescOrder.setText(myOrder.getOrderDescription());

    holderOrder.imageViewRemoveItem.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        clicItemList(true,myOrder.getOrderListId(),myOrder.getOrderId());

      }
    });
    holderOrder.containerOrderItem.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        clicItemList(false,myOrder.getOrderListId(),myOrder.getOrderId());
      }
    });
    holderOrder.checkBuyOrder.setChecked(myOrder.isOrderBuy());
    holderOrder.checkBuyOrder.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(getOnItemOrderBuy()!=null){
          myOrder.setOrderBuy(!myOrder.isOrderBuy());
          getOnItemOrderBuy().onItemOrderClic(myOrder.getOrderListId(),myOrder.getOrderId(),myOrder);
        }
      }
    });
  }


  private void clicItemList(boolean removeItem, String idOrder,String idItemOrder){
    if(getOnItemOrderDetailClic()!=null){
      getOnItemOrderDetailClic().onItemOrderClic(removeItem,idOrder,idItemOrder);
    }
  }
  @Override
  public int getItemCount() {
    return orderList.size();
  }

  class HolderOrder extends RecyclerView.ViewHolder{
    private TextView txtNameOrder,txtSizeOrder,txtGenderOrder,txtDescOrder,txtClientOrder;
    private ImageView imageViewRemoveItem;
    private CardView containerOrderItem;
    private AppCompatCheckBox checkBuyOrder;
    HolderOrder(@NonNull View itemView) {
      super(itemView);
      txtNameOrder = itemView.findViewById(R.id.lbl_order_name);
      txtSizeOrder = itemView.findViewById(R.id.lbl_order_size);
      txtGenderOrder = itemView.findViewById(R.id.lbl_order_gender);
      txtDescOrder = itemView.findViewById(R.id.lbl_order_descripcion);
      checkBuyOrder = itemView.findViewById(R.id.AppCompatCheckBox_buy_order);
      txtClientOrder = itemView.findViewById(R.id.lbl_order_client);
      imageViewRemoveItem= itemView.findViewById(R.id.ImageView_delete_item);
      containerOrderItem= itemView.findViewById(R.id.cardView_container_item_order);
    }
  }


  public com.epacheco.reports.View.OrderView.OrderDetailView.onItemOrderDetailClic getOnItemOrderDetailClic() {
    return onItemOrderDetailClic;
  }

  public void setOnItemOrderDetailClic(
      com.epacheco.reports.View.OrderView.OrderDetailView.onItemOrderDetailClic onItemOrderDetailClic) {
    this.onItemOrderDetailClic = onItemOrderDetailClic;
  }

  public com.epacheco.reports.View.OrderView.OrderDetailView.onItemOrderBuy getOnItemOrderBuy() {
    return onItemOrderBuy;
  }

  public void setOnItemOrderBuy(
      com.epacheco.reports.View.OrderView.OrderDetailView.onItemOrderBuy onItemOrderBuy) {
    this.onItemOrderBuy = onItemOrderBuy;
  }
}
