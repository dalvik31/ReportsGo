package com.epacheco.reports.tools;

import android.content.Intent;

import androidx.fragment.app.FragmentActivity;

import com.epacheco.reports.Pojo.Order.OrderList;
import com.epacheco.reports.view.clientView.clienDetailView.ClientDetailViewClass;
import com.epacheco.reports.view.clientView.clientAddView.ClientAddViewClass;
import com.epacheco.reports.view.clientView.clientDetailListView.ClientDetailListViewClass;
import com.epacheco.reports.view.clientView.clientView.ClientsViewClass;
import com.epacheco.reports.view.financeActivityView.FinanceAcitivityView;
import com.epacheco.reports.view.forgotPassword.ForgotPasswordAcitivity;
import com.epacheco.reports.view.mainAcitivityView.MainActivityViewClass;
import com.epacheco.reports.view.orderView.orderCreateView.OrderCreateView;
import com.epacheco.reports.view.orderView.orderDetailView.OrderDetailView;
import com.epacheco.reports.view.orderView.OrderViewClass;
import com.epacheco.reports.view.productsView.productAddView.ProductAddViewClass;
import com.epacheco.reports.view.productsView.productsView.ProductViewClass;
import com.epacheco.reports.view.productsView.scanCode.ScannedBarcodeActivity;
import com.epacheco.reports.view.profileView.ProfileViewClass;
import com.epacheco.reports.view.registerUserView.RegisterUserViewClass;
import com.epacheco.reports.view.saleView.SaleViewClass;
import com.epacheco.reports.view.searchElementsView.SearchElementView;

import java.util.ArrayList;

public class ScreenManager {
  public static void goRegisterActivity(FragmentActivity myActivity){
    Intent registerActivity = new Intent(myActivity, RegisterUserViewClass.class);
    registerActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    registerActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    registerActivity.putExtra("EXIT", true);
    myActivity.startActivity(registerActivity);
  }


  public static void goAddClientActivity(FragmentActivity myActivity, String idClient){
    Intent addClientActivity = new Intent(myActivity, ClientAddViewClass.class);
    addClientActivity.putExtra(ClientAddViewClass.CLIENT_ID,idClient);
    myActivity.startActivity(addClientActivity);
  }

  public static void goDetailClientActivity(FragmentActivity myActivity, String idClient){
    Intent detailClientActivity = new Intent(myActivity, ClientDetailViewClass.class);
    detailClientActivity.putExtra(ClientDetailViewClass.CLIENT_ID,idClient);
    myActivity.startActivity(detailClientActivity);
  }

  public static void goDetailListClientActivity(FragmentActivity myActivity, String idClient, String clientDebt){
    Intent detailClientListActivity = new Intent(myActivity, ClientDetailListViewClass.class);
    detailClientListActivity.putExtra(ClientDetailListViewClass.CLIENT_ID,idClient);
    detailClientListActivity.putExtra(ClientDetailListViewClass.CLIENT_DEBP,clientDebt);
    myActivity.startActivity(detailClientListActivity);
  }

  public static void goProfileActivity(FragmentActivity myActivity){
    Intent profileActivity = new Intent(myActivity, ProfileViewClass.class);
    myActivity.startActivity(profileActivity);
  }
  public static void goMainActivity(FragmentActivity myActivity){
    Intent mainActivity = new Intent(myActivity, MainActivityViewClass.class);
    myActivity.startActivity(mainActivity);
  }

  public static void goAddProductActivity(FragmentActivity myActivity, String productId){
    Intent addProductActivity = new Intent(myActivity, ProductAddViewClass.class);
    addProductActivity.putExtra(ProductAddViewClass.PRODUCT_ID,productId);
    myActivity.startActivity(addProductActivity);
  }

  public static void goSearchActivity(FragmentActivity myActivity, int fromSearch){
    Intent searchActivity = new Intent(myActivity, SearchElementView.class);
    searchActivity.putExtra(SearchElementView.FROM_SEARCH,fromSearch);
    myActivity.startActivityForResult(searchActivity,fromSearch);
  }

  public static void goClientsActivity(FragmentActivity myActivity, boolean isSearch){
    Intent clientActivity = new Intent(myActivity, ClientsViewClass.class);
    clientActivity.putExtra(ClientsViewClass.IS_SEARCH,isSearch);
    myActivity.startActivityForResult(clientActivity,ClientsViewClass.CLIENT_SELECTED);
  }
  public static void goProductsActivity(FragmentActivity myActivity, boolean isSearch){
    Intent productActivity = new Intent(myActivity, ProductViewClass.class);
    productActivity.putExtra(ProductViewClass.IS_SEARCH,isSearch);
    myActivity.startActivityForResult(productActivity,ProductViewClass.PRODUCT_SELECTED);
  }
  public static void goSaleActivity(FragmentActivity myActivity,String clientId,String productId){
    Intent saleActivity = new Intent(myActivity, SaleViewClass.class);
    saleActivity.putExtra(ClientAddViewClass.CLIENT_ID ,clientId);
    saleActivity.putExtra(ProductAddViewClass.PRODUCT_ID ,productId);
    myActivity.startActivity(saleActivity);
  }

  public static void goScanActivity(FragmentActivity myActivity){
    Intent scanActivity = new Intent(myActivity,ScannedBarcodeActivity.class);
    myActivity.startActivityForResult(scanActivity,ScannedBarcodeActivity.SCANBAR_ACTIVITY);
  }

  public static void goOrderActivity(FragmentActivity myActivity,String clientId){
    //Intent orderActivity = new Intent(myActivity, TestPin.class);
    //myActivity.startActivity(orderActivity);
   Intent orderActivity = new Intent(myActivity, OrderViewClass.class);
    if(clientId!=null)orderActivity.putExtra(ClientAddViewClass.CLIENT_ID ,clientId);
    myActivity.startActivity(orderActivity);
  }

  public static void goOrderActivityProduct(FragmentActivity myActivity,String productId){
    Intent orderActivity = new Intent(myActivity, OrderViewClass.class);
    if(productId!=null)orderActivity.putExtra(ProductAddViewClass.PRODUCT_ID ,productId);
    myActivity.startActivity(orderActivity);
  }





  public static void goOrderDetailActivity(FragmentActivity myActivity, String orderId, String nameOrder, String idClient, ArrayList<OrderList> myList){
    Intent orderDetailActivity = new Intent(myActivity, OrderDetailView.class);
    orderDetailActivity.putExtra(OrderDetailView.ORDER_ID,orderId);
    orderDetailActivity.putExtra(OrderDetailView.ORDER_NAME,nameOrder);
    orderDetailActivity.putParcelableArrayListExtra(OrderViewClass.ORDERLIST, myList);
    if(idClient!=null)orderDetailActivity.putExtra(OrderDetailView.CLIENT_ID,idClient);
    myActivity.startActivity(orderDetailActivity);

  }

  public static void goOrderDetailActivityProduct(FragmentActivity myActivity,String orderId,String nameOrder,String idProduct){
    Intent orderDetailActivity = new Intent(myActivity, OrderDetailView.class);
    orderDetailActivity.putExtra(OrderDetailView.ORDER_ID,orderId);
    orderDetailActivity.putExtra(OrderDetailView.ORDER_NAME,nameOrder);
    if(idProduct!=null)orderDetailActivity.putExtra(OrderDetailView.PRODUCT_ID,idProduct);
    myActivity.startActivity(orderDetailActivity);
    myActivity.finish();
  }

  public static void goOrderCreateActivity(FragmentActivity myActivity,String orderId,String idClient){
    Intent orderCreateActivity = new Intent(myActivity, OrderCreateView.class);
    orderCreateActivity.putExtra(OrderDetailView.ORDER_ID,orderId);
    if(idClient!=null)orderCreateActivity.putExtra(OrderDetailView.CLIENT_ID,idClient);
    myActivity.startActivity(orderCreateActivity);

  }

  public static void goOrderCreateActivityProduct(FragmentActivity myActivity,String orderId,String idProduct){
    Intent orderCreateActivity = new Intent(myActivity, OrderCreateView.class);
    orderCreateActivity.putExtra(OrderDetailView.ORDER_ID,orderId);
    if(idProduct!=null)orderCreateActivity.putExtra(OrderDetailView.PRODUCT_ID,idProduct);
    myActivity.startActivity(orderCreateActivity);
    myActivity.finish();
  }

  public static void goForgotPasswordActivity(FragmentActivity myActivity){
    Intent forgotPasswordActivity = new Intent(myActivity, ForgotPasswordAcitivity.class);
    myActivity.startActivity(forgotPasswordActivity);
  }

  public static void goFinanceActivity(FragmentActivity myActivity){
    Intent forgotPasswordActivity = new Intent(myActivity, FinanceAcitivityView.class);
    myActivity.startActivity(forgotPasswordActivity);
  }
}
