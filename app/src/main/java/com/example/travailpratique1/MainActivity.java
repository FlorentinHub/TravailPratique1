package com.example.travailpratique1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    private Spinner spinnerRestaurants;
    private TextView tvPlacesRestantes;
    private Button btnReserver;
    private Button btnAfficherReservations;

    private Restaurant selectedRestaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        // Initialisation des vues
        spinnerRestaurants = findViewById(R.id.spinnerRestaurants);
        tvPlacesRestantes = findViewById(R.id.tvPlacesRestantes);
        btnReserver = findViewById(R.id.btnReserver);
        btnAfficherReservations = findViewById(R.id.btnAfficherReservations);

        // Remplissage de la liste déroulante avec des restaurants fictifs
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Restaurant1", "Restaurant2"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRestaurants.setAdapter(adapter);

        // Gestion de la sélection dans la liste déroulante
        spinnerRestaurants.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Récupérer le restaurant sélectionné
                String restaurantName = (String) parent.getItemAtPosition(position);
                // Vous devez implémenter une méthode pour récupérer l'objet Restaurant correspondant
                selectedRestaurant = getRestaurantByName(restaurantName);

                // Mettre à jour le texte des places restantes en fonction du restaurant sélectionné
                int placesRestantes = selectedRestaurant.getNbPlacesRestantes();
                tvPlacesRestantes.setText(getString(R.string.places_restantes, placesRestantes));
                if (placesRestantes <= 4) {
                    tvPlacesRestantes.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                } else {
                    tvPlacesRestantes.setTextColor(getResources().getColor(android.R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ne rien faire ici
            }
        });

        // Gestion du clic sur le bouton "Réserver une table"
        btnReserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRestaurant != null) {
                    // Passer l'objet Restaurant à l'activité de réservation
                    Intent reservationIntent = new Intent(AccueilActivity.this, ReservationActivity.class);
                    reservationIntent.putExtra("selectedRestaurant", selectedRestaurant);
                    startActivity(reservationIntent);
                }
            }
        });

        // Gestion du clic sur le bouton "Afficher les réservations"
        btnAfficherReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vous devez implémenter cette partie pour passer à l'activité d'affichage des réservations
                // en transmettant également l'objet Restaurant sélectionné.
            }
        });
    }

    // Méthode fictive pour récupérer l'objet Restaurant par son nom
    private Restaurant getRestaurantByName(String name) {
        // Vous devez implémenter cette méthode en fonction de votre structure de données
        // (peut-être une liste d'objets Restaurant)
        // Pour l'exemple, nous renvoyons un objet fictif.
        if ("Restaurant1".equals(name)) {
            return new Restaurant(1, "Restaurant1", 30);
        } else if ("Restaurant2".equals(name)) {
            return new Restaurant(2, "Restaurant2", 16);
        } else {
            return null;
        }
    }
}