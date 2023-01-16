package com.franckstefani.e22labo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.Objects;

public class ListeSelonCategorie extends AppCompatActivity {

    private ArrayList<Produit> listeProduits;
    final String KEY_RECYCLER = "key_recycler";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie_produit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            listeProduits = savedInstanceState.getParcelableArrayList(KEY_RECYCLER);
        } else {
            Bundle bundle = getIntent().getExtras();
            listeProduits = bundle.getParcelableArrayList("mylist");
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.Adapter adapter = new ProduitAdapter(listeProduits, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(KEY_RECYCLER, listeProduits);
        super.onSaveInstanceState(savedInstanceState);
    }
}