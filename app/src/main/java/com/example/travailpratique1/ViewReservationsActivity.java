package com.example.travailpratique1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewReservationsActivity extends AppCompatActivity {

    private Spinner spinnerDates;
    private ListView listViewReservations;
    private List<Reservation> reservations;
    private ArrayAdapter<String> dateAdapter;
    private ReservationAdapter reservationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reservations);

        reservations = getIntent().getParcelableArrayListExtra("reservationList");
//        Log.i("ReservationList", "reservationList"+reservations.toString());

        //Initiations by ID
        spinnerDates = findViewById(R.id.spinnerDates);
        listViewReservations = findViewById(R.id.listViewReservations);

        // Adapter pour les dates dans le spinner
        dateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getDistinctDates());
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDates.setAdapter(dateAdapter);

        // Adapter pour la ListView personnalisée
        reservationAdapter = new ReservationAdapter(this, reservations);
        listViewReservations.setAdapter(reservationAdapter);

        // Tri des réservations par heure de début
        Collections.sort(reservations, new Comparator<Reservation>() {
            @Override
            public int compare(Reservation r1, Reservation r2) {
                return r1.getHeureDebut().compareTo(r2.getHeureDebut());
            }
        });

        // Gestion de la sélection dans le spinner
        spinnerDates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDate = parent.getItemAtPosition(position).toString();
                displayReservationsForDate(selectedDate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ne rien faire ici
            }
        });

        // Gestion du clic sur un élément de la liste
        listViewReservations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reservation selectedReservation = reservationAdapter.getReservation(position);
                String message = "Numéro de réservation : " + selectedReservation.getNoReservation() +
                        "\nNuméro de téléphone : " + selectedReservation.getTelPersonne();
                Toast.makeText(ViewReservationsActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Méthode pour afficher les réservations pour une date donnée
    private void displayReservationsForDate(String date) {
        if (reservations != null) {
            List<Reservation> reservationsForDate = new ArrayList<>();
            for (Reservation reservation : reservations) {
                if (reservation.getDateReservation().equals(date)) {
                    reservationsForDate.add(reservation);
                }
            }
            reservationAdapter.clear();
            reservationAdapter.addAll(reservationsForDate);
        } else {
            //la liste de réservations est nulle
            Log.v("Reservations vides", "La liste de reservation est vide");
        }
    }


    // Méthode pour obtenir une liste de dates distinctes à partir des réservations
    private List<String> getDistinctDates() {
        List<String> dates = new ArrayList<>();
        Log.e("Reservtest", "test: "+reservations.toString());
        for (Reservation reservation : reservations) {
            if (!dates.contains(reservation.getDateReservation())) {
                dates.add(reservation.getDateReservation());
            }
        }
        Collections.sort(dates); // Trie les dates en ordre croissant
        return dates;
    }
}
