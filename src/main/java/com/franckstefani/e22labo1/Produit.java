package com.franckstefani.e22labo1;

import android.os.Parcel;
import android.os.Parcelable;

public class Produit implements Parcelable {

    private int _id;
    private String nom;
    private String categ;
    private double prix;
    private int qte;
    private static int nbProduits = 0;

    public Produit(int id, String nom, String categ, double prix, int qte) {
        this._id = id;
        this.nom = nom;
        this. categ = categ;
        this.prix = prix;
        this.qte = qte;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCateg() {
        return categ;
    }

    public void setCateg(String categ) {
        this.categ = categ;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public String toString() {
        return "Ref prod: " + this._id +
                "Nom: " + this.nom +
                "Catégorie: " + this.categ +
                "Prix unitaire:" + this.prix +
                "Quantité:" + this.qte;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private Produit(Parcel in) {
        _id = in.readInt();
        nom = in.readString();
        categ = in.readString();
        prix = in.readDouble();
        qte = in.readInt();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(_id);
        out.writeString(nom);
        out.writeString(categ);
        out.writeDouble(prix);
        out.writeInt(qte);
    }

    public static final Parcelable.Creator<Produit> CREATOR = new Parcelable.Creator<Produit>() {
        public Produit createFromParcel(Parcel in) {
            return new Produit(in);
        }
        public Produit[] newArray(int size) {
            return new Produit[size];
        }
    };
}

