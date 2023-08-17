package com.epacheco.reports.view.orderView.orderDetailView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;
import com.epacheco.reports.R;
import com.epacheco.reports.tools.ReportsApplication;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AdapterOrdersDetail extends RecyclerView.Adapter<AdapterOrdersDetail.HolderOrder> {

    private List<OrderDetail> orderList;
    private onItemOrderDetailClic onItemOrderDetailClic;
    private onItemOrderBuy onItemOrderBuy;
    private OnClicListener itemClic;
    private onItemLocationOrder onItemLocationOrder;
    private onItemGenerateRout onItemGenerateRout;
    private Boolean isBuy;

    public interface OnClicListener {
        void onItemClick(OrderDetail order);
    }

    AdapterOrdersDetail(List<OrderDetail> orderList, Activity contexto) {
        this.orderList = orderList;
        this.itemClic = (OnClicListener) contexto;
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
        if (myOrder.isOrderBuy())
            holderOrder.containerOrderItem.setCardBackgroundColor(ContextCompat.getColor(ReportsApplication.getMyApplicationContext(), R.color.background_view));
        holderOrder.txtNameOrder.setText(myOrder.getOrderName());

        if (myOrder.getOrderDescription().isEmpty()) {
            holderOrder.txtDescOrder.setVisibility(View.GONE);
        } else {
            holderOrder.txtDescOrder.setText(myOrder.getOrderDescription() + "de " + myOrder.getOrderGender() + " color " + myOrder.getOrderColor() + " " + myOrder.getOrderSize() + ".");
        }

        if (myOrder.getOrderClient() == null) {
            holderOrder.txtClientName.setVisibility(View.GONE);
        } else {
            holderOrder.txtClientName.setText(myOrder.getOrderClient().getName());
        }

        holderOrder.imageViewRemoveItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clicItemList(true, myOrder.getOrderListId(), myOrder.getOrderId());

            }
        });
        holderOrder.btnMoveOrder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), R.string.msgChangeList, Toast.LENGTH_SHORT).show();
                itemClic.onItemClick(myOrder);
            }
        });
        holderOrder.containerOrderItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getOnItemOrderBuy() != null) {
                    myOrder.setOrderBuy(!myOrder.isOrderBuy());
                    getOnItemOrderBuy().onItemOrderClic(myOrder.getOrderListId(), myOrder.getOrderId(), myOrder);
                }
                clicItemList(false, myOrder.getOrderListId(), myOrder.getOrderId());
            }
        });

        holderOrder.btnGetLocation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getOnClickLocationOrder() != null) {
                    getOnClickLocationOrder().onItemLocataionOrderClic(myOrder);
                }
                Toast.makeText(view.getContext(), R.string.msgGetLocation, Toast.LENGTH_SHORT).show();
            }
        });

        if (myOrder.isOrderBuy()) {
            holderOrder.btnMoveOrder.setVisibility(View.GONE);
        } else {
            holderOrder.btnMoveOrder.setVisibility(View.VISIBLE);
        }

        if (myOrder.getOrderLocation() != null && myOrder.getOrderLocation().getLongitude() != null && myOrder.getOrderLocation().getLatitude() != null) {
            holderOrder.btnRoute.setVisibility(View.VISIBLE);
        } else {
            holderOrder.btnRoute.setVisibility(View.GONE);
        }


        holderOrder.btnRoute.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), R.string.msgGenerateRoute, Toast.LENGTH_SHORT).show();
                if (getOnClickGenerateRout() != null) {
                    getOnClickGenerateRout().onItemGenerateLocationClick(myOrder);
                }
            }
        });
    }


    private void clicItemList(boolean removeItem, String idOrder, String idItemOrder) {
        if (getOnItemOrderDetailClic() != null) {
            getOnItemOrderDetailClic().onItemOrderClic(removeItem, idOrder, idItemOrder);
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class HolderOrder extends RecyclerView.ViewHolder {
        private TextView txtNameOrder, txtDescOrder, txtClientName;
        private ImageView imageViewRemoveItem;
        private CardView containerOrderItem;
        private FloatingActionButton btnMoveOrder, btnGetLocation, btnRoute;
        //private AppCompatCheckBox checkBuyOrder;

        HolderOrder(@NonNull View itemView) {
            super(itemView);
            txtNameOrder = itemView.findViewById(R.id.lbl_order_name);
            txtClientName = itemView.findViewById(R.id.txtClientName);
            txtDescOrder = itemView.findViewById(R.id.lbl_order_descripcion);
            btnGetLocation = itemView.findViewById(R.id.btnGetUbication);
            btnRoute = itemView.findViewById(R.id.btnRoute);
            btnMoveOrder = itemView.findViewById(R.id.Imagen_mover_pedido);
            imageViewRemoveItem = itemView.findViewById(R.id.ImageView_delete_item);
            containerOrderItem = itemView.findViewById(R.id.cardView_container_item_order);
        }
    }

    public com.epacheco.reports.view.orderView.orderDetailView.onItemOrderDetailClic getOnItemOrderDetailClic() {
        return onItemOrderDetailClic;
    }

    public void setOnItemOrderDetailClic(
            com.epacheco.reports.view.orderView.orderDetailView.onItemOrderDetailClic onItemOrderDetailClic) {
        this.onItemOrderDetailClic = onItemOrderDetailClic;
    }

    public com.epacheco.reports.view.orderView.orderDetailView.onItemOrderBuy getOnItemOrderBuy() {
        return onItemOrderBuy;
    }

    public void setOnItemOrderBuy(
            com.epacheco.reports.view.orderView.orderDetailView.onItemOrderBuy onItemOrderBuy) {
        this.onItemOrderBuy = onItemOrderBuy;
    }

    public com.epacheco.reports.view.orderView.orderDetailView.onItemLocationOrder getOnClickLocationOrder() {
        return onItemLocationOrder;
    }

    public void setOnClickLocationOrder(
            com.epacheco.reports.view.orderView.orderDetailView.onItemLocationOrder onItemLocationOrder) {
        this.onItemLocationOrder = onItemLocationOrder;
    }

    public com.epacheco.reports.view.orderView.orderDetailView.onItemGenerateRout getOnClickGenerateRout() {
        return onItemGenerateRout;
    }

    public void setOnClickGenerateRoute(
            com.epacheco.reports.view.orderView.orderDetailView.onItemGenerateRout onItemGenerateRout) {
        this.onItemGenerateRout = onItemGenerateRout;
    }
}
