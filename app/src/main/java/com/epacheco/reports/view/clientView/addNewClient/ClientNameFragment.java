package com.epacheco.reports.view.clientView.addNewClient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epacheco.reports.R;
import com.epacheco.reports.databinding.FragmentClientNameBinding;

public class ClientNameFragment extends Fragment {
    private FragmentClientNameBinding binding;
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

    }
}