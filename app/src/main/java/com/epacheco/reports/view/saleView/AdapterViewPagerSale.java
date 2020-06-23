package com.epacheco.reports.view.saleView;

import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.epacheco.reports.Pojo.Product.Product;
import com.epacheco.reports.R;
import com.epacheco.reports.Tools.ReportsApplication;
import java.util.ArrayList;

public class AdapterViewPagerSale extends PagerAdapter {
  // This holds all the currently displayable views, in order from left to right.
  private ArrayList<View> views = new ArrayList<View>();
  private ArrayList<Product> productList;
  private getTotalSale getTotalSale;


  //-----------------------------------------------------------------------------
  // Used by ViewPager.  "Object" represents the page; tell the ViewPager where the
  // page should be displayed, from left-to-right.  If the page no longer exists,
  // return POSITION_NONE.
  @Override
  public int getItemPosition (@NonNull Object object)
  {
    int index = views.indexOf (object);
    if (index == -1)
      return POSITION_NONE;
    else
      return index;
  }

  //-----------------------------------------------------------------------------
  // Used by ViewPager.  Called when ViewPager needs a page to display; it is our job
  // to add the page to the container, which is normally the ViewPager itself.  Since
  // all our pages are persistent, we simply retrieve it from our "views" ArrayList.
  @Override
  public Object instantiateItem (@NonNull ViewGroup container, int position)
  {
    View v = views.get (position);
    container.addView (v);
    return v;
  }

  //-----------------------------------------------------------------------------
  // Used by ViewPager.  Called when ViewPager no longer needs a page to display; it
  // is our job to remove the page from the container, which is normally the
  // ViewPager itself.  Since all our pages are persistent, we do nothing to the
  // contents of our "views" ArrayList.
  @Override
  public void destroyItem (@NonNull ViewGroup container, int position,@NonNull Object object)
  {
    container.removeView (views.get (position));
  }

  //-----------------------------------------------------------------------------
  // Used by ViewPager; can be used by app as well.
  // Returns the total number of pages that the ViewPage can display.  This must
  // never be 0.
  @Override
  public int getCount ()
  {
    return views.size();
  }

  //-----------------------------------------------------------------------------
  // Used by ViewPager.
  @Override
  public boolean isViewFromObject (@NonNull View view,@NonNull Object object)
  {
    return view == object;
  }

  //-----------------------------------------------------------------------------
  // Add "view" to right end of "views".
  // Returns the position of the new view.
  // The app should call this to add pages; not used by ViewPager.
  public int addView (View v)
  {
    return addView (v, views.size(),null);
  }

  //-----------------------------------------------------------------------------
  // Add "view" at "position" to "views".
  // Returns position of new view.
  // The app should call this to add pages; not used by ViewPager.
   int addView (View v, int position, Product product)
  {
    views.add (position, v);

    if(product!=null) loadInformation(product,position);
    return position;
  }

  private void loadInformation(final Product product, final int position) {
    getProductList().add(product);
    product.setAuxPrice(product.getProductPriceSale());
    product.setAuxStock(1);
    getTotalSale.getTotalSale(product.getProductPriceSale(),true);
    TextView lblProName = getView(position).findViewById(R.id.lbl_product_name);
    TextView lblProid = getView(position).findViewById(R.id.lbl_product_id);
    final TextView lblProPriceTotal = getView(position).findViewById(R.id.lbl_product_price_total);
    TextView lblProPriceUnit = getView(position).findViewById(R.id.lbl_product_price_unit);
    TextView lblProStock = getView(position).findViewById(R.id.txt_product_stock);
    final TextView lblProStockCpunt = getView(position).findViewById(R.id.lbl_stock_product_count);
    ImageView imgPro = getView(position).findViewById(R.id.img_product);
    FloatingActionButton btnPlus = getView(position).findViewById(R.id.fabtn_plus_product);
    FloatingActionButton btnMinum = getView(position).findViewById(R.id.fabtn_minum_product);
    lblProStockCpunt.setText(String.valueOf(1));
    lblProid.setText(String.valueOf(product.getProductCode()));
    lblProName.setText(product.getProductName());
    lblProPriceUnit.setText(String.format(ReportsApplication.getMyApplicationContext().getString(R.string.txt_client_amount_format),String.valueOf(product.getProductPriceSale())));

    lblProPriceTotal.setText(String.format(ReportsApplication.getMyApplicationContext().getString(R.string.txt_client_amount_format),String.valueOf(product.getProductPriceSale())));
    lblProStock.setText(String.format(ReportsApplication.getMyApplicationContext().getString(R.string.lbl_stock_product),String.valueOf(product.getInStock())));
    Glide.with(ReportsApplication.getMyApplicationContext()).load(product.getUrlImage()).into(imgPro);

    btnPlus.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if(product.getAuxStock()<product.getInStock()){
          product.setAuxPrice(product.getProductPriceSale()+product.getAuxPrice());
          lblProPriceTotal.setText(String.format(ReportsApplication.getMyApplicationContext().getString(R.string.txt_client_amount_format),String.valueOf(product.getAuxPrice())));
          product.setAuxStock(product.getAuxStock()+1);
          lblProStockCpunt.setText(String.valueOf(product.getAuxStock()));
          getTotalSale.getTotalSale(product.getProductPriceSale(),true);
        }

       /* if(productList.get(position).getInStock()<product.getInStock()){
          setPorductStock(productList.get(position).getInStock() + 1);
          lblProStockCpunt.setText(String.valueOf(productList.get(position).getInStock()));
          setPriceTotal(productList.get(position).getProductPriceSale()+getPriceTotal());
          lblProPriceTotal.setText(String.valueOf(getPriceTotal()));
        }*/
      }
    });
    btnMinum.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if(product.getAuxStock()>1){
          product.setAuxPrice(product.getAuxPrice()-product.getProductPriceSale());
          lblProPriceTotal.setText(String.format(ReportsApplication.getMyApplicationContext().getString(R.string.txt_client_amount_format),String.valueOf(product.getAuxPrice())));
          product.setAuxStock(product.getAuxStock()-1);
          lblProStockCpunt.setText(String.valueOf(product.getAuxStock()));
          getTotalSale.getTotalSale(product.getProductPriceSale(),false);
        }

       /* if(productList.get(position).getInStock()>1){
          setPorductStock(productList.get(position).getInStock() - 1);
          lblProStockCpunt.setText(String.valueOf(productList.get(position).getInStock()));
          setPriceTotal(getPriceTotal()-productList.get(position).getProductPriceSale());
          lblProPriceTotal.setText(String.valueOf(getPriceTotal()));
        }*/
      }
    });
  }


  //-----------------------------------------------------------------------------
  // Removes "view" from "views".
  // Retuns position of removed view.
  // The app should call this to remove pages; not used by ViewPager.
  public int removeView (ViewPager pager, View v)
  {
    return removeView (pager, views.indexOf (v));
  }

  //-----------------------------------------------------------------------------
  // Removes the "view" at "position" from "views".
  // Retuns position of removed view.
  // The app should call this to remove pages; not used by ViewPager.
   int removeView (ViewPager pager, int position)
  {
    // ViewPager doesn't have a delete method; the closest is to set the adapter
    // again.  When doing so, it deletes all its views.  Then we can delete the view
    // from from the adapter and finally set the adapter to the pager again.  Note
    // that we set the adapter to null before removing the view from "views" - that's
    // because while ViewPager deletes all its views, it will call destroyItem which
    // will in turn cause a null pointer ref.
    pager.setAdapter (null);
    views.remove (position);
    getTotalSale.getTotalSale(getProductList().get(position).getAuxPrice(),false);
    getProductList().remove(position);
    pager.setAdapter (this);

    return position;
  }

  void removeAllView(ViewPager pager)
  {
    // ViewPager doesn't have a delete method; the closest is to set the adapter
    // again.  When doing so, it deletes all its views.  Then we can delete the view
    // from from the adapter and finally set the adapter to the pager again.  Note
    // that we set the adapter to null before removing the view from "views" - that's
    // because while ViewPager deletes all its views, it will call destroyItem which
    // will in turn cause a null pointer ref.
    pager.setAdapter (null);
    views.clear();
   getProductList().clear();
    pager.setAdapter (this);

  }

  //-----------------------------------------------------------------------------
  // Returns the "view" at "position".
  // The app should call this to retrieve a view; not used by ViewPager.
  public View getView (int position)
  {
    return views.get (position);
  }

  public ArrayList<Product> getProductList() {
    if(productList==null){
      Log.e("entro","aqui");
      setProductList(new ArrayList<Product>());
    }
    return productList;
  }

  public void setProductList(ArrayList<Product> productList) {
    this.productList = productList;
  }

  public com.epacheco.reports.view.saleView.getTotalSale getGetTotalSale() {
    return getTotalSale;
  }

  public void setGetTotalSale(com.epacheco.reports.view.saleView.getTotalSale getTotalSale) {
    this.getTotalSale = getTotalSale;
  }

  // Other relevant methods:

  // finishUpdate - called by the ViewPager - we don't care about what pages the
  // pager is displaying so we don't use this method.
}
