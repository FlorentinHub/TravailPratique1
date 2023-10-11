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
    private Restaurant resto1 = new Restaurant(1, "Chez Vincent", 30);
    private Restaurant resto2 = new Restaurant(2, "La Croisée des Chemins", 16);
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
                changerNbPlaces();
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
                    myList = reservationListRestaurant1; //Vincent
                } else if (selectedRestaurant.getNoRestaurant() == 2) {
                    myList = reservationListRestaurant2; //Croisee
                }
                if (myList.isEmpty()) {
                    //Envoie juste une notif et ne fait rien
                    Toast.makeText(MainActivity.this, "Aucune reservation dans le systeme", Toast.LENGTH_SHORT).show();
                } else {
                    Log.v("ReservationsMain", "Main:\n" + myList.toString());

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
    public void changerNbPlaces(){
        // Mettre à jour le texte des places restantes en fonction du restaurant sélectionné
        int placesRestantes = selectedRestaurant.getNbPlacesRestantes();
        Log.i("placesRestantes", "placesRestantes: "+placesRestantes);
        tvPlacesRestantes.setText(getString(R.string.places_restantes, placesRestantes));
        //tvPlacesRestantes.setText(getString(R.string.places_restantes, 69));

        //changer couleur Text selon nbPlacesRestantes
        int textColor;
        if (placesRestantes <= 4) {
            textColor = getResources().getColor(R.color.couleur_texte_rouge);
        } else {
            textColor = getResources().getColor(R.color.couleur_texte_darkBlue);
        }
        tvPlacesRestantes.setTextColor(textColor);
    };

    private Restaurant getRestaurantByName(String name) {
        if ("Chez Vincent".equals(name)) {
            return resto1;
        } else if ("La Croisée des Chemins".equals(name)) {
            return resto2;
        } else {
            return null;
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("reservationList", reservationList);
        outState.putParcelableArrayList("reservationListRestaurant1", reservationListRestaurant1);
        outState.putParcelableArrayList("reservationListRestaurant2", reservationListRestaurant2);
        outState.putParcelable("selectedRestaurant", selectedRestaurant);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        reservationList = savedInstanceState.getParcelableArrayList("reservationList");
        reservationListRestaurant1 = savedInstanceState.getParcelableArrayList("reservationListRestaurant1");
        reservationListRestaurant2 = savedInstanceState.getParcelableArrayList("reservationListRestaurant2");

        selectedRestaurant = savedInstanceState.getParcelable("selectedRestaurant");

        updateUI();
    }

    private void updateUI() {
        // Mettre à jour la liste déroulante en fonction du restaurant sélectionné
        if (selectedRestaurant != null) {
            int position = getRestaurantPosition(selectedRestaurant);
            spinnerRestaurants.setSelection(position);
        }
        // Mettre à jour le texte des places restantes
        if (selectedRestaurant != null) {
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

        // Mettre à jour la liste de réservations en fonction du restaurant sélectionné
        List<Reservation> newReservationList = new ArrayList<>();
        if (selectedRestaurant != null) {
            if (selectedRestaurant.getNoRestaurant() == 1) {
                newReservationList = reservationListRestaurant1;
            } else if (selectedRestaurant.getNoRestaurant() == 2) {
                newReservationList = reservationListRestaurant2;
            }
        }
        reservationList.clear();
        reservationList.addAll(newReservationList);
    }
    private int getRestaurantPosition(Restaurant restaurant) {
        // Methode pour obtenir la position du restaurant dans la liste déroulante
        for (int i = 0; i < spinnerRestaurants.getAdapter().getCount(); i++) {
            Restaurant item = (Restaurant) spinnerRestaurants.getItemAtPosition(i);
            if (item.getNoRestaurant() == restaurant.getNoRestaurant()) {
                return i;
            }
        }
        return 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            super.onActivityResult(requestCode, resultCode, data);
            Reservation reservation = data.getParcelableExtra("myReservation");
            if (reservation == null) {
                Log.e("onActivityResult", "la reservation est nulle:");
            } else {
                // Vérifie le restaurant associé à la réservation
                if (selectedRestaurant != null) {
                    if (selectedRestaurant.getNoRestaurant() == 1) {
                        reservationListRestaurant1.add(reservation); //Vincent
                        Log.i("reservationListRestaurant1", "reservationListRestaurant1:\n" + reservationListRestaurant1.toString());
                    } else if (selectedRestaurant.getNoRestaurant() == 2) {
                        reservationListRestaurant2.add(reservation); //Croisee
                        Log.i("reservationListRestaurant2", "reservationListRestaurant2:\n" + reservationListRestaurant2.toString());
                    }
                    Log.i("selectedRestaurant", "selectedRestaurant: " + selectedRestaurant.getNbPlacesRestantes()); //toDelete
                    Log.i("selectedRestaurant", "selectedRestaurant: " + selectedRestaurant.getNomRestaurant()); //toDelete
                    getRestaurantByName(selectedRestaurant.getNomRestaurant()).setNbPlacesRestantes(selectedRestaurant.getNbPlacesRestantes() - reservation.getNbPlace());
                    // Mettez à jour le nombre de places restantes
                    int placesRestantes = selectedRestaurant.getNbPlacesRestantes() - reservation.getNbPlace();
                    selectedRestaurant.setNbPlacesRestantes(placesRestantes);
                    changerNbPlaces();
                    Log.i("selectedRestaurant", "selectedRestaurant: " + selectedRestaurant.getNbPlacesRestantes());//toDelete
                }
                Log.i("onActivityResult - Liste Activite: ", "reservationList:\n" + reservationList);
            }
        }
    }
}