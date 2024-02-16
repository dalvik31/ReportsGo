package com.epacheco.reports.view.clientView.addNewClient;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epacheco.reports.R;
import com.epacheco.reports.databinding.FragmentClientCreditBinding;
import com.epacheco.reports.databinding.FragmentClientPhoneBinding;
import com.epacheco.reports.tools.Constants;
import com.epacheco.reports.view.clientView.clientAddView.ClientAddViewClass;

public class ClientCreditFragment extends Fragment {
    private FragmentClientCreditBinding binding;
    public ClientCreditFragment() {
    }

    public static ClientCreditFragment newInstance() {
        ClientCreditFragment fragment = new ClientCreditFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClientCreditBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnCreateClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Bundle bundle = new Bundle();
                assert getArguments() != null;*/

                Intent intent = new Intent(getActivity(), ClientAddViewClass.class);
                intent.putExtra(Constants.CLIENT_NAME, getArguments().getString(Constants.CLIENT_NAME));
                intent.putExtra(Constants.CLIENT_LAST_NAME, getArguments().getString(Constants.CLIENT_LAST_NAME));
                intent.putExtra(Constants.CLIENT_INFORMATION, getArguments().getString(Constants.CLIENT_INFORMATION));
                intent.putExtra(Constants.CLIENT_NUMBER, getArguments().getString(Constants.CLIENT_NUMBER));
                intent.putExtra(Constants.CLIENT_CREDIT, getArguments().getString(Constants.CLIENT_CREDIT));
                startActivity(intent);
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
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

    }
}