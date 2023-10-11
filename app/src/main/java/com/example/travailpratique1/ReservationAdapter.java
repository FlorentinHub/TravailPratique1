package com.example.travailpratique1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ReservationAdapter extends ArrayAdapter<Reservation> {
    private List<Reservation> reservations;
    private Context context;

    public ReservationAdapter(Context context, List<Reservation> reservations) {
        super(context, 0, reservations);
        this.context = context; // Initialize the context variable
        this.reservations = reservations;
    }

    @Override
    public int getCount() {
        return reservations.size();
    }

    public Reservation getReservation(int position) {
        return reservations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.reservation_list_item, parent, false);
        }

        // Additional null checks for safety
        if (convertView == null) {
            return convertView;
        }
        Reservation reservation = reservations.get(position);
        ImageView imageView = convertView.findViewById(R.id.imgReservation);
        TextView nomPersonneTextView = convertView.findViewById(R.id.tvNomPersonne);
        TextView nbPlacesTextView = convertView.findViewById(R.id.tvNbPlaces);
        TextView heuresTextView = convertView.findViewById(R.id.tvHeureDebutFin);

        if (imageView != null) {
            imageView.setImageResource(R.drawable.roundtable);
        }

        if (nomPersonneTextView != null) {
            nomPersonneTextView.setText(reservation.getNomPersonne());
        }

        if (nbPlacesTextView != null) {
            nbPlacesTextView.setText(String.valueOf(reservation.getNbPlace()));
        }

        if (heuresTextView != null) {
            heuresTextView.setText(reservation.getBlocReservationDebut() + " - " + reservation.getBlocReservationFin());
        }

        return convertView;
    }
}
