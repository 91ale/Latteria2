package com.example.latteria;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.latteria.ui.Prodotti.ProdottiViewModel;

public class ProdottiFragment extends Fragment {

    private ProdottiViewModel prodottiViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        prodottiViewModel =
                ViewModelProviders.of(this).get(ProdottiViewModel.class);
        View root = inflater.inflate(R.layout.fragment_prodotti, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        prodottiViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}