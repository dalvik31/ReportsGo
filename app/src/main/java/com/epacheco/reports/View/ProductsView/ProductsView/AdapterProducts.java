package com.epacheco.reports.View.ProductsView.ProductsView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.epacheco.reports.Pojo.Product.Product;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.ReportsApplication;
import java.util.List;

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.HolderProducts>{

  private List<Product> productList;
  private onItemProductClic onItemProductClic;

   AdapterProducts(List<Product> productList) {
    this.productList = productList;
  }

  @NonNull
  @Override
  public HolderProducts onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_product, viewGroup, false);
    return new HolderProducts(v);
  }

  @Override
  public void onBindViewHolder(@NonNull HolderProducts holderProducts, int i) {
    final Product myProduct = productList.get(i);
    Glide.with(ReportsApplication.getMyApplicationContext()).load(myProduct.getUrlImage()).into(holderProducts.imgProduct);
    holderProducts.txtProId.setText(String.valueOf(myProduct.getProductCode()));
    holderProducts.txtProName.setText(myProduct.getProductName());
    holderProducts.txtProDesc.setText(myProduct.getProductDescription());
    holderProducts.txtProPrice.setText(String.format(ReportsApplication.getMyApplicationContext().getString(R.string.txt_client_amount_format),String.valueOf(myProduct.getProductPriceSale())));
    holderProducts.txtProStock.setText(String.format(ReportsApplication.getMyApplicationContext().getString(R.string.lbl_stock_product),String.valueOf(myProduct.getInStock())));
    holderProducts.imgModify.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        getOnItemProductClic().onItemProductClic(v,myProduct.getProductId(),myProduct.getInStock()>0);
      }
    });
    holderProducts.cardviewProduct.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        getOnItemProductClic().onItemProductClic(v,myProduct.getProductId(),myProduct.getInStock()>0);
      }
    });
    holderProducts.imgDetails.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        getOnItemProductClic().onItemProductClic(v,myProduct.getProductId(),myProduct.getInStock()>0);
      }
    });

    holderProducts.backStockEmpty.setVisibility(myProduct.getInStock()>0 ? View.GONE : View.VISIBLE);
   }

  @Override
  public int getItemCount() {
    return productList.size();
  }

  class HolderProducts extends RecyclerView.ViewHolder{
    private ImageView imgProduct, imgModify,imgDetails;
    private TextView txtProName,txtProDesc,txtProId,txtProPrice,txtProStock;
    private CardView cardviewProduct;
    private View backStockEmpty;
    HolderProducts(@NonNull View itemView) {
      super(itemView);
      imgProduct = itemView.findViewById(R.id.img_product);
      txtProName = itemView.findViewById(R.id.lbl_product_name);
      txtProDesc = itemView.findViewById(R.id.lbl_product_description);
      txtProId = itemView.findViewById(R.id.lbl_product_id);
      txtProPrice = itemView.findViewById(R.id.lbl_product_price);
      imgModify = itemView.findViewById(R.id.btn_modify);
      imgDetails = itemView.findViewById(R.id.btn_detail);
      cardviewProduct = itemView.findViewById(R.id.card_product);
      txtProStock = itemView.findViewById(R.id.txt_product_stock);
      backStockEmpty = itemView.findViewById(R.id.background_stock_empty);


    }
  }

  public com.epacheco.reports.View.ProductsView.ProductsView.onItemProductClic getOnItemProductClic() {
    return onItemProductClic;
  }

  public void setOnItemProductClic(
      com.epacheco.reports.View.ProductsView.ProductsView.onItemProductClic onItemProductClic) {
    this.onItemProductClic = onItemProductClic;
  }
}
