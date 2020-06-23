package com.epacheco.reports.Controller.RegisterUserController;

import android.util.Log;

import androidx.annotation.NonNull;
import com.epacheco.reports.Model.RegisterUserModel.RegisterUserModelClass;
import com.epacheco.reports.R;
import com.epacheco.reports.tools.ReportsApplication;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.TwitterSession;

public class RegisterUserControllerClass implements RegisterUserControllerInterface {
  private FirebaseAuth mAuth;
  private RegisterUserModelClass registerUserModelClass;

  public RegisterUserControllerClass(RegisterUserModelClass registerUserModelClass) {
    this.registerUserModelClass = registerUserModelClass;
    this.mAuth =  FirebaseAuth.getInstance();
  }

  @Override
  public void createAccountEmailAndPassword(String email, String password) {

      Log.e("Reports","is null: "+(registerUserModelClass==null));
    if(registerUserModelClass!=null){
      mAuth.createUserWithEmailAndPassword(email.trim(), password.trim())
          .addOnCompleteListener(registerUserModelClass.getMyActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()) {
                  Log.e("Reports","task.isSuccessful()");
                registerUserModelClass.successRegisterUserEmail(mAuth.getCurrentUser());
              } else {
                  Log.e("Reports","task no isSuccessful()");
                if(task.getException()!=null && task.getException().getMessage()!=null){
                  registerUserModelClass.errorRegisterUserEmail(getMessageFireBase(task.getException().getMessage()));
                }else{
                  registerUserModelClass.errorRegisterUserEmail(registerUserModelClass.getMyActivity().getString(R.string.msg_error_sistema));
                }
              }
            }
          });
    }
  }

  @Override
  public void loginEmailAndPassword(String email, String password) {
    if(registerUserModelClass!=null){
      mAuth.signInWithEmailAndPassword(email.trim(), password.trim())
          .addOnCompleteListener(registerUserModelClass.getMyActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()) {
                registerUserModelClass.successLoginUserEmail(mAuth.getCurrentUser());

              } else {
                if(task.getException()!=null && task.getException().getMessage()!=null){
                  registerUserModelClass.errorLoginUserEmail(getMessageFireBase(task.getException().getMessage()));
                }else{
                  registerUserModelClass.errorLoginUserEmail(registerUserModelClass.getMyActivity().getString(R.string.msg_error_sistema));
                }

              }

            }
          });
    }
  }

  @Override
  public void loginGoogle(GoogleSignInAccount acct) {

    AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
    mAuth.signInWithCredential(credential)
        .addOnCompleteListener(registerUserModelClass.getMyActivity(), new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {

             registerUserModelClass.successLoginUserGoogle(mAuth.getCurrentUser());

            } else {
              if(task.getException()!=null && task.getException().getMessage()!=null){
                registerUserModelClass.errorLoginUserGoogle(task.getException().getMessage());
              }else{
                registerUserModelClass.errorLoginUserGoogle(registerUserModelClass.getMyActivity().getString(R.string.msg_error_sistema));
              }

            }

            // ...
          }
        });
  }

  @Override
  public void loginTwitter(TwitterSession session) {
    if(registerUserModelClass!=null){
      AuthCredential credential = TwitterAuthProvider.getCredential(
          session.getAuthToken().token,
          session.getAuthToken().secret);

      mAuth.signInWithCredential(credential)
          .addOnCompleteListener(registerUserModelClass.getMyActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()) {
                registerUserModelClass.successLoginUserTwitter(mAuth.getCurrentUser());
              } else {
                if(task.getException()!=null){
                  registerUserModelClass.errorLoginUserTwitter(task.getException().getMessage());
                }else{
                  registerUserModelClass.errorLoginUserTwitter(ReportsApplication.getMyApplicationContext().getString(R.string.msg_error_sistema));
                }
              }
            }
          });
    }

  }

  @Override
  public void loginFacebook(AccessToken token) {
    if(registerUserModelClass!=null){
      AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
      mAuth.signInWithCredential(credential)
          .addOnCompleteListener(registerUserModelClass.getMyActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()) {
                registerUserModelClass.successLoginFacebook(mAuth.getCurrentUser());
              } else {
                if( task.getException()!=null){
                  registerUserModelClass.errorLoginFacebook( task.getException().getMessage());
                }else{
                  registerUserModelClass.errorLoginUserTwitter(ReportsApplication.getMyApplicationContext().getString(R.string.msg_error_sistema));
                }
              }
            }
          });
    }
  }

  private String getMessageFireBase(String exception){
   if(exception.contains("The user may have been deleted")){
     return ReportsApplication.getMyApplicationContext().getString(R.string.msg_firebase_delete_user);
   }else if(exception.contains("The password is invalid or the user does not have a password")){
     return ReportsApplication.getMyApplicationContext().getString(R.string.msg_firebase_without_password);
   }else if(exception.contains("The email address is already in use by another account")){
     return ReportsApplication.getMyApplicationContext().getString(R.string.msg_firebase_used_email);
   }else{
     return exception;
   }
  }


}
