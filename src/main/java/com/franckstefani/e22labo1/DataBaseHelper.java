package com.franckstefani.e22labo1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "bdproduits";
    public static final String TABLE_NAME = "produits";
    public static final String COL_ID = "id";
    public static final String COL_NOM = "nom";
    public static final String COL_CATEG = "categ";
    public static final String COL_PRIX = "prix";
    public static final String COL_QTE = "qte";
    String sql = "CREATE TABLE IF NOT EXISTS produits (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nom TEXT, " +
            "categ TEXT, " +
            "prix FLOAT, " +
            "qte INTEGER)";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sql);

        ContentValues values = new ContentValues();

        values.put(COL_NOM, "Chai");
        values.put(COL_CATEG, "Boissons");
        values.put(COL_PRIX, "90.00");
        values.put(COL_QTE, "39");
        db.insert(TABLE_NAME, "nom", values);

        values.put(COL_NOM, "Chang");
        values.put(COL_CATEG, "Boissons");
        values.put(COL_PRIX, "95.00");
        values.put(COL_QTE, "17");
        db.insert(TABLE_NAME, "nom", values);

        values.put(COL_NOM, "AniseedSyrup");
        values.put(COL_CATEG, "Condiments");
        values.put(COL_PRIX, "50.00");
        values.put(COL_QTE, "13");
        db.insert(TABLE_NAME, "nom", values);

        values.put(COL_NOM, "Chef Anton's Cajun Seasoning");
        values.put(COL_CATEG, "Condiments");
        values.put(COL_PRIX, "110.00");
        values.put(COL_QTE, "53");
        db.insert(TABLE_NAME, "nom", values);

        values.put(COL_NOM, "Chef Anton's Gumbo Mix");
        values.put(COL_CATEG, "Condiments");
        values.put(COL_PRIX, "106.75");
        values.put(COL_QTE, "0");
        db.insert(TABLE_NAME, "nom", values);

        values.put(COL_NOM, "Grandma's Boysenberry Spread");
        values.put(COL_CATEG, "Condiments");
        values.put(COL_PRIX, "125.00");
        values.put(COL_QTE, "120");
        db.insert(TABLE_NAME, "nom", values);

        values.put(COL_NOM, "Uncle Bob's organic Dried Pears");
        values.put(COL_CATEG, "Produits secs");
        values.put(COL_PRIX, "150.00");
        values.put(COL_QTE, "15");
        db.insert(TABLE_NAME, "nom", values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS produits");
        onCreate(db);
    }

    public ArrayList<Produit> obtenirListProduits(){
        ArrayList<Produit> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    String categ = cursor.getString(2);
                    double prix = cursor.getDouble(3);
                    int qte = cursor.getInt(4);
                    Produit newProduct = new Produit(id, name, categ, prix, qte);
                    returnList.add(newProduct);

                } while (cursor.moveToNext());
            }
            cursor.close();

        return returnList;
    }

    public long dbhEnregistrerProduit(String nom, String categ, String prix, String qte){
        SQLiteDatabase dtb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOM, nom);
        values.put(COL_CATEG, categ);
        values.put(COL_PRIX, prix);
        values.put(COL_QTE, qte);

        long id = dtb.insert(TABLE_NAME, null, values);
        return id;
    }

    public List<String> getAllCategory(){
        List<String> labels = new ArrayList<String>();
        SQLiteDatabase dtb = this.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select DISTINCT "+ COL_CATEG +" from " + TABLE_NAME, null);
        labels.add("Choisir une catégorie");

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEG)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        dtb.close();
        return labels;
    }

    /*public void delete(long itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db == null) {
            return;
        }
        db.delete(TABLE_NAME, "_id = ?", new String[] { String.valueOf(itemId) });
        db.close();

    }*/

}
