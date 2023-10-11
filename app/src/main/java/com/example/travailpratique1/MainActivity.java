package com.example.travailpratique1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1; //test
    private Spinner spinnerRestaurants;
    private TextView tvPlacesRestantes;
    private Button btnReserver;
    private Button btnAfficherReservations;
    private Restaurant selectedRestaurant;
    private ArrayList<Reservation> reservationList = new ArrayList<>(); //Ma Liste de reservations que je passe dans mes autres activites

    private ArrayList<Reservation> reservationListRestaurant1 = new ArrayList<>();
    private ArrayList<Reservation> reservationListRestaurant2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des vues
        spinnerRestaurants = findViewById(R.id.spinnerRestaurants);
        tvPlacesRestantes = findViewById(R.id.tvPlacesRestantes);
        btnReserver = findViewById(R.id.btnReserver);
        btnAfficherReservations = findViewById(R.id.btnAfficherReservations);

        // Remplissage de la liste déroulante avec les noms des restaurants
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Chez Vincent", "La Croisée des Chemins"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRestaurants.setAdapter(adapter);

        // Gestion de la sélection dans la liste déroulante
        spinnerRestaurants.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Récupérer le restaurant sélectionné
                String restaurantName = (String) parent.getItemAtPosition(position);
                selectedRestaurant = getRestaurantByName(restaurantName);

                // Mettre à jour le texte des places restantes en fonction du restaurant sélectionné
                int placesRestantes = selectedRestaurant.getNbPlacesRestantes();
                tvPlacesRestantes.setText(getString(R.string.places_restantes, placesRestantes));

                int textColor;
                if (placesRestantes <= 4) {
                    textColor = getResources().getColor(R.color.couleur_texte_rouge);
                } else {
                    textColor = getResources().getColor(R.color.couleur_texte_darkBlue);
                }
                tvPlacesRestantes.setTextColor(textColor);
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
                    Intent reservationIntent = new Intent(MainActivity.this, ReservationActivity.class);
                    reservationIntent.putExtra("selectedRestaurant", selectedRestaurant);
//                    startActivity(reservationIntent);
                    startActivityForResult(reservationIntent, REQUEST_CODE);
                }
            }
        });

        // Gestion du clic sur le bouton "Afficher les réservations"
        btnAfficherReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Reservation> myList = new ArrayList<>();
                if (selectedRestaurant.getNoRestaurant() == 1) {
                    myList= reservationListRestaurant1; //Vincent
                } else if (selectedRestaurant.getNoRestaurant() == 2) {
                    myList= reservationListRestaurant2; //Croisee
                }
                if(myList.isEmpty()){
                //Envoie juste une notif et ne fait rien
                    Toast.makeText(MainActivity.this, "Aucune reservation dans le systeme", Toast.LENGTH_SHORT).show();
                }else{
                Log.v("ReservationsMain", "Main:\n"+myList.toString());

                    //Affichage pour le user
                    Toast.makeText(MainActivity.this, "Chargement des réservations pour " + selectedRestaurant.getNomRestaurant() + "...", Toast.LENGTH_SHORT).show();

        //ENVOI DE LA BONNE LISTE POUR L'ACTIVITE
                        Intent viewReservationsIntent = new Intent(MainActivity.this, ViewReservationsActivity.class);
                    viewReservationsIntent.putExtra("reservationList", myList);
                    startActivity(viewReservationsIntent);
                }
            }
        });
    }
    private Restaurant getRestaurantByName(String name) {
        if ("Chez Vincent".equals(name)) {
            return new Restaurant(1, "Chez Vincent", 30);
        } else if ("La Croisée des Chemins".equals(name)) {
            return new Restaurant(2, "La Croisée des Chemins", 16);
        } else {
            return null;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Reservation reservation = data.getParcelableExtra("myReservation");
        if (reservation == null) {
            Log.e("onActivityResult", "la reservation est nulle:");
        } else {
            Log.e("test", "test" + reservation); // test
            // Vérifie le restaurant associé à la réservation
            if (selectedRestaurant != null) {
                if (selectedRestaurant.getNoRestaurant() == 1) {
                    reservationListRestaurant1.add(reservation); //Vincent
                    Log.i("reservationListRestaurant1", "reservationListRestaurant1:\n"+reservationListRestaurant1.toString());
                } else if (selectedRestaurant.getNoRestaurant() == 2) {
                    reservationListRestaurant2.add(reservation); //Croisee
                    Log.i("reservationListRestaurant2", "reservationListRestaurant2:\n"+reservationListRestaurant2.toString());
                }
            }
            Log.i("onActivityResult - Liste Activite: ", "reservationList:\n" + reservationList);
        }
    }
}