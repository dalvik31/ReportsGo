package com.epacheco.reports.view.financeActivityView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.epacheco.reports.Model.FinanceActivityModel.FinanceActitvityModel;
import com.epacheco.reports.Pojo.Sales.SalesDetail;
import com.epacheco.reports.R;
import com.epacheco.reports.databinding.ActivityFinanceAcitivityViewBinding;
import com.epacheco.reports.tools.ReportsApplication;
import com.epacheco.reports.tools.ScreenManager;
import com.epacheco.reports.tools.Tools;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class FinanceAcitivityView extends AppCompatActivity implements FinanceActivityInterface, AdapterSales.onItemClic {
    private FirebaseAuth mAuth;
    private ActivityFinanceAcitivityViewBinding binding;
    private Calendar min;
    private FinanceActitvityModel financeActitvityModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_finance_acitivity_view);
        mAuth = FirebaseAuth.getInstance();
        financeActitvityModel = new FinanceActitvityModel(this);

        initDate();
    }

    private void initDate() {
        min = Calendar.getInstance();
        min.set(Calendar.YEAR, min.get(Calendar.YEAR) - 1);
        binding.tvDate.setText(Tools.getFormatDateSimple(String.valueOf(System.currentTimeMillis())));

        financeActitvityModel.getSales(System.currentTimeMillis(), 0);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null && mAuth.getCurrentUser().getPhotoUrl() != null) {
            Glide
                    .with(ReportsApplication.getMyApplicationContext())
                    .load(com.epacheco.reports.tools.Tools.getFormatUrlImage(mAuth.getCurrentUser().getPhotoUrl()))
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.appBarLayout.getImageView());
        }

    }

    public void goProfileActivity(View v) {
        ScreenManager.goProfileActivity(this);
    }

    public void goSelectDate(View v) {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Selecciona un periodo");
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setStart(min.getTimeInMillis());
        constraintsBuilder.setEnd(System.currentTimeMillis());
        builder.setCalendarConstraints(constraintsBuilder.build());
        MaterialDatePicker<Pair<Long, Long>> picker = builder.build();
        picker.show(getSupportFragmentManager(), picker.toString());
        picker.addOnPositiveButtonClickListener(selection -> {
            long startDate = selection.first;
            long endDate = selection.second;
            TimeZone timeZoneUTC = TimeZone.getDefault();
            int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;
            Date firstDate = new Date(startDate + offsetFromUTC);
            Date secondDate = new Date(endDate + offsetFromUTC);
            financeActitvityModel.getSales(firstDate.getTime(), startDate == endDate ? 0 : secondDate.getTime());
            binding.tvDate.setText(Tools.getFormatDateToDates(String.valueOf(firstDate.getTime()), String.valueOf(secondDate.getTime())));

        });

    }


    @Override
    public void successGetSales(List<SalesDetail> salesDetails) {
        if (salesDetails != null && !salesDetails.isEmpty()) {
            getSalesFinance(salesDetails);
            AdapterSales adapterSales = new AdapterSales(this, salesDetails);
            binding.rvListSales.setHasFixedSize(true);
            binding.rvListSales.setLayoutManager(new LinearLayoutManager(this));
            binding.rvListSales.setAdapter(adapterSales);
            binding.rvListSales.setVisibility(View.VISIBLE);
            binding.imgEmpty.setVisibility(View.GONE);
            binding.tvEmptyData.setVisibility(View.GONE);
        } else {
            getSalesFinance(null);
            binding.rvListSales.setVisibility(View.GONE);
            binding.imgEmpty.setVisibility(View.VISIBLE);
            binding.tvEmptyData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void errorGetSales(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successCancelSale() {
        Toast.makeText(this, getString(R.string.msg_cancel_sale_succes), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorCancelSale(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private void getSalesFinance(List<SalesDetail> salesDetails) {
        if (salesDetails != null) {
            double price = 0;
            double investment = 0;
            int numberProducts = 0;
            for (SalesDetail salesDetail : salesDetails) {
                if (!salesDetail.isCancelSale()) {
                    if(!salesDetail.isCreditSale()){
                        price += salesDetail.getProductPriceSale();
                        investment += salesDetail.getProductPricreBuy();
                    }
                    numberProducts += salesDetail.getAuxStock();
                }
            }
            binding.tvNumberProduct.setText(String.format(getString(R.string.lbl_number_product_sales), String.valueOf(numberProducts)));
            binding.tvNumberPrice.setText(String.format(getString(R.string.lbl_price_sales), String.valueOf(price)));
            binding.tvNumberInvestment.setText(String.format(getString(R.string.lbl_price_sales), String.valueOf(investment)));
            binding.tvNumberProfit.setText(String.format(getString(R.string.lbl_price_sales), String.valueOf(price - investment)));
        } else {
            binding.tvNumberProduct.setText((String.format(getString(R.string.lbl_number_product_sales), String.valueOf(0))));
            binding.tvNumberPrice.setText(R.string.lbl_price_zero);
            binding.tvNumberInvestment.setText(R.string.lbl_price_zero);
            binding.tvNumberProfit.setText(R.string.lbl_price_zero);

        }

    }

    @Override
    public void onItemClicSale(String idSale) {
        financeActitvityModel.cancelSale(idSale);
    }
}