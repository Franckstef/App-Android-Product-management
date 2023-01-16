package com.franckstefani.e22labo1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProduitAdapter extends RecyclerView.Adapter<ProduitAdapter.ViewHolder> {
    private final List<Produit> listProduits;
    Context context;

    public ProduitAdapter(List<Produit> listProduits, Context context) {
        this.context = context;
        this.listProduits = listProduits;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textID;
        public TextView textNom;
        public TextView textCateg;
        public TextView textPrix;
        public TextView textQte;
        public androidx.constraintlayout.widget.ConstraintLayout ConstraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textID = itemView.findViewById(R.id.idList);
            this.textNom = itemView.findViewById(R.id.nomList);
            this.textCateg = itemView.findViewById(R.id.categorieList);
            this.textPrix = itemView.findViewById(R.id.prixList);
            this.textQte = itemView.findViewById(R.id.qteList);
            ConstraintLayout = itemView.findViewById(R.id.constraintLayout);

            itemView.setOnClickListener(view -> { });
        }
    }

    @NonNull
    @Override
    public ProduitAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.list_item, parent, false);

        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Produit myListData = listProduits.get(position);
        holder.textID.setText(String.valueOf(myListData.get_id()));
        holder.textNom.setText(myListData.getNom());
        holder.textCateg.setText(String.valueOf(myListData.getCateg()));
        holder.textPrix.setText(String.valueOf(myListData.getPrix()));
        holder.textQte.setText(String.valueOf(myListData.getQte()));

        if(position%2 == 0){
            holder.ConstraintLayout.setBackgroundColor(Color.WHITE);
        } else {
            holder.ConstraintLayout.setBackgroundColor(Color.LTGRAY);

        }

        holder.ConstraintLayout.setOnClickListener(v -> {
            /*DataBaseHelper databaseHelper = new DataBaseHelper(v.getContext());
            listProduits.remove(position);
            databaseHelper.delete(myListData.get_id());
            notifyDataSetChanged();*/
            //Toast.makeText(v.getContext(),"click on item: " + myListData.get_id(),Toast.LENGTH_LONG).show();*/
        });
    }

    @Override
    public int getItemCount() {
        return listProduits.size();
    }

}

