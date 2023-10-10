package com.example.travailpratique1;

import java.util.ArrayList;
import java.util.List;

public class ReservationList {
    private List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Reservation reservation : reservations) {
            stringBuilder.append("Numéro de réservation : ").append(reservation.getNoReservation()).append("\n");
            stringBuilder.append("Date de réservation : ").append(reservation.getDateReservation()).append("\n");
            stringBuilder.append("Nombre de places : ").append(reservation.getNbPlace()).append("\n");
            stringBuilder.append("Heure de début : ").append(reservation.getHeureDebut()).append("\n");
            stringBuilder.append("Nom : ").append(reservation.getNomPersonne()).append("\n");
            stringBuilder.append("Numéro de téléphone : ").append(reservation.getTelPersonne()).append("\n");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}

