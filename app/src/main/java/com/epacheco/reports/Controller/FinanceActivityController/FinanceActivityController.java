package com.epacheco.reports.Controller.FinanceActivityController;

import android.util.Log;

import androidx.annotation.NonNull;

import com.epacheco.reports.Model.FinanceActivityModel.FinanceActitvityModel;
import com.epacheco.reports.Pojo.Product.Product;
import com.epacheco.reports.Pojo.Sales.SalesDetail;
import com.epacheco.reports.R;
import com.epacheco.reports.tools.Constants;
import com.epacheco.reports.tools.ReportsApplication;
import com.epacheco.reports.tools.Tools;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FinanceActivityController implements FinanceActivityControllerInterface{
    private FirebaseAuth mAuth;
    private FinanceActitvityModel financeActitvityModel;

    public FinanceActivityController(FinanceActitvityModel financeActitvityModel) {
        this.financeActitvityModel = financeActitvityModel;
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void getSales(long firstDate, long secondDate) {
        if (financeActitvityModel != null && mAuth.getUid() != null) {
            final ArrayList<SalesDetail> salesDetails = new ArrayList<>();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myDataBase = database.getReference("Reports");

            final DatabaseReference usersRef = myDataBase.child(mAuth.getUid()).child(Constants.CLIENT_SALES_TABLE_FIREBASE);

            Log.e("firstdate","firstdate"+ Tools.getFormatDate(String.valueOf(firstDate)));
            Log.e("secondDate","secondDate"+Tools.getFormatDate(String.valueOf(secondDate)));
            Query oneDate = null;
            if(secondDate == 0){
                oneDate = usersRef.orderByChild("saleId").equalTo(Tools.getFormatDate(String.valueOf(firstDate)));
            }else{
                oneDate =   usersRef.orderByChild("saleId").startAt(Tools.getFormatDate(String.valueOf(firstDate))).endAt(Tools.getFormatDate(String.valueOf(secondDate)));
            }

            oneDate.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    salesDetails.clear();

                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        SalesDetail sale = postSnapshot.getValue(SalesDetail.class);
                        salesDetails.add(sale);
                    }

                    financeActitvityModel.successGetSales(salesDetails);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    financeActitvityModel.errorGetSales(databaseError.getMessage());
                }
            });
        }
    }
}
