package com.example.travailpratique1;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
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
}
