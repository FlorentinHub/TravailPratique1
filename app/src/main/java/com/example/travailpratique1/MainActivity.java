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

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1; //test
    private Spinner spinnerRestaurants;
    private TextView tvPlacesRestantes;
    private Button btnReserver;
    private Button btnAfficherReservations;
    private Restaurant selectedRestaurant;
    private ArrayList<Reservation> reservationList = new ArrayList<>(); //Ma Liste de reservations que je passe dans mes autres activites
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des vues
        spinnerRestaurants = findViewById(R.id.spinnerRestaurants);
        tvPlacesRestantes = findViewById(R.id.tvPlacesRestantes);
        btnReserver = findViewById(R.id.btnReserver);
        btnAfficherReservations = findViewById(R.id.btnAfficherReservations);

        // Remplissage de la liste déroulante avec des restaurants fictifs
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
                if(reservationList.isEmpty()){
                //Envoie juste une notif et ne fait rien
                    Toast.makeText(MainActivity.this, "Aucune reservation dans le systeme", Toast.LENGTH_SHORT).show();
                }else{
                Log.v("ReservationsMain", "Main:"+reservationList.toString());
                Intent viewReservationsIntent = new Intent(MainActivity.this, ViewReservationsActivity.class);
                viewReservationsIntent.putExtra("reservationList", reservationList);
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
        Log.e("test", "RESUME"); //test
        if (reservation == null) {
            Log.e("onActivityResult", "la reservation est nulle:"); //test
        }else{
            Log.e("test", "test" + reservation); //test
            reservationList.add(reservation);
            Log.i("onActivityResult - Liste Activite: ", "reservationList:\n" +reservationList);
        }
    }
}