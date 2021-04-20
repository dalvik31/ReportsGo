package com.epacheco.reports.view.productsView.productsView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.databinding.DataBindingUtil;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;
import android.widget.SearchView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.epacheco.reports.Model.ProductsModel.ProductsModel.ProductsModelClass;
import com.epacheco.reports.Pojo.Product.Product;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.ReportsApplication;
import com.epacheco.reports.Tools.ReportsDialogGlobal;
import com.epacheco.reports.Tools.ReportsProgressDialog;
import com.epacheco.reports.Tools.ScreenManager;
import com.epacheco.reports.view.productsView.productAddView.ProductAddViewClass;
import com.epacheco.reports.view.productsView.scanCode.ScannedBarcodeActivity;
import com.epacheco.reports.databinding.ActivityProductViewClassBinding;
import com.google.firebase.auth.FirebaseAuth;
import java.util.List;

public class ProductViewClass extends AppCompatActivity implements ProductsViewInterface, SearchView.OnQueryTextListener, onItemProductClic{
  private final int MY_PERMISSIONS_REQUEST_CAMERA = 1;

  private ActivityProductViewClassBinding binding;
  public final static String IS_SEARCH = "isSearch";
  public final static int PRODUCT_SELECTED = 1;
  private ProductsModelClass productsModelClass;
  private boolean isSearch;
  private FirebaseAuth mAuth;
  private ReportsProgressDialog progressbar;
  private Handler handler;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this,R.layout.activity_product_view_class);
    inicializateElements();
  }

  private void inicializateElements() {
    mAuth = FirebaseAuth.getInstance();
    progressbar = ReportsProgressDialog.getInstance();
    if(getIntent()!=null){
      setSearch(getIntent().getBooleanExtra(IS_SEARCH,false));
    }
    binding.searchView.setOnQueryTextListener(this);
    productsModelClass = new ProductsModelClass(this);
    downloadProducts(null);
  }

  @Override
  public void onStart() {
    super.onStart();
    if(mAuth.getCurrentUser()!=null ){
      if( mAuth.getCurrentUser().getPhotoUrl()!=null){
        Glide.with(ReportsApplication.getMyApplicationContext()).load(com.epacheco.reports.Tools.Tools.getFormatUrlImage(mAuth.getCurrentUser().getPhotoUrl()))  .apply(
            RequestOptions.circleCropTransform()).into(binding.imgProfile);
      }
    }
  }
  @Override
  public boolean onQueryTextSubmit(String query) {
    return false;
  }

  @Override
  public boolean onQueryTextChange( String productName) {
    downloadProducts(productName);
    return true;
  }

  private void downloadProducts(final String productName){
    getHandler().removeCallbacksAndMessages(null);
    if(productName!=null){
      handler.postDelayed(new Runnable() {
        @Override
        public void run() {
          showProgress(getString(R.string.msg_search_product));
          if(productName.matches("[a-zA-Z1-9]+")){
            productsModelClass.downloadPorducts(null,productName);
          }else{
            productsModelClass.downloadPorducts(productName,null);
          }
        }
      }, 1000);
    }else{
      showProgress(getString(R.string.msg_search_product));
      productsModelClass.downloadPorducts( null,null);
    }
  }

  @Override
  public FragmentActivity getMyActivity() {
    return this;
  }

  @Override
  public void successDownloadProducts(List<Product> productList) {
    if(productList.size()>0){
      binding.lblZeroProducts.setVisibility(View.GONE);
      binding.recyclerListClient.setVisibility(View.VISIBLE);
      binding.progressDownloadclient.setVisibility(View.GONE);
      progressbar.hideProgress();
      binding.recyclerListClient.setHasFixedSize(true);
      binding.recyclerListClient.setLayoutManager(new LinearLayoutManager(this));
      AdapterProducts adapterClients = new AdapterProducts(productList);
      adapterClients.setOnItemProductClic(this);
      binding.recyclerListClient.setAdapter(adapterClients);
    }else{
      binding.lblZeroProducts.setVisibility(View.VISIBLE);
      binding.recyclerListClient.setVisibility(View.GONE);
    }

  }

  @Override
  public void errorDownloadProducts(String error) {
    if(!error.isEmpty()) com.epacheco.reports.Tools.Tools.showToasMessage(this,error);

    binding.lblZeroProducts.setVisibility(View.VISIBLE);
    binding.recyclerListClient.setVisibility(View.GONE);
    binding.progressDownloadclient.setVisibility(View.GONE);
    progressbar.hideProgress();
  }

  @Override
  public void onItemProductClic(View v, String productId, boolean inExistence) {
    if(isSearch() && inExistence){
      Intent intent = new Intent();
      intent.putExtra(ProductAddViewClass.PRODUCT_ID,productId);
      setResult(RESULT_OK, intent);
      finish();
    }else{
      if(!inExistence){
       com.epacheco.reports.Tools.Tools.showToasMessage(this,getString(R.string.lbl_stock_product_empty));
      }
      ScreenManager.goAddProductActivity(this,productId);
    }
  }



  public boolean isSearch() {
    return isSearch;
  }

  public void setSearch(boolean search) {
    isSearch = search;
  }

  public void goProfileActivity(View v){
    ScreenManager.goProfileActivity(this);
  }
  public void goAddProductActivity(View v){
    ScreenManager.goAddProductActivity(this,null);
  }

  public void openQRorBarCode(View view){
    checkPermissionsCamera();
  }

  /**
   * 2.- Revisamos que los permisos esten aceptados
   *
   * */
  private void checkPermissionsCamera() {
    if(com.epacheco.reports.Tools.Tools.checkPermissionsCamera(this)){
      if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
        createDialogPermisionCamera(); /**Si el usuario no ha aceptado los permisos, creamos un dialogo explicandole por que necesitamos utilizar la camara*/
      }else{
        createDialogPermisionCamera();/**Los permisos y el mensaje solo se ejecutan en versiones de android posteriores a la 5, si no es necesario abrir los permisos abrimos la camara*/
      }
    }else{
        ScreenManager.goScanActivity(this);
    }
  }


  /**
   * 4.- Creamos el dialogo donde le explicamos el por que necesitamos utilizar la camara
   *
   * */
  private void createDialogPermisionCamera() {
    ReportsDialogGlobal.showDialogAccept(this, getString(R.string.msg_permissions_title),
        getString(R.string.msg_permissions_camera_body),
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            /**
             * Si acepta abrimos el dialogo del sistema para que acepte los persmisos
             * Si cancela no hacemos nada
             * */
            ActivityCompat.requestPermissions(ProductViewClass.this,
                new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
          }
        }
    );
  }

  /**
   * 6.-Escuchamos la respuesta del usuario por cada persmiso
   *
   * */
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case MY_PERMISSIONS_REQUEST_CAMERA: {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          /**Si acepto los permisos ejecutamos el metodo de la camara*/
          ScreenManager.goScanActivity(this);
        } else {
          /**Si NO acepto los permisos ejecutamos el mensaje donde explicamos el por que necesitamos acceder a la camara*/
          createDialogPermisionCamera();
        }
        break;
      }
    }
  }
  /**
   * 9.- Escuchamos la imagen que el usuario elijio
   *
   * */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case ScannedBarcodeActivity.SCANBAR_ACTIVITY:
        if(data==null) return;
        binding.searchView.setQuery(data.getStringExtra(ScannedBarcodeActivity.CODE_SCANNER),false);
        break;
    }
  }


  private void showProgress(String message){
    progressbar.showProgress(this,message);
    binding.progressDownloadclient.setVisibility(View.VISIBLE);
  }
  private void hideProgress(){
    progressbar.hideProgress();
    binding.progressDownloadclient.setVisibility(View.GONE);
  }

  public Handler getHandler() {
    if(handler==null){
      handler = new Handler();
    }
    return handler;
  }
}
