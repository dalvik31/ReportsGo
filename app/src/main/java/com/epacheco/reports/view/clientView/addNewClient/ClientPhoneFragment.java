package com.epacheco.reports.view.clientView.addNewClient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epacheco.reports.R;
import com.epacheco.reports.databinding.FragmentClientNameBinding;
import com.epacheco.reports.databinding.FragmentClientPhoneBinding;
import com.epacheco.reports.tools.Constants;

public class ClientPhoneFragment extends Fragment {
    private String clientPhone;
    private FragmentClientPhoneBinding binding;

    public ClientPhoneFragment() {
    }

    public static ClientPhoneFragment newInstance() {
        ClientPhoneFragment fragment = new ClientPhoneFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClientPhoneBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments().containsKey(Constants.CLIENT_NUMBER)) {
            clientPhone = getArguments().getString(Constants.CLIENT_NUMBER);
            binding.txtClientPhone.setText(clientPhone);
        }

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                assert getArguments() != null;
                bundle.putString(Constants.CLIENT_NAME, getArguments().getString(Constants.CLIENT_NAME));
                bundle.putString(Constants.CLIENT_LAST_NAME, getArguments().getString(Constants.CLIENT_LAST_NAME));
                bundle.putString(Constants.CLIENT_INFORMATION, getArguments().getString(Constants.CLIENT_INFORMATION));
                bundle.putString(Constants.CLIENT_NUMBER, getArguments().getString(Constants.CLIENT_NUMBER));
                Navigation.findNavController(view).navigate(R.id.action_clientPhoneFragment_to_clientCreditFragment,bundle);
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