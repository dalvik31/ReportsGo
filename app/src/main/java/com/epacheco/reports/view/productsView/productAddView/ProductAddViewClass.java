package com.epacheco.reports.view.productsView.productAddView;

import android.Manifest;
import android.Manifest.permission;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import android.text.TextUtils;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.epacheco.reports.BuildConfig;
import com.epacheco.reports.Model.ProductsModel.ProductsAddModel.ProductsAddModelClass;
import com.epacheco.reports.Pojo.Product.Product;
import com.epacheco.reports.R;
import com.epacheco.reports.tools.ReportsApplication;
import com.epacheco.reports.tools.ReportsDialogGlobal;
import com.epacheco.reports.tools.ReportsProgressDialog;
import com.epacheco.reports.tools.ScreenManager;
import com.epacheco.reports.view.productsView.productsView.ProductViewClass;
import com.epacheco.reports.view.productsView.scanCode.ScannedBarcodeActivity;
import com.epacheco.reports.databinding.ActivityProductAddViewClassBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import yuku.ambilwarna.AmbilWarnaDialog;

public class ProductAddViewClass extends AppCompatActivity implements ProductAddViewInterface {
    int defaultColor;
    public final static String PRODUCT_ID = "productId";
    private final static String MY_PROVIDER = BuildConfig.APPLICATION_ID + ".providers.FileProvider";
    private File photoFile;

    private final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL = 2;
    private final int REQUEST_IMAGE_CAPTURE = 3;
    private final int REQUEST_IMAGE_GALLERY = 4;

    public ActivityProductAddViewClassBinding binding;
    private ProductsAddModelClass productsAddModelClass;
    private String imgUrlUpload;
    private Product newProduct;
    private boolean imgSelected;
    private String productId;
    private boolean uploadImageAgain = true;
    private int productStock;
    private String typeSelected = ReportsApplication.getMyApplicationContext().getString(R.string.lbl_select_product_type_empty);
    private String sizeSelected = ReportsApplication.getMyApplicationContext().getString(R.string.lbl_select_product_type_empty);
    private boolean sizeNumeric;
    private boolean isCameraCode;
    private ReportsProgressDialog progressbar;
    private String myPathImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_add_view_class);
        inicializateElements();
        validateModifyProduct();
    }

    private void validateModifyProduct() {
        binding.AppCompatCheckBoxNumeric.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sizeNumeric = isChecked;

            }
        });

        if (productId != null && !productId.isEmpty()) {
            uploadImageAgain = false;
            binding.appBar.setTitle(R.string.lbl_modify_product);
            binding.containerModify.setVisibility(View.VISIBLE);
            binding.btnCreateProduct.setVisibility(View.GONE);
            binding.btnAddProduct.setVisibility(View.GONE);
            binding.btnModifyAccoount.setVisibility(View.VISIBLE);
            showProgress("Buscando producto");
            productsAddModelClass.getProduct(productId);
            binding.btnModifyProduct.setVisibility(View.VISIBLE);

            if (binding.txtOrderColor.getText().toString() == null && binding.txtOrderSize.getText().toString() == null && binding.txtOrderGendero.getText().toString() == null) {
                binding.txtOrderColor.setHint("Cadena vacia");
                binding.txtOrderSize.setHint("Cadena vacia");
                binding.txtOrderGendero.setHint("Cadena vacia");
            }


            if (binding.txtOrderColor.getText().toString().isEmpty() && binding.txtOrderSize.getText().toString().isEmpty() && binding.txtOrderGendero.getText().toString().isEmpty()) {
                binding.txtOrderColor.setHint("Cadena vacia");
                binding.txtOrderSize.setHint("Cadena vacia");
                binding.txtOrderGendero.setHint("Cadena vacia");
            }
        } else {
            uploadImageAgain = true;
            binding.containerModify.setVisibility(View.GONE);
            binding.btnCreateProduct.setVisibility(View.VISIBLE);
            binding.btnAddProduct.setVisibility(View.VISIBLE);
            binding.btnModifyAccoount.setVisibility(View.GONE);
            binding.btnModifyProduct.setVisibility(View.GONE);
        }


    }

    private void inicializateElements() {
        progressbar = ReportsProgressDialog.getInstance();
        productId = getIntent() != null ? getIntent().getStringExtra(PRODUCT_ID) : "";
        productsAddModelClass = new ProductsAddModelClass(this);

    }

    public void validateImage(){
        if (isImgSelected()) {
            if (validateInputs()) {
                showProgress(getString(R.string.msg_save_image));
                productsAddModelClass.uploadImage(ProductAddViewClass.this,myPathImage);
            } else {
                com.epacheco.reports.tools.Tools.showToasMessage(ProductAddViewClass.this, getString(R.string.msg_error_empty_inputs));
            }
        } else {
            com.epacheco.reports.tools.Tools.showToasMessage(ProductAddViewClass.this, getString(R.string.msg_error_empty_img_url));
        }
    }

    public void createNewProduct(View view) {

        if(binding.txtProductPriceBuy.getText().toString().equals(binding.txtProductPriceSale.getText().toString())){

            ReportsDialogGlobal.showDialogAcceptAnCancel(this, "Mismo monto", "¿Realmente deseas agregar este producto con el precio de compra igual al precio de venta ?",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            validateImage();

                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            binding.txtProductPriceSale.setText(" ");
                        }
                    });
        }else{
            validateImage();

        }

    }

    private void showProgress(String message) {
        progressbar.showProgress(this, message);
        binding.progressUploadProduct.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressbar.hideProgress();
        binding.progressUploadProduct.setVisibility(View.GONE);
    }


    /**
     *
     * Se obtiene la informacion del producto
     */
    private void createProduct(Product product) {
        Product myProduct = getNewProduct();
        String orderSize = binding.txtOrderSize.getText().toString();
        String orderColor = binding.txtOrderColor.getText().toString();
        myProduct.setProductId(product == null ? binding.txtProductCode.getText().toString() : product.getProductId());
        myProduct.setProductDate(product == null ? String.valueOf(System.currentTimeMillis()) : product.getProductDate());
        myProduct.setProductName(binding.txtProductName.getText().toString());
        myProduct.setProductCode(binding.txtProductCode.getText().toString());
        myProduct.setProductDescription(binding.txtProductDescription.getText().toString());
        myProduct.setProductPriceBuy(Double.parseDouble(binding.txtProductPriceBuy.getText().toString()));
        myProduct.setProductPriceSale(Double.parseDouble(binding.txtProductPriceSale.getText().toString()));
        myProduct.setProductType(typeSelected);
        myProduct.setInStock(productStock);
        myProduct.setUrlImage(getImgUrlUpload());
        myProduct.setColor(orderColor);
        myProduct.setTalla(orderSize);
        selectTypeProduct(myProduct);
    }

    private void selectTypeProduct(Product myProduct) {
       // if (binding.CheckRopa.isChecked()) {
            //myProduct.setTypeProduct(binding.CheckRopa.getText().toString());
       // }
    }

    public void goNewOrder1(View view) {
        ScreenManager.goOrderActivityProduct(this, productId);
    }


    private boolean validateInputs() {
        boolean inputsValidate = true;

        if (binding.txtProductName.getText().toString().isEmpty()) {
            binding.txtProductName.setError(getString(R.string.msg_error_empty_product_name));
            inputsValidate = false;
        }

         if (typeSelected.equals(getString(R.string.lbl_select_product_type_empty))) {
                binding.txtOrderGendero.setError(getString(R.string.msg_error_empty_type_name));
                inputsValidate = false;
            }
            if (binding.txtOrderColor.getText().toString().isEmpty()) {
                binding.txtOrderColor.setError(getString(R.string.msg_error_empty_inputs));
                inputsValidate = false;
            }
            if (binding.txtOrderSize.getText().toString().isEmpty()) {
                binding.txtOrderSize.setError(getString(R.string.msg_error_empty_inputs));
                inputsValidate = false;
            }

        if (binding.txtProductPriceBuy.getText().toString().isEmpty()) {
            binding.txtProductPriceBuy.setError(getString(R.string.msg_error_empty_price_buy));
            inputsValidate = false;
        }

        if (binding.txtProductPriceSale.getText().toString().isEmpty()) {
            binding.txtProductPriceSale.setError(getString(R.string.msg_error_empty_price_sale));
            inputsValidate = false;
        }

        if (binding.txtProductCode.getText().toString().isEmpty()) {
            binding.txtProductCode.setError(getString(R.string.msg_error_empty_code));
            inputsValidate = false;
        }

        productStock = binding.txtProductStock.getText().toString().isEmpty() ? 1 : Integer.parseInt(binding.txtProductStock.getText().toString());

        return inputsValidate;
    }

    @Override
    public FragmentActivity getMyActivity() {
        return this;
    }

    @Override
    public void successUploadImage(String imgUrl) {
        hideProgress();
        setImgUrlUpload(imgUrl);
        showProgress(getString(R.string.msg_save_product));
        if (productId != null && !productId.isEmpty()) {
            createProduct(getNewProduct());
            productsAddModelClass.modifyProduct(getNewProduct());
        } else {
            createProduct(null);
            productsAddModelClass.addProduct(getNewProduct());
        }


    }

    @Override
    public void errorUploadImage(String error) {
        hideProgress();
        com.epacheco.reports.tools.Tools.showToasMessage(this, error);
    }

    @Override
    public void successAddProduct() {
        hideProgress();
        finish();
        com.epacheco.reports.tools.Tools.showToasMessage(this, getString(R.string.msg_success_product));
    }

    @Override
    public void errorAddProduct(String error) {
        hideProgress();
        com.epacheco.reports.tools.Tools.showToasMessage(this, error);
    }


    /**
     *
     * Se muestra la informacion del producto
     */
    @Override
    public void successGetProduct(Product product) {
        hideProgress();
        setNewProduct(product);
        binding.txtProductName.setText(product.getProductName());
        binding.txtProductDescription.setText(product.getProductDescription());
        typeSelected = product.getProductType();
        binding.txtOrderGendero.setText(typeSelected);
        binding.txtProductPriceBuy.setText(String.valueOf(product.getProductPriceBuy()));
        binding.txtProductPriceSale.setText(String.valueOf(product.getProductPriceSale()));
        binding.txtProductCode.setText(String.valueOf(product.getProductCode()));
        binding.txtProductStock.setText(String.valueOf(product.getInStock()));
        validateProductStok(product.getInStock() == 0);


        if (TextUtils.isEmpty(product.getTalla()))
            binding.txtOrderSize.setHint(R.string.lbl_title_create_order_size_hint);
        else binding.txtOrderSize.setText(String.valueOf(product.getTalla()));

        if (TextUtils.isEmpty(product.getColor()))
            binding.txtOrderColor.setHint(R.string.lbl_title_create_order_color_hint);
        else binding.txtOrderColor.setText(String.valueOf(product.getColor()));



        boolean esColorValido = validarColor(product.getColor());
        if (esColorValido) {
            Log.e("Es numero", "Es un numero");
            binding.viewColorChoose.setBackgroundColor(Integer.parseInt(product.getColor()));
            defaultColor = Integer.parseInt(product.getColor());
            //Log.e("Color obtenido", product.getColor());
        } else {
            Log.e("No es numero", "No es un numero");
            Log.e("Color obtenido", product.getColor());
            defaultColor = ContextCompat.getColor(ProductAddViewClass.this,R.color.colorWhite);
        }

        setImgUrlUpload(product.getUrlImage());
        Glide.with(ReportsApplication.getMyApplicationContext()).load(product.getUrlImage()).into(binding.imgProduct);

        configShowTypeProduct(product);
    }
        public boolean validarColor(String str) {
            return str.matches("-?\\d+(\\.\\d+)?");
        }
    public void openColorPicker(View view){
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor = color;
                binding.viewColorChoose.setBackgroundColor(defaultColor);
                Log.e("Codigo de color", String.valueOf(color));
                binding.txtOrderColor.setText(String.valueOf(color));
            }
        });
        colorPicker.show();
    }

    private void configShowTypeProduct(Product product) {
        if (!TextUtils.isEmpty(product.getTypeProduct())) {
            String typeProduct = product.getTypeProduct();
        }
    }

    public void modifyProduct(View view) {
        if (validateInputs()) {
            createProduct(getNewProduct());
            if (uploadImageAgain) {
                showProgress(getString(R.string.msg_save_image));
                productsAddModelClass.uploadImage(this,myPathImage);
            } else {
                productsAddModelClass.modifyProduct(getNewProduct());
            }

        } else {
            com.epacheco.reports.tools.Tools.showToasMessage(this, getString(R.string.msg_name_empty));
        }
    }

    public void saleProduct(View view) {
        if (getNewProduct().getInStock() > 0) {
            ScreenManager.goSaleActivity(this, null, productId);
        }

    }

    public void removeProduct(View view) {
        ReportsDialogGlobal.showDialogAccept(this, getString(R.string.title_message_delete_elemnt),
                getString(R.string.body_message_delete_elemnt),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        productsAddModelClass.removeProduct(productId);
                    }
                }
        );

    }

    @Override
    public void errorGetProduct(String error) {
        hideProgress();
        finish();
        com.epacheco.reports.tools.Tools.showToasMessage(this, error);
    }

    @Override
    public void successModifyProduct() {
        hideProgress();
        finish();
        com.epacheco.reports.tools.Tools.showToasMessage(this, getString(R.string.msg_product_modify_success));
    }

    @Override
    public void errorModifyProduct(String error) {
        com.epacheco.reports.tools.Tools.showToasMessage(this, error);
    }

    @Override
    public void successRemoveProduct() {
        finish();
        com.epacheco.reports.tools.Tools.showToasMessage(this, getString(R.string.msg_product_remove_success));
    }

    @Override
    public void errorRemoveProduct(String error) {
        com.epacheco.reports.tools.Tools.showToasMessage(this, getString(R.string.msg_product_modify_success));
    }

    /**METODOS PARA ABRIR LA CAMARA Y LA GALERIA CON MENSAJES DE EXPLICACION*/

    /**
     * 1.- Primero al dar clic al boton que abre la camara, se crea un dialog que nos muestra las opciones
     * para elegir entre la camara o la galeria.
     */
    public void selectImage(View v) {
        setCameraCode(false);
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
            if (isCameraCode()) {
                ScreenManager.goScanActivity(this);
            } else {
                dispatchTakePictureIntent();/**Si el usuario ya acepto los permisos, abrimos la camara*/
            }
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
                        ActivityCompat.requestPermissions(ProductAddViewClass.this,
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
                        ActivityCompat.requestPermissions(ProductAddViewClass.this,
                                new String[]{permission.READ_EXTERNAL_STORAGE,permission.WRITE_EXTERNAL_STORAGE},
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
                    if (isCameraCode()) {
                        ScreenManager.goScanActivity(this);
                    } else {
                        dispatchTakePictureIntent();
                    }

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

                    setImgSelected(true);
                    uploadImageAgain = true;
                    myPathImage = photoFile.getAbsolutePath();
                    Log.e("getpath","getpatch"+myPathImage);
                    binding.imgProduct.setImageBitmap(takenImage);
                }
                break;
            case REQUEST_IMAGE_GALLERY:
                if (resultCode == RESULT_OK) {
                    /**Si el usuario selecciono la imagen de la galeria la pintamos en el imageView*/
                    Uri selectedImage = data.getData();
                    setImgSelected(true);
                    uploadImageAgain = true;
                    myPathImage = getPathFromInputStreamUri(this,selectedImage);
                    binding.imgProduct.setImageURI(selectedImage);
                }
                break;
            case ScannedBarcodeActivity.SCANBAR_ACTIVITY:
                if (data == null) return;
                binding.txtProductCode.setText(data.getStringExtra(ScannedBarcodeActivity.CODE_SCANNER));
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

    public String getImgUrlUpload() {
        return imgUrlUpload;
    }

    public void setImgUrlUpload(String imgUrlUpload) {
        this.imgUrlUpload = imgUrlUpload;
    }

    public Product getNewProduct() {
        if (newProduct == null) {
            setNewProduct(new Product());
        }
        return newProduct;
    }

    public void setNewProduct(Product newProduct) {
        this.newProduct = newProduct;
    }

    public boolean isImgSelected() {
        return imgSelected;
    }

    public void setImgSelected(boolean imgSelected) {
        this.imgSelected = imgSelected;
    }

    public boolean isCameraCode() {
        return isCameraCode;
    }

    public void setCameraCode(boolean cameraCode) {
        isCameraCode = cameraCode;
    }

    public void createSelectedDialog(View view) {
        AlertDialog.Builder b = new Builder(this);
        b.setTitle(getString(R.string.lbl_select_image_title));
        String[] types = {getString(R.string.lbl_select_product_type_woman), getString(R.string.lbl_select_product_type_man), getString(R.string.lbl_select_product_type_child), getString(R.string.lbl_select_product_type_girl)};
        b.setItems(types, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                switch (which) {
                    case 0:
                        typeSelected = getString(R.string.lbl_select_product_type_woman);
                        binding.txtOrderGendero.setText(typeSelected);
                        break;
                    case 1:
                        typeSelected = getString(R.string.lbl_select_product_type_man);
                        binding.txtOrderGendero.setText(typeSelected);
                        break;
                    case 2:
                        typeSelected = getString(R.string.lbl_select_product_type_child);
                        binding.txtOrderGendero.setText(typeSelected);
                        break;
                    case 3:
                        typeSelected = getString(R.string.lbl_select_product_type_girl);
                        binding.txtOrderGendero.setText(typeSelected);
                        break;
                    default:
                        typeSelected = getString(R.string.lbl_select_product_type_empty);
                        binding.txtOrderGendero.setText(typeSelected);
                }
            }

        });

        b.show();
    }

    public void createSelectedSizeDialog(View view) {
        if (sizeNumeric) {
            AlertDialog.Builder b = new Builder(this);
            b.setTitle(getString(R.string.lbl_select_dialog_title));
            String[] types = {
                    getString(R.string.lbl_select_product_size_2),
                    getString(R.string.lbl_select_product_size_4),
                    getString(R.string.lbl_select_product_size_6),
                    getString(R.string.lbl_select_product_size_8),
                    getString(R.string.lbl_select_product_size_10),
                    getString(R.string.lbl_select_product_size_12),
                    getString(R.string.lbl_select_product_size_14),
                    getString(R.string.lbl_select_product_size_16),
                    getString(R.string.lbl_select_product_size_28),
                    getString(R.string.lbl_select_product_size_30),
                    getString(R.string.lbl_select_product_size_32),
                    getString(R.string.lbl_select_product_size_34),
                    getString(R.string.lbl_select_product_size_36),
                    getString(R.string.lbl_select_product_size_38),
                    getString(R.string.lbl_select_product_size_40),
                    getString(R.string.lbl_select_product_size_42),
                    getString(R.string.lbl_select_product_size_44),
            };
            b.setItems(types, new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    switch (which) {
                        case 0:
                            sizeSelected = getString(R.string.lbl_select_product_size_2);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        case 1:
                            sizeSelected = getString(R.string.lbl_select_product_size_4);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        case 2:
                            sizeSelected = getString(R.string.lbl_select_product_size_6);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        case 3:
                            sizeSelected = getString(R.string.lbl_select_product_size_8);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        case 4:
                            sizeSelected = getString(R.string.lbl_select_product_size_10);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        case 5:
                            sizeSelected = getString(R.string.lbl_select_product_size_12);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        case 6:
                            sizeSelected = getString(R.string.lbl_select_product_size_14);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        case 7:
                            sizeSelected = getString(R.string.lbl_select_product_size_16);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        case 8:
                            sizeSelected = getString(R.string.lbl_select_product_size_28);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        case 9:
                            sizeSelected = getString(R.string.lbl_select_product_size_30);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        case 10:
                            sizeSelected = getString(R.string.lbl_select_product_size_32);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        case 11:
                            sizeSelected = getString(R.string.lbl_select_product_size_34);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        case 12:
                            sizeSelected = getString(R.string.lbl_select_product_size_36);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        case 13:
                            sizeSelected = getString(R.string.lbl_select_product_size_38);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        case 14:
                            sizeSelected = getString(R.string.lbl_select_product_size_40);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        case 15:
                            sizeSelected = getString(R.string.lbl_select_product_size_42);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        case 16:
                            sizeSelected = getString(R.string.lbl_select_product_size_44);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        default:
                            sizeSelected = getString(R.string.lbl_select_product_type_empty);
                            binding.txtOrderSize.setText(sizeSelected);
                    }
                }

            });
            b.show();
        } else {
            AlertDialog.Builder b = new Builder(this);
            b.setTitle(getString(R.string.lbl_select_dialog_title));
            String[] types = {getString(R.string.lbl_select_product_size_ch), getString(R.string.lbl_select_product_size_me), getString(R.string.lbl_select_product_size_gra), getString(R.string.lbl_select_product_size_ex)};
            b.setItems(types, new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    switch (which) {
                        case 0:
                            sizeSelected = getString(R.string.lbl_select_product_size_ch);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        case 1:
                            sizeSelected = getString(R.string.lbl_select_product_size_me);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        case 2:
                            sizeSelected = getString(R.string.lbl_select_product_size_gra);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        case 3:
                            sizeSelected = getString(R.string.lbl_select_product_size_ex);
                            binding.txtOrderSize.setText(sizeSelected);
                            break;
                        default:
                            sizeSelected = getString(R.string.lbl_select_product_type_empty);
                            binding.txtOrderSize.setText(sizeSelected);
                    }
                }

            });

            b.show();
        }

    }


    public void openQRorBarCode(View view) {
        setCameraCode(true);
        checkPermissionsCamera();
    }

    public void validateProductStok(boolean showDialog){
        if(showDialog){
            ReportsDialogGlobal.showCustomDialog(this);

        }
    }


}
