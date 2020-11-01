package com.example.latteria;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.latteria.ui.Insert.InsertViewModel;

import java.util.concurrent.ExecutionException;

import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class InsertFragment extends Fragment {

    TextView textCodiceProdotto;
    EditText editDescrizioneProdotto;
    EditText editPrezzoProdotto;
    EditText editNomeProdotto;
    Button btnAggiungiProdotto;
    private String barcode = "";
    Product product;

    private InsertViewModel insertViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        insertViewModel = new ViewModelProvider(this).get(InsertViewModel.class);
        View root = inflater.inflate(R.layout.fragment_insert, container, false);
        root.requestFocus();
        textCodiceProdotto = root.findViewById(R.id.textCodiceProdotto);
        editDescrizioneProdotto = root.findViewById(R.id.editDescrizioneProdotto);
        editPrezzoProdotto = root.findViewById(R.id.editPrezzoProdotto);
        editNomeProdotto = root.findViewById(R.id.editNomeProdotto);
        btnAggiungiProdotto = root.findViewById(R.id.btnAggiungiProdotto);

        btnAggiungiProdotto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!editNomeProdotto.getText().toString().equals("") && !editPrezzoProdotto.getText().toString().equals("")) {
                    product.setName(editNomeProdotto.getText().toString());
                    product.setSellingPrice(Double.parseDouble(editPrezzoProdotto.getText().toString().replace(",",".")));
                    product.setDescription(editDescrizioneProdotto.getText().toString());
                    insertViewModel.insert(product);
                    Navigation.findNavController(v).navigate(R.id.navigation_insert);
                }
            }
        });

        editDescrizioneProdotto.setEnabled(false);
        editPrezzoProdotto.setEnabled(false);
        editNomeProdotto.setEnabled(false);

        return root;
    }

    public void myDispatchKeyEvent(KeyEvent event) {
        Log.d("KeyEvent", String.valueOf(event.getNumber()));
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                AppExecutors.xDisk(() -> {
                    product = insertViewModel.getSingleProductFromBarcode(barcode);
                    AppExecutors.xMain(() -> {
                        textCodiceProdotto.setText(barcode);
                        barcode = "";
                        if (product != null) {
                            editDescrizioneProdotto.setText(product.getDescription());
                            editPrezzoProdotto.setText(String.valueOf(product.getSellingPrice()).replace(".",","));
                            editNomeProdotto.setText(product.getName());
                        }
                        editDescrizioneProdotto.setEnabled(true);
                        editPrezzoProdotto.setEnabled(true);
                        editNomeProdotto.setEnabled(true);
                    });
                });
            }
        }
        else {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                barcode = barcode + event.getNumber();
            }
        }
    }

    /*public void onMyKeyDown(int key, KeyEvent event) throws ExecutionException, InterruptedException {
        //textCodiceProdotto.setFocusable(true);
        //textCodiceProdotto.requestFocus();
        Log.d("KeyEvent", String.valueOf(event.getNumber()));
        if (key == KeyEvent.KEYCODE_ENTER) {
            AppExecutors.xDisk(() -> {
                product = insertViewModel.getSingleProductFromBarcode(barcode);
                AppExecutors.xMain(() -> {
                    textCodiceProdotto.setText(barcode);
                    barcode = "";
                    if (product != null) {
                        editDescrizioneProdotto.setText(product.getDescription());
                        editPrezzoProdotto.setText(String.valueOf(product.getSellingPrice()).replace(".",","));
                        editNomeProdotto.setText(product.getName());
                    }
                    editDescrizioneProdotto.setEnabled(true);
                    editPrezzoProdotto.setEnabled(true);
                    editNomeProdotto.setEnabled(true);
                });
            });
        }
        else {
            barcode = barcode + event.getNumber();
        }
    }*/
}