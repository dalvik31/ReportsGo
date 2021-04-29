package com.epacheco.reports.view.saleView;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.epacheco.reports.Model.SaleModel.SaleModelClass;
import com.epacheco.reports.Pojo.Client.Client;
import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;
import com.epacheco.reports.Pojo.Product.Product;
import com.epacheco.reports.R;
import com.epacheco.reports.tools.ReportsApplication;
import com.epacheco.reports.tools.ReportsDialogGlobal;
import com.epacheco.reports.tools.ReportsProgressDialog;
import com.epacheco.reports.tools.ScreenManager;
import com.epacheco.reports.view.clientView.clientAddView.ClientAddViewClass;
import com.epacheco.reports.view.clientView.clientView.ClientsViewClass;
import com.epacheco.reports.view.productsView.productAddView.ProductAddViewClass;
import com.epacheco.reports.view.productsView.productsView.ProductViewClass;
import com.epacheco.reports.databinding.ActivitySaleViewClass2Binding;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.List;

public class SaleViewClass extends AppCompatActivity implements SaleViewInterface, getTotalSale{

  private SaleModelClass saleModelClass;
  private ActivitySaleViewClass2Binding binding;
  private AdapterViewPagerSale adapterViewPagerSale = null;
  private double totalSale;
  private double debtClient;
  private Client objClient;
  private FirebaseAuth mAuth;
  private String clientId;
  private ReportsProgressDialog progressbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_sale_view_class2);
    inicalizateElements();
  }

  private void inicalizateElements() {
    mAuth = FirebaseAuth.getInstance();
    progressbar = ReportsProgressDialog.getInstance();
    adapterViewPagerSale = new AdapterViewPagerSale();
    binding.viewPagerProducts.setAdapter(adapterViewPagerSale);
    saleModelClass = new SaleModelClass(this);
    getTotalSale(0,false);
    if(getIntent()!=null && getIntent().getStringExtra(ClientAddViewClass.CLIENT_ID)!=null && !getIntent().getStringExtra(ClientAddViewClass.CLIENT_ID).isEmpty()){
      clientId = getIntent().getStringExtra(ClientAddViewClass.CLIENT_ID);
      showProgress(getString(R.string.lbl_search_clients));
      saleModelClass.getCLient(clientId);
    }else if(getIntent()!=null && getIntent().getStringExtra(ProductAddViewClass.PRODUCT_ID)!=null && !getIntent().getStringExtra(ProductAddViewClass.PRODUCT_ID).isEmpty()){
      showProgress(getString(R.string.msg_search_products));
      saleModelClass.getProduct(getIntent().getStringExtra(ProductAddViewClass.PRODUCT_ID));
    }
  }

  public void addView(Product product) {
   if(adapterViewPagerSale!=null && adapterViewPagerSale.getProductList()!=null && adapterViewPagerSale.getProductList().size()>0 && adapterViewPagerSale.getProductList().contains(product)){
      com.epacheco.reports.tools.Tools.showToasMessage(this,getString(R.string.lbl_stock_product_exist));
      return;
    }
    LayoutInflater inflater = getLayoutInflater();
    RelativeLayout v0 = (RelativeLayout) inflater.inflate (R.layout.item_product_sale, null);
    int position = adapterViewPagerSale.getCount()-1;
    adapterViewPagerSale.setGetTotalSale(this);
    adapterViewPagerSale.addView (v0, position+1,product);
    adapterViewPagerSale.notifyDataSetChanged();
    binding.viewPagerProducts.setCurrentItem(position+1);
  }

  public void goClientsActivity(View v) {
    ScreenManager.goClientsActivity(this, true);
  }

  public void goProductsActivity(View v) {
    ScreenManager.goProductsActivity(this, true);
  }

  @Override
  public FragmentActivity getMyActivity() {
    return this;
  }

  @Override
  public void successGetClient(Client client) {
    hideProgress();
    binding.btnNameClient.setText(String.format(ReportsApplication.getMyApplicationContext().getString(R.string.txt_client_name_format), client.getName(), client.getLastNanme()));
    setObjClient(client);
    saleModelClass.getClientDetail(getObjClient().getId());
    binding.txtSaleClientName.setVisibility(View.VISIBLE);
  }

  @Override
  public void errrorGetClient(String error) {
    hideProgress();
    com.epacheco.reports.tools.Tools.showToasMessage(this,error);
  }

  @Override
  public void successGetProduct(Product product) {
    hideProgress();
    addView(product);
    binding.lblMsgTicketEmpty.setVisibility(View.GONE);
    binding.viewPagerProducts.setVisibility(View.VISIBLE);
    binding.txtSaleProductName.setVisibility(View.VISIBLE);
    binding.btnNameProduct.setText(String.format(
        ReportsApplication.getMyApplicationContext().getString(R.string.txt_product_name_and_code_format),
        product.getProductName(), String.valueOf(product.getProductCode())));
   /* binding.lblNameProduct.setText(product.getProductName());
    binding.lblPriceProduct.setText(String.format(ReportsApplication.getMyApplicationContext().getString(R.string.txt_client_amount_format),String.valueOf(product.getProductPriceSale())));
    binding.lblCodeProduct.setText(String.valueOf(product.getProductCode()));
    Glide.with(ReportsApplication.getMyApplicationContext()).load(product.getUrlImage()).into(binding.imgProduct);
*/
  }

  @Override
  public void errorGetProduct(String error) {
    hideProgress();
    com.epacheco.reports.tools.Tools.showToasMessage(this,error);
  }

  @Override
  public void successAddClientDetail() {
    hideProgress();
    if(adapterViewPagerSale!=null){
      adapterViewPagerSale.removeAllView(binding.viewPagerProducts);

      com.epacheco.reports.tools.Tools.showToasMessage(this,getString(R.string.msg_client_detail_created));
      setTotalSale(0);
      binding.btnTotal.setText(String.format(getString(R.string.lbl_sale_total),String.valueOf(getTotalSale())));
      binding.lblMsgTicketEmpty.setVisibility(View.VISIBLE);
      binding.viewPagerProducts.setVisibility(View.GONE);
      binding.txtSaleClientName.setVisibility(View.GONE);
      binding.txtSaleProductName.setVisibility(View.GONE);
      binding.btnNameClient.setText(getString(R.string.lbl_sale_select_name));
      binding.btnNameProduct.setText(getString(R.string.lbl_sale_select_product));
    }


  }

  @Override
  public void errorAddClientDetail(String error) {
    hideProgress();
    com.epacheco.reports.tools.Tools.showToasMessage(this,error);
  }

  @Override
  public void successGetClientDetail(ClientDetail clientDetail) {
    setDebtClient(clientDetail.getDebt());
    binding.btnNameClient.setText(String.format(ReportsApplication.getMyApplicationContext().getString(R.string.txt_product_name_and_debt_format), getObjClient().getName(), getObjClient().getLastNanme(),String.valueOf(getDebtClient())));
  }

  @Override
  public void errorGetClientDetail(String error) {
    hideProgress();
    if(error!=null && !error.isEmpty()){
      com.epacheco.reports.tools.Tools.showToasMessage(this,error);
     }else{
      binding.btnNameClient.setText(String.format(ReportsApplication.getMyApplicationContext().getString(R.string.txt_product_name_and_debt_format), getObjClient().getName(), getObjClient().getLastNanme(),String.valueOf(0)));

    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == ProductViewClass.PRODUCT_SELECTED && resultCode == RESULT_OK) {
      if (data == null) {
        return;
      }

      saleModelClass.getProduct(data.getStringExtra(ProductAddViewClass.PRODUCT_ID));

    }
    if (requestCode == ClientsViewClass.CLIENT_SELECTED && resultCode == RESULT_OK) {
      if (data == null) {
        return;
      }

      saleModelClass.getCLient(data.getStringExtra(ClientAddViewClass.CLIENT_ID));
    }

  }

  public void removeProduct(View view){
    ReportsDialogGlobal.showDialogAccept(this, getString(R.string.title_message_delete_elemnt),
        getString(R.string.body_message_delete_elemnt),
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            if(adapterViewPagerSale!=null && adapterViewPagerSale.getCount()>0){
              adapterViewPagerSale.removeView(binding.viewPagerProducts,binding.viewPagerProducts.getCurrentItem());
            }else{
              Toast.makeText(SaleViewClass.this,getString(R.string.msg_sale_empty_products),Toast.LENGTH_LONG).show();
            }
          }
        }
    );


  }

  public void generateTicket(View view){
    if(adapterViewPagerSale!=null && adapterViewPagerSale.getCount()>0){
      showProgress(getString(R.string.msg_generate_sale));
      List<ClientDetail> listDetail = new ArrayList<>(adapterViewPagerSale.getCount());
      for(Product product: adapterViewPagerSale.getProductList()){
        ClientDetail clientDetail = new ClientDetail();
        clientDetail.setDatePayment(String.valueOf(System.currentTimeMillis()));
        clientDetail.setDebt(getDebtClient() + getTotalSale());
        clientDetail.setCantProduct(product.getAuxStock());
        clientDetail.setUrlImage(product.getUrlImage());
        clientDetail.setAmount(product.getAuxPrice());
        clientDetail.setProductId(product.getProductId());
        clientDetail.setUpdateStock(product.getInStock() -product.getAuxStock());
        clientDetail.setConcept(product.getProductName()+" --- "+product.getProductDescription());
        listDetail.add(clientDetail);
      }

      saleModelClass.addClientDetail(listDetail ,getObjClient()!=null && !getObjClient().getId().isEmpty() ?getObjClient().getId() : mAuth.getUid() );
    }else{
      Toast.makeText(this,getString(R.string.msg_sale_empty_products),Toast.LENGTH_LONG).show();
    }
  }


  @Override
  public void getTotalSale(double getAuxPrice, boolean isAument) {
    setTotalSale(isAument ? getAuxPrice+getTotalSale() : getTotalSale()-getAuxPrice);
    binding.btnTotal.setText(String.format(getString(R.string.lbl_sale_total),String.valueOf(getTotalSale())));

  }

  public double getTotalSale() {
    return totalSale;
  }

  public void setTotalSale(double totalSale) {
    this.totalSale = totalSale;
  }

  public double getDebtClient() {
    return debtClient;
  }

  public void setDebtClient(double debtClient) {
    this.debtClient = debtClient;
  }

  public Client getObjClient() {
    return objClient;
  }

  public void setObjClient(Client objClient) {
    this.objClient = objClient;
  }


  private void showProgress(String message){
    progressbar.showProgress(this,message);
    binding.progressDownloadclient.setVisibility(View.VISIBLE);
  }
  private void hideProgress(){
    progressbar.hideProgress();
    binding.progressDownloadclient.setVisibility(View.GONE);
  }
}
