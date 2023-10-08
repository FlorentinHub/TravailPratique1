package com.example.travailpratique1;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation implements Parcelable {
    private int noReservation;
    private String dateReservation;
    private int nbPlace;
    private String blocReservationDebut;
    private String blocReservationFin;
    private String nomPersonne;
    private String telPersonne;

    public Reservation(int noReservation, String dateReservation, int nbPlace, String blocReservationDebut, String nomPersonne, String telPersonne) {
        this.noReservation = noReservation;
        this.dateReservation = dateReservation;
        this.nbPlace = nbPlace;
        this.blocReservationDebut = blocReservationDebut;
        // Calcul de l'heure de fin
        this.blocReservationFin = calculerHeureFin(blocReservationDebut);
        this.nomPersonne = nomPersonne;
        this.telPersonne = telPersonne;
    }

    protected Reservation(Parcel in) {
        noReservation = in.readInt();
        dateReservation = in.readString();
        nbPlace = in.readInt();
        blocReservationDebut = in.readString();
        blocReservationFin = in.readString();
        nomPersonne = in.readString();
        telPersonne = in.readString();
    }

    public static final Creator<Reservation> CREATOR = new Creator<Reservation>() {
        @Override
        public Reservation createFromParcel(Parcel in) {
            return new Reservation(in);
        }

        @Override
        public Reservation[] newArray(int size) {
            return new Reservation[size];
        }
    };
    @Override
    public String toString() {
        return "Numéro de réservation : " + noReservation + "\n" +
                "Date de réservation : " + dateReservation + "\n" +
                "Nombre de places : " + nbPlace + "\n" +
                "Bloc de réservation (début) : " + blocReservationDebut + "\n" +
                "Bloc de réservation (fin) : " + blocReservationFin + "\n" +
                "Nom de la personne : " + nomPersonne + "\n" +
                "Téléphone de la personne : " + telPersonne;
    }


    public int getNoReservation() {
        return noReservation;
    }

    public void setNoReservation(int noReservation) {
        this.noReservation = noReservation;
    }

    public String getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(String dateReservation) {
        this.dateReservation = dateReservation;
    }

    public int getNbPlace() {
        return nbPlace;
    }

    public void setNbPlace(int nbPlace) {
        this.nbPlace = nbPlace;
    }

    public String getBlocReservationDebut() {
        return blocReservationDebut;
    }

    public void setBlocReservationDebut(String blocReservationDebut) {
        this.blocReservationDebut = blocReservationDebut;
    }

    public String getBlocReservationFin() {
        return blocReservationFin;
    }

    public void setBlocReservationFin(String blocReservationFin) {
        this.blocReservationFin = blocReservationFin;
    }

    public String getNomPersonne() {
        return nomPersonne;
    }

    public void setNomPersonne(String nomPersonne) {
        this.nomPersonne = nomPersonne;
    }

    public String getTelPersonne() {
        return telPersonne;
    }

    public void setTelPersonne(String telPersonne) {
        this.telPersonne = telPersonne;
    }
    private String calculerHeureFin(String heureDebut) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date date = sdf.parse(heureDebut);
            date.setTime(date.getTime() + (1 * 60 * 29 * 1000)); // 1 heure et 29 minutes en millisecondes
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(noReservation);
        dest.writeString(dateReservation);
        dest.writeInt(nbPlace);
        dest.writeString(blocReservationDebut);
        dest.writeString(blocReservationFin);
        dest.writeString(nomPersonne);
        dest.writeString(telPersonne);
    }
}
