package com.epacheco.reports.view.profileView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.epacheco.reports.BuildConfig;
import com.epacheco.reports.Model.ProfileModel.ProfileModelClass;
import com.epacheco.reports.R;
import com.epacheco.reports.tools.ReportsDialogGlobal;
import com.epacheco.reports.tools.ScreenManager;
import com.epacheco.reports.databinding.ActivityProfileViewClassBinding;
import com.epacheco.reports.view.productsView.productAddView.ProductAddViewClass;
import com.epacheco.reports.view.productsView.scanCode.ScannedBarcodeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ProfileViewClass extends AppCompatActivity implements ProfileViewInterface {
    private FirebaseAuth mAuth;
    private ActivityProfileViewClassBinding binding;
    private FirebaseUser firebaseUser;
    private ProfileModelClass profileModelClass;


    //Constantes para abrir la camara
    private final static String MY_PROVIDER = BuildConfig.APPLICATION_ID + ".providers.FileProvider";
    private File photoFile;

    private final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL = 2;
    private final int REQUEST_IMAGE_CAPTURE = 3;
    private final int REQUEST_IMAGE_GALLERY = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_view_class);
        mAuth = FirebaseAuth.getInstance();
        profileModelClass = new ProfileModelClass(this);
        inicializateElements();
        chargeInformation();
    }

    private void chargeInformation() {
        if (getFirebaseUser().getDisplayName() != null && !getFirebaseUser().getDisplayName().isEmpty()) {
            binding.lblUserName.setText(getFirebaseUser().getDisplayName());
        } else {
            binding.lblUserName.setText(getFirebaseUser().getEmail().split("@")[0]);
        }
        if (getFirebaseUser().getPhotoUrl() != null && !getFirebaseUser().getPhotoUrl().toString().isEmpty()) {


            Glide.with(this).load(com.epacheco.reports.tools.Tools.getFormatUrlImage(mAuth.getCurrentUser().getPhotoUrl())).apply(
                    RequestOptions.circleCropTransform()).into(binding.imageviewAccountProfile);
            Glide.with(this).load(com.epacheco.reports.tools.Tools.getFormatUrlImage(mAuth.getCurrentUser().getPhotoUrl())).into(binding.imgBackground);
        }
        binding.lblUserEmail.setText(getFirebaseUser().getEmail());

    }

    private void inicializateElements() {
        if (mAuth.getCurrentUser() == null) {
            finish();
        } else {
            setFirebaseUser(mAuth.getCurrentUser());

        }
    }

    public void closeSesion(View v) {

        ReportsDialogGlobal.showDialogAccept(this, getString(R.string.Titulo_cerrar_sesion),
                getString(R.string.msg_cerrar_sesion),
                (dialog, which) -> {
                    mAuth.signOut();
                    ScreenManager.goRegisterActivity(ProfileViewClass.this);
                    finish();
                }
        );


    }

    @Override
    public FragmentActivity getMyActivity() {
        return this;
    }

    @Override
    public void successGetProfile(FirebaseUser firebaseUser) {

    }

    @Override
    public void errorGetProfile(String error) {
        com.epacheco.reports.tools.Tools.showToasMessage(this, error);
    }

    @Override
    public void successUpdateProfile() {
        com.epacheco.reports.tools.Tools.showToasMessage(this, "Imagen actualizada correctamente");
    }

    @Override
    public void errorUpdateProfile(String error) {
        com.epacheco.reports.tools.Tools.showToasMessage(this, error);
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

    /**METODOS PARA ABRIR LA CAMARA Y LA GALERIA CON MENSAJES DE EXPLICACION*/

    /**
     * 1.- Primero al dar clic al boton que abre la camara, se crea un dialog que nos muestra las opciones
     * para elegir entre la camara o la galeria.
     */

    public void updateImagen(View v) {
        final CharSequence[] options = {getString(R.string.lbl_take_photo), getString(R.string.lbl_select_gallery), getString(R.string.btn_cancel)};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.lbl_select_image_title));
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals(getString(R.string.lbl_take_photo))) {
                    checkPermissionsCamera();/**Al dar clic en la camara ejecutamos el metodo que revisa que los permisos esten aceptados*/
                    dialog.dismiss();
                } else if (options[item].equals(getString(R.string.lbl_select_gallery))) {
                    checkPermissionsGallery();/**Al dar clic en la galeria ejecutamos el metodo que revisa que los permisos esten aceptados*/
                    dialog.dismiss();
                } else if (options[item].equals(getString(R.string.btn_cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * 2.- Revisamos que los permisos esten aceptados
     */
    private void checkPermissionsCamera() {
        if (com.epacheco.reports.tools.Tools.checkPermissionsCamera(this)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                createDialogPermisionCamera(); /**Si el usuario no ha aceptado los permisos, creamos un dialogo explicandole por que necesitamos utilizar la camara*/
            } else {
                createDialogPermisionCamera();/**Los permisos y el mensaje solo se ejecutan en versiones de android posteriores a la 5, si no es necesario abrir los permisos abrimos la camara*/
            }
        } else {
            dispatchTakePictureIntent();/**Si el usuario ya acepto los permisos, abrimos la camara*/
        }
    }


    /**
     * 3.- Revisamos que los permisos esten aceptados
     * Hacemos lo mismo que con la camara.
     */
    private void checkPermissionsGallery() {
        if (com.epacheco.reports.tools.Tools.checkPermissionsGallery(this)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                createDialogPermisionGallery();
            } else {
                dispatchGalleryPictureIntent();
            }
        } else {
            dispatchGalleryPictureIntent();
        }
    }


    /**
     * 4.- Creamos el dialogo donde le explicamos el por que necesitamos utilizar la camara
     */
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
                        ActivityCompat.requestPermissions(ProfileViewClass.this,
                                new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                }
        );
    }

    /**
     * 5.- Creamos el dialogo donde le explicamos el por que necesitamos acceder a la galeria
     */
    private void createDialogPermisionGallery() {

        ReportsDialogGlobal.showDialogAccept(this, getString(R.string.msg_permissions_title),
                getString(R.string.msg_permissions_galler_body),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /**
                         * Si acepta abrimos el dialogo del sistema para que acepte los persmisos
                         * Si cancela no hacemos nada
                         * */
                        ActivityCompat.requestPermissions(ProfileViewClass.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL);
                    }
                }
        );
    }


    /**
     * 6.-Escuchamos la respuesta del usuario por cada persmiso
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /**Si acepto los permisos ejecutamos el metodo de la camara*/
                    dispatchTakePictureIntent();

                } else {
                    /**Si NO acepto los permisos ejecutamos el mensaje donde explicamos el por que necesitamos acceder a la camara*/
                    createDialogPermisionCamera();
                }
                break;
            }
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /**Si acepto los permisos ejecutamos el metodo de la galeria*/
                    dispatchGalleryPictureIntent();
                } else {
                    /**Si NO acepto los permisos ejecutamos el mensaje donde explicamos el por que necesitamos acceder a la galeria*/
                    createDialogPermisionGallery();
                }
                break;
            }
        }
    }

    /**
     * 7.- Creamos el metodo que abrira la camara
     * Para esto es necesario agregar un archivo xml en el directorio
     * xml para este ejemplo se llama filepaths.xml y ese se llama en el manifest
     * de la siguiente manera:
     * <p>
     * <provider
     * android:name="android.support.v4.content.FileProvider"
     * android:authorities="${applicationId}.providers.FileProvider"
     * android:exported="false"
     * android:grantUriPermissions="true">
     * <meta-data
     * android:name="android.support.FILE_PROVIDER_PATHS"
     * android:resource="@xml/filepaths"/>
     * </provider>
     * <p>
     * una vez que tenemos esto creamos el intent de la camara (validando que los permisos se hayan llamado)
     */

    private void dispatchTakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String photoFileName = "photo.jpg";
        photoFile = getPhotoFileUri(photoFileName);
        Uri fileProvider = FileProvider.getUriForFile(this, MY_PROVIDER, photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

    }

    /**
     * Con ayuda del archivo filepaths.xml vamos a obtener una uri de la foto que haya tomado
     * el usuario, guardamos la imagen en el dispositivo y obtenemos la uri
     */
    public File getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "");
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    /**
     * 8.- Creamos el metodo que abrira la galeria
     */
    private void dispatchGalleryPictureIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_IMAGE_GALLERY);
    }

    /**
     * 9.- Escuchamos la imagen que el usuario elijio
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    //Bitmap obtenido en la toma de la foto.
                    Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    try {
                        takenImage = rotateImageIfRequired(takenImage, photoFile.getAbsolutePath());
                    } catch (IOException e) {
                        Log.e("Error", "Ocurrio un error al girar la imagen");
                        e.printStackTrace();
                    }
                    if (takenImage == null) return;

                    Glide.with(this).load(takenImage).apply(
                            RequestOptions.circleCropTransform()).into(binding.imageviewAccountProfile);
                    binding.imgBackground.setImageBitmap(takenImage);
                    profileModelClass.updateProfile(photoFile.getAbsolutePath(), this);

                }
                break;
            case REQUEST_IMAGE_GALLERY:
                if (resultCode == RESULT_OK) {


                    Uri selectedImage = data.getData();
                    String tempPath = getPathFromInputStreamUri(this, selectedImage);
                    Glide.with(this).load(selectedImage).apply(
                            RequestOptions.circleCropTransform()).into(binding.imageviewAccountProfile);
                    binding.imgBackground.setImageURI(selectedImage);
                    profileModelClass.updateProfile(tempPath, this);
                }
                break;
        }
    }


    public String getPathFromInputStreamUri(Context context, Uri uri) {
        InputStream inputStream = null;
        String filePath = null;

        if (uri.getAuthority() != null) {
            try {
                inputStream = context.getContentResolver().openInputStream(uri);
                File photoFile = createTemporalFileFrom(inputStream);

                filePath = photoFile.getPath();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePath;
    }

    private File createTemporalFileFrom(InputStream inputStream) throws IOException {
        File targetFile = null;

        if (inputStream != null) {
            int read;
            byte[] buffer = new byte[8 * 1024];

            targetFile = createTemporalFile();
            OutputStream outputStream = new FileOutputStream(targetFile);

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return targetFile;
    }


    private File createTemporalFile() {
        return new File(getDirectoryName(), "tempPicture.jpg");
    }

    private String getDirectoryName() {
        PackageManager m = getPackageManager();
        String s = getPackageName();
        try {
            PackageInfo p = m.getPackageInfo(s, 0);
            return p.applicationInfo.dataDir;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("yourtag", "Error Package name not found ", e);
        }

        return "";
    }

    /**
     * En dispositivos actuales al tomar la foto, la toma con una orientacion
     * diferente y parace que la toma horizontal.
     * Este metodo parece que soluciona ese issue pero se tendria que probar en diferentes dispositivos.
     */
    private static Bitmap rotateImageIfRequired(Bitmap img, String selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    /**
     * Metodo que rota la imagen
     */
    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

}
