package com.epacheco.reports.view.orderView.orderDetailView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.epacheco.reports.Model.OrderModel.OrderDetailModel.OrderDetailModelClass;
import com.epacheco.reports.Model.OrderModel.OrderModelClass;
import com.epacheco.reports.Pojo.Order.OrderList;
import com.epacheco.reports.Pojo.OrderDetail.OrderDetail;
import com.epacheco.reports.R;
import com.epacheco.reports.tools.ReportsApplication;
import com.epacheco.reports.tools.ReportsDialogGlobal;
import com.epacheco.reports.tools.ReportsProgressDialog;
import com.epacheco.reports.tools.ScreenManager;
import com.epacheco.reports.databinding.ActivityOrderDetailViewBinding;
import com.epacheco.reports.view.orderView.OrderViewClass;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailView extends AppCompatActivity implements AdapterOrdersDetail.OnClicListener, OrderDetailInterface, onItemOrderDetailClic, onItemOrderBuy {

  private final String TAG = OrderDetailView.class.getSimpleName();
  public static final String ORDER_ID = "orderId";
  public static final String ORDER_NAME = "orderName";
  public static final String CLIENT_ID = "clientId";
  public static final String PRODUCT_ID = "productId";


  private ActivityOrderDetailViewBinding binding;
  private OrderModelClass orderModelClass;
  private FirebaseAuth mAuth;
  private String listOrderId;
  private ReportsProgressDialog progressbar;
  private OrderDetailModelClass orderDetailModelClass;
  private String clientId;
  private String productId;
  private Bundle extras;
  private ArrayList<OrderList> myArrayList;
  private ListView List_move_order;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(OrderDetailView.this, R.layout.activity_order_detail_view);
    initElements();
  }

  private void initElements() {
    mAuth = FirebaseAuth.getInstance();
    progressbar = ReportsProgressDialog.getInstance();
    orderDetailModelClass = new OrderDetailModelClass(this);
    extras = getIntent().getExtras();
    if (extras != null) {
      myArrayList = extras.getParcelableArrayList(OrderViewClass.ORDERLIST);
      binding.appBarLayout.setTitle(extras.getString(ORDER_NAME));
      listOrderId = extras.getString(ORDER_ID);
      showProgress(getString(R.string.msg_search_orders_details));
      orderDetailModelClass.getDetailOrderList(listOrderId);
      Log.e("Array ", "list : " + myArrayList);

    }

  }


  @Override
  public void onStart() {
    super.onStart();
    if (mAuth.getCurrentUser() != null) {
      if (mAuth.getCurrentUser().getPhotoUrl() != null) {
        Glide.with(ReportsApplication.getMyApplicationContext()).load(
                com.epacheco.reports.tools.Tools.getFormatUrlImage(mAuth.getCurrentUser().getPhotoUrl())) .apply(RequestOptions.circleCropTransform()).into(binding.appBarLayout.getImageView());
      }
    }
  }

  public void goCreateOrder(View view) {
    ScreenManager.goOrderCreateActivity(this, listOrderId, null);
  }

  @Override
  public FragmentActivity getMyActivity() {
    return this;
  }

  @Override
  public void successGetListDetail(List<OrderDetail> orderDetailList) {
    hideProgress();
    if (orderDetailList.size() > 0) {
      binding.lblZeroOrdersElements.setVisibility(View.GONE);
      binding.imgEmptyOrders.setVisibility(View.GONE);
      binding.recyclerListOrderElelments.setVisibility(View.VISIBLE);
      binding.recyclerListOrderElelments.setHasFixedSize(true);
      binding.recyclerListOrderElelments.setLayoutManager(new LinearLayoutManager(this));
      AdapterOrdersDetail adapterOrders = new AdapterOrdersDetail(orderDetailList, this);
      adapterOrders.setOnItemOrderDetailClic(this);
      adapterOrders.setOnItemOrderBuy(this);
      binding.recyclerListOrderElelments.setAdapter(adapterOrders);
    } else {
      binding.lblZeroOrdersElements.setVisibility(View.VISIBLE);
      binding.imgEmptyOrders.setVisibility(View.VISIBLE);
      binding.recyclerListOrderElelments.setVisibility(View.GONE);
    }
    checkIdClient();
  }


  @Override
  public void errorGetListDetail(String error) {
    hideProgress();
    binding.recyclerListOrderElelments.removeAllViews();
    binding.lblZeroOrdersElements.setVisibility(View.VISIBLE);
    binding.imgEmptyOrders.setVisibility(View.VISIBLE);
    checkIdClient();
  }

  @Override
  public void successOrderBuyElement() {
    hideProgress();
    com.epacheco.reports.tools.Tools.showToasMessage(this, getString(R.string.lbl_order_buy_update));
  }

  @Override
  public void errorOrderBuyElement(String error) {
    hideProgress();
    com.epacheco.reports.tools.Tools.showToasMessage(this, error);
  }

  @Override
  public void successremoveOrderDetail() {
    hideProgress();
    com.epacheco.reports.tools.Tools.showToasMessage(this, getString(R.string.msg_item_order_removed));
  }

  @Override
  public void errorremoveOrderDetail(String error) {
    hideProgress();
    com.epacheco.reports.tools.Tools.showToasMessage(this, error);
  }




  @Override
  public void successMoveOrder() {
    hideProgress();
    com.epacheco.reports.tools.Tools.showToasMessage(this, getString(R.string.msg_item_order_move));
  }

  @Override
  public void errorMoveOrder(String error) {
    hideProgress();
    com.epacheco.reports.tools.Tools.showToasMessage(this, error);
  }




  private void showProgress(String message) {
    progressbar.showProgress(this, message);
    binding.progressDownloadOrderElements.setVisibility(View.VISIBLE);
  }

  private void hideProgress() {
    progressbar.hideProgress();
    binding.progressDownloadOrderElements.setVisibility(View.GONE);
  }

  @Override
  public void onItemOrderClic(boolean removeElement, final String orderId, final String orderItemId) {
    if (removeElement) {
      ReportsDialogGlobal.showDialogAccept(this, getString(R.string.title_message_delete_elemnt),
              getString(R.string.body_message_delete_elemnt),
              new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  orderDetailModelClass.removeOrderDetail(orderId, orderItemId);
                }
              }
      );

    }
  }

  @Override
  public void onItemOrderClic(String orderId, String orderItemId, OrderDetail orderDetail) {
    orderDetailModelClass.orderItemBuy(orderId, orderItemId, orderDetail);
  }

  public void goProfileActivity(View v) {
    ScreenManager.goProfileActivity(this);
  }


  private void checkIdClient() {
    if (extras != null && extras.containsKey(CLIENT_ID)) {
      clientId = extras.getString(CLIENT_ID);
      Log.e(TAG, "clientId: " + clientId);
      ScreenManager.goOrderCreateActivity(this, listOrderId, clientId);
      extras.remove(CLIENT_ID);
    } else if (extras != null && extras.containsKey(PRODUCT_ID)) {
      productId = extras.getString(PRODUCT_ID);
      Log.e(TAG, "productId :  " + productId);
      ScreenManager.goOrderCreateActivityProduct(this, listOrderId, productId);
      extras.remove(OrderDetailView.PRODUCT_ID);
    } else {
      Log.e(TAG, "extra nulo");
    }
  }

  @Override
  public void onItemClick(OrderDetail order) {
    createMoveDialogo(order);
  }

  public AlertDialog createMoveDialogo(OrderDetail orderDetail) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    LayoutInflater inflater = this.getLayoutInflater();
    View v = inflater.inflate(R.layout.layout_list_move_order, null);
    ArrayAdapter<OrderList> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myArrayList);
    List_move_order = v.findViewById(R.id.List_move_order);
    List_move_order.setAdapter(adapter);

    List_move_order.setOnItemClickListener((parent, view, position, id) -> {

      ReportsDialogGlobal.showDialogAcceptAnCancel(this, "Mover Pedido", "¿ Deceas mover el pedido a esta lista ?",
              new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          orderDetailModelClass.moveOrder(adapter.getItem(position).getDateOrder(),orderDetail);
          finish();
        }
      }, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
      });

      Log.e(TAG, "getNameOrder :  " + adapter.getItem(position).getNameOrder());


    });
    builder.setView(v);
    builder.setTitle("Mover Pedido");
    builder.setMessage("Selecciona la lista a la cual quieres mover el pedido.");
    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {

      }
    });


    return  builder.show();
  }


}
