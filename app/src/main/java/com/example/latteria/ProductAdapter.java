package com.example.latteria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private List<Product> products = new ArrayList<>();

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product currentProduct = products.get(position);
        holder.textViewNome.setText(currentProduct.getName());
        holder.textViewDescrizione.setText(currentProduct.getDescription());
        holder.textViewPrezzo.setText(String.valueOf(currentProduct.getSellingPrice()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    class ProductHolder extends RecyclerView.ViewHolder {
        private TextView textViewNome;
        private TextView textViewDescrizione;
        private TextView textViewPrezzo;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.text_view_nome_prodotto);
            textViewDescrizione = itemView.findViewById(R.id.text_view_descrizione);
            textViewPrezzo = itemView.findViewById(R.id.text_view_prezzo);

        }
    }
}
