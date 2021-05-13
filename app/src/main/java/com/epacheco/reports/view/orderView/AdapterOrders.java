package com.epacheco.reports.view.orderView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.epacheco.reports.Pojo.Order.OrderList;
import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;
import com.epacheco.reports.R;
import com.google.android.material.progressindicator.ProgressIndicator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class AdapterOrders extends RecyclerView.Adapter<AdapterOrders.HolderOrder>{

  private List<OrderList> orderList;
  private onItemOrderClic onItemOrderClic;
  private int imageResourses;
  private String mes;


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

  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  public void onBindViewHolder(@NonNull HolderOrder holderOrder, int i) {
    final OrderList myOrder = orderList.get(i);
    holderOrder.txttitle.setText(myOrder.getNameOrder());
    holderOrder.txtNameOrder.setText(myOrder.getMsjOrder());

    if(!TextUtils.isEmpty(myOrder.getDateOrder())){
      long date = Long.parseLong(myOrder.getDateOrder());
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(date);
      int month = calendar.get(Calendar.MONTH);
      getStationImage(month);
    }

    holderOrder.relativItemOrder.setBackgroundResource(imageResourses);
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
    int countOrders = 0;

    if (myOrder.getOrderLists() != null) {
      holderOrder.progressIndicator.setMax(myOrder.getOrderLists().size());
      for (Map.Entry<String, OrderDetail> entry : myOrder.getOrderLists().entrySet()) {
        if (((OrderDetail) entry.getValue()).isOrderBuy()) {
          countOrders++;
        }
      }
      holderOrder.progressIndicator.setProgress(countOrders);
    }

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
    private final ProgressIndicator progressIndicator;
    HolderOrder(@NonNull View itemView) {
      super(itemView);
      relativItemOrder = itemView.findViewById(R.id.relativItemOrder);
      txttitle = itemView.findViewById(R.id.txttitle);
      txtNameOrder = itemView.findViewById(R.id.lbl_order_name);
      imageViewRemoveItem= itemView.findViewById(R.id.ImageView_delete_item);
      containerOrderItem= itemView.findViewById(R.id.cardView_container_item_order);
      progressIndicator = itemView.findViewById(R.id.progressOrder);

    }
  }

  public com.epacheco.reports.view.orderView.onItemOrderClic getOnItemOrderClic() {
    return onItemOrderClic;
  }

  public void setOnItemOrderClic(
          com.epacheco.reports.view.orderView.onItemOrderClic onItemOrderClic) {
    this.onItemOrderClic = onItemOrderClic;
  }


  private void getStationImage(int mesActual){

    if(mesActual < 5 && mesActual > 1){
      getImagePrimavera();
    }else if(mesActual > 4 && mesActual < 8){
      getImageVerano();
    }else if(mesActual > 7 && mesActual < 12){
      getImageOtonio();
    }else{
      getImageInvierno();
    }
  }

  private void getImageInvierno() {

    int [] imageInvierno = new int[4];
    imageInvierno[0] = R.drawable.imagen_invierno1;
    imageInvierno[1] = R.drawable.imagen_invierno2;
    imageInvierno[2] = R.drawable.imagen_invierno3;
    imageInvierno[3] = R.drawable.imagen_invierno4;
    int min = 0;
    int max = 3;
    final int random = new Random().nextInt((max - min) + 1) + min;
    imageResourses = imageInvierno[random];


  }

  private void getImageOtonio() {

    int [] imageOtoño = new int[4];
    imageOtoño[0] = R.drawable.imagen_otonio1;
    imageOtoño[1] = R.drawable.imagen_otonio2;
    imageOtoño[2] = R.drawable.imagen_otonio3;
    imageOtoño[3] = R.drawable.imagen_otonio4;
    int min = 0;
    int max = 3;
    final int random = new Random().nextInt((max - min) + 1) + min;
    imageResourses = imageOtoño[random];

  }

  private void getImagePrimavera(){

    int [] imagePrimavera = new int[4];
    imagePrimavera[0] = R.drawable.imagen_primavera1;
    imagePrimavera[1] = R.drawable.imagen_primavera2;
    imagePrimavera[2] = R.drawable.imagen_primavera3;
    imagePrimavera[3] = R.drawable.imagen_primavera4;
    int min = 0;
    int max = 3;
    final int random = new Random().nextInt((max - min) + 1) + min;
    imageResourses = imagePrimavera[random];
  }

  private void getImageVerano(){

    int [] imageVerano = new int[4];
    imageVerano[0] = R.drawable.imagen_verano1;
    imageVerano[1] = R.drawable.imagen_verano2;
    imageVerano[2] = R.drawable.imagen_verano3;
    imageVerano[3] = R.drawable.imagen_verano4;
    int min = 0;
    int max = 3;
    final int random = new Random().nextInt((max - min) + 1) + min;
    imageResourses = imageVerano[random];
  }




}
