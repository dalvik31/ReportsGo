package com.epacheco.reports.Controller.ProfileController;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.epacheco.reports.Model.ProfileModel.ProfileModelClass;
import com.epacheco.reports.R;
import com.epacheco.reports.tools.Constants;
import com.epacheco.reports.tools.CustomOnCompleteListener;
import com.epacheco.reports.tools.ReportsApplication;
import com.epacheco.reports.tools.Tools;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ProfileControllerClass implements ProfileControllerInterface {

    private ProfileModelClass profileModelClass;
    private FirebaseAuth mAuth;


    public ProfileControllerClass(ProfileModelClass profileModelClass) {
        this.profileModelClass = profileModelClass;
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void getProfile() {

    }

    @Override
    public void updateProfile(String imgUrl, Context context) {
        if (profileModelClass != null && mAuth != null && mAuth.getUid() != null) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            final StorageReference ref = storage.getReference().child("Reports").child(mAuth.getUid()).child(Constants.CLIENT_IMAGES_PROFILE_TABLE_FIREBASE).child(mAuth.getUid() + ".jpg");
            byte[] data = Tools.getImage(context, imgUrl, 150, 150);
            if (data != null) {
                UploadTask uploadTask = ref.putBytes(data);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful() && task.getException() != null) {
                            profileModelClass.errorUpdateProfile(task.getException().getMessage());
                            throw task.getException();
                        }
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new CustomOnCompleteListener<Uri>(context) {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        super.onComplete(task);
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            if (downloadUri != null) {
                                uploadProfile(downloadUri+downloadUri.toString());
                            } else {
                                profileModelClass.errorUpdateProfile(ReportsApplication.getMyApplicationContext().getString(R.string.msg_error_sistema));
                            }
                        } else {
                            if (task.getException() != null) {
                                profileModelClass.errorUpdateProfile(task.getException().getMessage());
                            } else {
                                profileModelClass.errorUpdateProfile(ReportsApplication.getMyApplicationContext().getString(R.string.msg_error_sistema));
                            }
                        }
                    }
                });
            } else {
                profileModelClass.errorUpdateProfile("Ocurrio un error al subir la imagen, o no has aceptado los permisos necesarios.");
            }

        }
    }


    private void uploadProfile(String url){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse(url))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        profileModelClass.successUpdateProfile();
                    }else{
                        profileModelClass.errorUpdateProfile("Ocurrio un error al actualizar tu perfil");
                    }
                });
    }
}
