package com.franckstefani.e22labo1;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ArrayList<Produit> listeProduits, listeCategorie;
    private DataBaseHelper myDB;
    private TextView total;
    private EditText nom, prix, qte, categ;
    private Spinner spinChoix, spinChoix2;
    private View groupeBoutons, groupeLogo;
    private String spinnerValue, spinnerValue1, KEY_RECYCLER = "key_recycler";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        total = findViewById(R.id.textTotal);
        spinChoix = findViewById(R.id.spinner);
        groupeBoutons = findViewById(R.id.groupeBoutons);
        groupeLogo = findViewById(R.id.groupeLogoTitre);

        this.configureDrawerLayout();
        this.configureNavigationView();

        listeProduits = new ArrayList<>();
        listeCategorie = new ArrayList<>();
        myDB= new DataBaseHelper(this);
        chargerProduits();
    }

    private void configureDrawerLayout(){
        this.drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void configureSpinner() {
        List<String> labels = myDB.getAllCategory();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinChoix.setAdapter(dataAdapter);

        this.spinChoix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextSize(20);
                spinnerValue = spinChoix.getSelectedItem().toString();
                if(!spinnerValue.equals("Choisir une catégorie")) {
                    listeProduitsSelonCategorie(spinnerValue);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (this.drawer.isDrawerOpen(GravityCompat.START)) {
            this.drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.ajouter:
                enregistrerProduit();
                break;
            case R.id.lister:
                listerProduits();
                break;
            case R.id.categorie:
                groupeLogo.setVisibility(View.GONE);
                total.setVisibility(View.GONE);
                groupeBoutons.setVisibility(View.GONE);
                spinChoix.setVisibility(View.VISIBLE);
                spinChoix.setSelection(0);
                configureSpinner();
                break;
            case R.id.total:
                totalInventaire();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        this.drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void chargerProduits() {
        listeProduits = myDB.obtenirListProduits();
    }

    private void enregistrerProduit() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_ajouter);

        spinChoix2 = dialog.findViewById(R.id.spinner);
        nom = dialog.findViewById(R.id.editTextNom);
        prix = dialog.findViewById(R.id.editTextPrix);
        qte = dialog.findViewById(R.id.editTextQuantité);
        categ = dialog.findViewById(R.id.editTextCateg);
        Button ajouter = dialog.findViewById(R.id.ajouter);
        Button cancel = dialog.findViewById(R.id.cancel);

        List<String> labels = myDB.getAllCategory();
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, labels);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        labels.add(1, "Ajouter une catégorie");
        spinChoix2.setAdapter(adapter2);

        this.spinChoix2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //((TextView) parent.getChildAt(1)).setTextColor(Color.RED);
                spinnerValue1 = spinChoix2.getSelectedItem().toString();
                if(spinChoix2.getSelectedItem().toString().equals("Ajouter une catégorie")) {
                    spinChoix2.setVisibility(View.GONE);
                    categ.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

            ajouter.setOnClickListener(view -> {
                boolean isValid = true;

                if (nom.getText().toString().isEmpty()) {
                    nom.setError("Nom requis");
                    isValid = false;
                }
                if (spinChoix2.getSelectedItemPosition() == 0) {
                    ((TextView) spinChoix2.getSelectedView()).setError("Choisissez une catégorie");
                    isValid = false;
                }
                if (spinChoix2.getSelectedItemPosition() == 1 && categ.getText().toString().isEmpty()) {
                    categ.setError("Entrez une catégorie");
                    isValid = false;
                }
                if (prix.getText().toString().isEmpty()) {
                    prix.setError("Prix requis");
                    isValid = false;
                }
                if (qte.getText().toString().isEmpty()) {
                    qte.setError("Quantité requise");
                    isValid = false;
                }

                if (isValid) {
                    long id;
                    if(spinChoix2.getSelectedItemPosition() == 1) {
                        id = myDB.dbhEnregistrerProduit(nom.getText().toString(), categ.getText().toString(), prix.getText().toString(), qte.getText().toString());
                    } else {
                        id = myDB.dbhEnregistrerProduit(nom.getText().toString(), spinnerValue1, prix.getText().toString(), qte.getText().toString());
                    }
                    chargerProduits();
                    listerProduits();
                    dialog.dismiss();
                    Toast.makeText(this, "Le produit " + id + " a bien été enregistré !", Toast.LENGTH_LONG).show();
                }
            });

            cancel.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
    }

    public void listerProduits() {
        groupeLogo.setVisibility(View.GONE);
        total.setVisibility(View.GONE);
        spinChoix.setVisibility(View.GONE);
        groupeBoutons.setVisibility(View.VISIBLE);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ProduitAdapter adapter = new ProduitAdapter(listeProduits, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void listeProduitsSelonCategorie(String sValue) {
        for(Produit item : listeProduits) {
            if(item.getCateg().equals(sValue)) {
                listeCategorie.add(item);
            }
        }
        Intent intent = new Intent(this, ListeSelonCategorie.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("mylist", listeCategorie);
        intent.putExtras(bundle);
        startActivity(intent);
        listeCategorie.clear();
    }

    private void totalInventaire() {
        groupeLogo.setVisibility(View.GONE);
        groupeBoutons.setVisibility(View.GONE);
        spinChoix.setVisibility(View.GONE);
        total.setVisibility(View.VISIBLE);
        double somme = 0;
        for(Produit item : listeProduits) {
            double px = item.getPrix();
            int qte = item.getQte();
            somme += px * qte;
        }
        total.setTextColor(Color.DKGRAY);
        total.setText("Le montant total de l'inventaire est: \n\n" + somme + " $");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(KEY_RECYCLER, listeProduits);
        super.onSaveInstanceState(savedInstanceState);
    }
}