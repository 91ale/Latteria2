package com.example.latteria;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.latteria.ui.Insert.InsertViewModel;
import com.example.latteria.ui.Spesa.SpesaViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class InsertFragment extends Fragment {

    TextView textCodiceProdotto;
    EditText editDescrizioneProdotto;
    EditText editPrezzoProdotto;
    EditText editNomeProdotto;
    private String barcode = "";

    private InsertViewModel insertViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        insertViewModel = new ViewModelProvider(this).get(InsertViewModel.class);
        View root = inflater.inflate(R.layout.fragment_insert, container, false);
        textCodiceProdotto = root.findViewById(R.id.textCodiceProdotto);
        editDescrizioneProdotto = root.findViewById(R.id.editDescrizioneProdotto);
        editPrezzoProdotto = root.findViewById(R.id.editPrezzoProdotto);
        editNomeProdotto = root.findViewById(R.id.editNomeProdotto);
        editDescrizioneProdotto.setEnabled(false);
        editPrezzoProdotto.setEnabled(false);
        editNomeProdotto.setEnabled(false);
        textCodiceProdotto.setFocusable(true);
        textCodiceProdotto.requestFocus();
        return root;
    }

    public void onMyKeyDown(int key, KeyEvent event) throws ExecutionException, InterruptedException {
        textCodiceProdotto.setFocusable(true);
        textCodiceProdotto.requestFocus();
        Log.d("KeyEvent", String.valueOf(event.getNumber()));
        if (key == KeyEvent.KEYCODE_ENTER) {
            AppExecutors.xDisk(() -> {
                Product product = insertViewModel.getSingleProductFromBarcode(barcode);
                AppExecutors.xMain(() -> {
                    textCodiceProdotto.setText(barcode);
                    barcode = "";
                    editDescrizioneProdotto.setEnabled(true);
                    editPrezzoProdotto.setEnabled(true);
                    editNomeProdotto.setEnabled(true);
                });
            });
        }
        else {
            barcode = barcode + event.getNumber();
        }
    }
}