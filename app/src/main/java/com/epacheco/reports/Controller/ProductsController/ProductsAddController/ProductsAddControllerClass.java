package com.epacheco.reports.Controller.ProductsController.ProductsAddController;

import android.graphics.Bitmap;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.epacheco.reports.Model.ProductsModel.ProductsAddModel.ProductsAddModelClass;
import com.epacheco.reports.Pojo.Product.Product;
import com.epacheco.reports.R;
import com.epacheco.reports.tools.Constants;
import com.epacheco.reports.tools.ReportsApplication;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;

public class ProductsAddControllerClass implements ProductsAddControllerInterface {
  private FirebaseAuth mAuth;
  private ProductsAddModelClass productsAddModelClass;
  private ByteArrayOutputStream baos;

  public ProductsAddControllerClass(ProductsAddModelClass productsAddModelClass) {
    this.productsAddModelClass = productsAddModelClass;
    this.mAuth = FirebaseAuth.getInstance();
  }


  @Override
  public void addProduct(Product product) {
    if(productsAddModelClass!=null && mAuth!=null&& mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");

      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_PRODUCTS_TABLE_FIREBASE);
      usersRef.child(product.getProductId()).setValue(product, new CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
          if (databaseError != null) {
            productsAddModelClass.errorAddProduct(databaseError.getMessage());

          } else {
            productsAddModelClass.successAddProduct();
          }
        }

      });
    }
  }

  @Override
  public void uploadImage(Bitmap imgUpload) {
    if(productsAddModelClass!=null && mAuth!=null&& mAuth.getUid()!=null){
      FirebaseStorage storage = FirebaseStorage.getInstance();
      final StorageReference ref   =storage.getReference().child("Reports").child(mAuth.getUid()).child(Constants.CLIENT_IMAGES_TABLE_FIREBASE).child(System.currentTimeMillis()+".jpg");
      baos = new ByteArrayOutputStream();
      imgUpload.compress(Bitmap.CompressFormat.JPEG, 100, baos);
      byte[] data = baos.toByteArray();
      UploadTask uploadTask = ref.putBytes(data);
      uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
        @Override
        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
          if (!task.isSuccessful() && task.getException()!=null) {
            productsAddModelClass.errorUploadImage(task.getException().getMessage());
            throw task.getException();
          }
          return ref.getDownloadUrl();
        }
      }).addOnCompleteListener(new OnCompleteListener<Uri>() {
        @Override
        public void onComplete(@NonNull Task<Uri> task) {
          baos = null;
          if (task.isSuccessful()) {
            Uri downloadUri = task.getResult();
            if(downloadUri!=null){
              productsAddModelClass.successUploadImage(downloadUri.toString());
            }else{
              productsAddModelClass.errorUploadImage(ReportsApplication.getMyApplicationContext().getString(R.string.msg_error_sistema));
            }
          } else {
            if(task.getException()!=null){
              productsAddModelClass.errorUploadImage(task.getException().getMessage());
            }else{
              productsAddModelClass.errorUploadImage(ReportsApplication.getMyApplicationContext().getString(R.string.msg_error_sistema));
            }
          }
        }
      });
    }
  }

  @Override
  public void getProduct(String idProduct) {
    if(productsAddModelClass!=null && mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");

      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_PRODUCTS_TABLE_FIREBASE);
      String paramName = idProduct!=null && !idProduct.isEmpty() ? idProduct : "";

      usersRef.orderByChild("productId").equalTo(paramName).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

          Product myProduct = null;
          for (DataSnapshot postSnapshot: snapshot.getChildren()) {
            myProduct = postSnapshot.getValue(Product.class);
          }
          if(myProduct!=null){
            productsAddModelClass.successGetProduct(myProduct);
          }else{
            productsAddModelClass.errorGetProduct("");
          }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
          productsAddModelClass.errorGetProduct(databaseError.getMessage());
        }
      });
    }
  }

  @Override
  public void modifyProduct(Product product) {
    if(productsAddModelClass!=null && mAuth!=null&& mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");
      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_PRODUCTS_TABLE_FIREBASE);
      usersRef.child(product.getProductId()).setValue(product, new CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
          if (databaseError != null) {
            productsAddModelClass.errorModifyProduct(databaseError.getMessage());

          } else {
            productsAddModelClass.successModifyProduct();
          }
        }

      });
    }
  }

  @Override
  public void removeProduct(String idProduct) {
    if(productsAddModelClass!=null && mAuth!=null&& mAuth.getUid()!=null){
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("Reports");
      DatabaseReference usersRef = myRef.child(mAuth.getUid()).child(Constants.CLIENT_PRODUCTS_TABLE_FIREBASE);
      usersRef.child(idProduct).removeValue()
          .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
              productsAddModelClass.successRemoveProduct();
            }
          })
          .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              productsAddModelClass.errorRemoveProduct(e.getMessage());
            }
          });
    }
  }
}
