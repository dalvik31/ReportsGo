package com.epacheco.reports.view.clientView.addNewClient;


import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.epacheco.reports.R;
import com.epacheco.reports.databinding.FragmentClientNameBinding;
import com.epacheco.reports.tools.Constants;
import com.epacheco.reports.view.clientView.clientAddView.ClientAddViewClass;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class ClientNameFragment extends Fragment {
    Bundle bundle = new Bundle();
    private FragmentClientNameBinding binding;
    private static final int READ_CONTACTS_PERMISSION = 2;

    public ClientNameFragment() {
    }

    public static ClientNameFragment newInstance() {
        ClientNameFragment fragment = new ClientNameFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClientNameBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(binding.txtClientName.getText().toString().isEmpty()){
                    Toast.makeText(requireActivity(), "Campo vacio", Toast.LENGTH_SHORT).show();
                }else{
                    Navigation.findNavController(view).navigate(R.id.action_clientNameFragment_to_clientLastNameFragment, bundle);

                }
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddNewClientActivity actividad = (AddNewClientActivity) getActivity();
                if (actividad != null) {
                    actividad.finish();
                }

            }
        });

        binding.btnSearchContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestContactPermission();
            }
        });


    }

    /**
     *Metodos para obtener el contacto del telefono.
     */
    private void requestContactPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_CONTACTS)) {
            Snackbar.make(binding.fragmentClientName, "Requiere aceptar permiso para acceder a contactos ", Snackbar.LENGTH_INDEFINITE).setAction("ACEPTAR", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_PERMISSION );
                }
            }).show();
        }else{
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_PERMISSION );
            accessContacts();
        }
    }

    private void accessContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, READ_CONTACTS_PERMISSION);
    }

    /**
     * Metodos para obtener los datos del contacto.
     */

    private void renderContact(Uri uri) {
        String nombreCompleto = getName(uri);
        String[] partesNombre = nombreCompleto.split(" ");
        String nombre = partesNombre[0];
        String apellido;

        System.out.println("El nombre es " + nombre);
        binding.txtClientName.setText(nombre);
        bundle.putString(Constants.CLIENT_NAME, nombre);


        if (partesNombre.length > 1) {
            apellido = partesNombre[partesNombre.length - 1];
            System.out.println("El apellido es " + apellido);
            bundle.putString(Constants.CLIENT_LAST_NAME, apellido);

        }else{
        }


        String telefono = getPhone(uri);
        if (telefono != null){
            telefono = telefono.replace(" ", "");
            System.out.println("El telefono es " + telefono);
            bundle.putString(Constants.CLIENT_NUMBER, telefono);
        }

    }

        private String getName(Uri uri) {

        String name = null;
        ContentResolver contentResolver = requireActivity().getContentResolver();

        Cursor c = contentResolver.query(
                uri, new String[]{ContactsContract.Contacts.DISPLAY_NAME}, null, null, null);

        if(c.moveToFirst()){
            name = c.getString(0);
        }
        c.close();
        return name;
    }

    private String getPhone(Uri uri) {
        String id = null;
        String phone = null;

        Cursor contactCursor = requireActivity().getContentResolver().query(
                uri, new String[]{ContactsContract.Contacts._ID},
                null, null, null);


        if (contactCursor.moveToFirst()) {
            id = contactCursor.getString(0);
        }
        contactCursor.close();

        String selectionArgs =
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE+"= " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;

        Cursor phoneCursor = requireActivity().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER },
                selectionArgs
                ,
                new String[] { id },
                null
        );
        if (phoneCursor.moveToFirst()) {
            phone = phoneCursor.getString(0);
        }
        phoneCursor.close();

        return phone;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_CONTACTS_PERMISSION && resultCode == RESULT_OK) {
            if (data != null) {
                Uri contactUri = data.getData();
                if (contactUri != null) {
                    renderContact(contactUri);
                }
            }
        }
    }


}