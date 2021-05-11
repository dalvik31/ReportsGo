package com.epacheco.reports.Controller.ProductsController.ProductsController;

import androidx.annotation.NonNull;

import com.epacheco.reports.Model.ProductsModel.ProductsModel.ProductsModelClass;
import com.epacheco.reports.Pojo.Product.Product;
import com.epacheco.reports.R;
import com.epacheco.reports.tools.Constants;
import com.epacheco.reports.tools.ReportsApplication;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductsControllerClass implements ProductsControllerInterface {
    private FirebaseAuth mAuth;
    private ProductsModelClass productsModelClass;

    public ProductsControllerClass(ProductsModelClass productsModelClass) {
        this.productsModelClass = productsModelClass;
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void downloadPorducts(final String productName) {
        if (productsModelClass != null && mAuth.getUid() != null) {
            final ArrayList<Product> productsList = new ArrayList<>();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myDataBase = database.getReference("Reports");

            final DatabaseReference usersRef = myDataBase.child(mAuth.getUid()).child(Constants.CLIENT_PRODUCTS_TABLE_FIREBASE);

            usersRef.orderByChild("productName").startAt(productName)
                    .endAt(productName + "\uf8ff").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    productsList.clear();

                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Product product = postSnapshot.getValue(Product.class);
                        productsList.add(product);
                    }


                    usersRef.orderByChild("productCode").startAt(productName)
                            .endAt(productName + "\uf8ff").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                Product product = postSnapshot.getValue(Product.class);
                                if (!productsList.contains(product)) {
                                    productsList.add(product);
                                }

                            }


                            if (productsList.size() > 0) {
                                productsModelClass.successDownloadProducts(productsList);
                            } else {
                                productsModelClass.errorDownloadProducts(ReportsApplication.getMyApplicationContext().getString(
                                        R.string.msg_zero_products));
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            productsModelClass.errorDownloadProducts(databaseError.getMessage());
                        }
                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    productsModelClass.errorDownloadProducts(databaseError.getMessage());
                }
            });
        }
    }
}
