package com.epacheco.reports.view.financeActivityView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.epacheco.reports.Pojo.Sales.SalesDetail;
import com.epacheco.reports.R;
import com.epacheco.reports.tools.ReportsApplication;
import com.epacheco.reports.tools.Tools;
import com.epacheco.reports.view.productsView.productsView.AdapterProducts;

import java.util.List;

public class AdapterSales extends RecyclerView.Adapter<AdapterSales.HolderSale>{
    private List<SalesDetail> salesDetailList;

    public AdapterSales(List<SalesDetail> salesDetailList) {
        this.salesDetailList = salesDetailList;
    }

    @NonNull
    @Override
    public HolderSale onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sale, parent, false);
        return new AdapterSales.HolderSale(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSales.HolderSale holder, int position) {
        final SalesDetail salesDetail = salesDetailList.get(position);
        holder.tvClientName.setText(salesDetail.getNameClient());
        holder.tvProductCant.setText(String.format(ReportsApplication.getMyApplicationContext().getString(R.string.txt_product_cant),String.valueOf(salesDetail.getAuxStock())));
        holder.tvPriceBuy.setText(String.format(ReportsApplication.getMyApplicationContext().getString(R.string.txt_client_amount_format),String.valueOf(salesDetail.getProductPricreBuy())));
        holder.tvPriceSale.setText(String.format(ReportsApplication.getMyApplicationContext().getString(R.string.txt_client_amount_format),String.valueOf(salesDetail.getProductPriceSale())));
        holder.tvProductName.setText(salesDetail.getProductName());
        holder.tvProductDate.setText(salesDetail.getSaleId());
        Glide.with(ReportsApplication.getMyApplicationContext()).load(salesDetail.getImgProduct()).into(holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return salesDetailList!=null && salesDetailList.isEmpty() ? 0 : salesDetailList.size();
    }

    class HolderSale extends RecyclerView.ViewHolder{

        private ImageView imgProduct;
        private TextView tvProductName, tvClientName, tvPriceSale, tvPriceBuy, tvProductDate,tvProductCant;

        public HolderSale(@NonNull  View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_product);
            tvClientName = itemView.findViewById(R.id.tvClientName);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPriceSale = itemView.findViewById(R.id.tvPriceSale);
            tvPriceBuy = itemView.findViewById(R.id.tvPriceBuy);
            tvProductDate = itemView.findViewById(R.id.tvPriceSDate);
            tvProductCant = itemView.findViewById(R.id.tvProductCant);
        }
    }
}
