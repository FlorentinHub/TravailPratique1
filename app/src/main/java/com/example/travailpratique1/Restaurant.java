package com.example.travailpratique1;

import android.os.Parcelable;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Restaurant implements Parcelable {
    private int noRestaurant;
    private String nomRestaurant;
    private int nbPlacesMax;
    private int nbPlacesRestantes;

    public Restaurant(int noRestaurant, String nomRestaurant, int nbPlacesMax) {
        this.noRestaurant = noRestaurant;
        this.nomRestaurant = nomRestaurant;
        this.nbPlacesMax = nbPlacesMax;
        this.nbPlacesRestantes = nbPlacesMax; // Au debut, le nombre de places restantes sera égal au maximum possible (logique)
    }

    protected Restaurant(Parcel in) {
        noRestaurant = in.readInt();
        nomRestaurant = in.readString();
        nbPlacesMax = in.readInt();
        nbPlacesRestantes = in.readInt();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public int getNoRestaurant() {
        return noRestaurant;
    }

    public void setNoRestaurant(int noRestaurant) {
        this.noRestaurant = noRestaurant;
    }

    public String getNomRestaurant() {
        return nomRestaurant;
    }

    public void setNomRestaurant(String nomRestaurant) {
        this.nomRestaurant = nomRestaurant;
    }

    public int getNbPlacesMax() {
        return nbPlacesMax;
    }

    public void setNbPlacesMax(int nbPlacesMax) {
        this.nbPlacesMax = nbPlacesMax;
    }

    public int getNbPlacesRestantes() {
        return nbPlacesRestantes;
    }

    public void setNbPlacesRestantes(int nbPlacesRestantes) {
        this.nbPlacesRestantes = nbPlacesRestantes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(noRestaurant);
        dest.writeString(nomRestaurant);
        dest.writeInt(nbPlacesMax);
        dest.writeInt(nbPlacesRestantes);
    }
}

