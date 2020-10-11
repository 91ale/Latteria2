package com.example.latteria;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment {

    private ProductViewModel productViewModel;
    ProductAdapter adapter = new ProductAdapter();
    String barcode = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        /*productViewModel.getAllProducts().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                adapter.setProducts(products);
            }
        });*/

    }

    public void onMyKeyDown(int key, KeyEvent event){
        Log.d("KeyEvent", String.valueOf(event.getNumber()));
        if (key == KeyEvent.KEYCODE_ENTER) {
            try {
                productViewModel.getProductFromBarcode(barcode).observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
                    @Override
                    public void onChanged(@Nullable List<Product> products) {
                        adapter.setProducts(products);
                    }
                });
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            barcode = "";
        }
        else {
            barcode = barcode + event.getNumber();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.top_menu_spesa, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_spesa) {

            /*Intent intent = new Intent(getActivity(), ScannerActivity.class);
            //startActivityForResult(intent,2);

            //Toast.makeText(getActivity(), "Scansionare codice", Toast.LENGTH_LONG ).show();*/
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 2) {
            if(resultCode == RESULT_OK) {
                String returnString = data.getStringExtra("barcode");
                try {
                    productViewModel.getProductFromBarcode(returnString).observe(getActivity(), new Observer<List<Product>>() {
                        @Override
                        public void onChanged(@Nullable List<Product> products) {
                            adapter.setProducts(products);
                        }
                    });
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }*/

}