package com.example.travailpratique1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.DatePickerDialog;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.text.Editable;

public class ReservationActivity extends AppCompatActivity {

    private EditText etDate, etHeureFin, etNomPersonne, etNumTelephone;
    private Button btnDatePicker, btnFaireReservation;
    private SeekBar seekBarPlaces;
    private TextView tvProgressionPlaces;
    private Spinner spinnerHeureDebut;

    private Calendar calendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        // Initialisation des vues
        etDate = findViewById(R.id.etDate);
        etHeureFin = findViewById(R.id.etHeureFin);
        etNomPersonne = findViewById(R.id.etNomPersonne);
        etNumTelephone = findViewById(R.id.etNumTelephone);
        btnDatePicker = findViewById(R.id.btnDatePicker);
        btnFaireReservation = findViewById(R.id.btnFaireReservation);
        seekBarPlaces = findViewById(R.id.seekBarPlaces);
        tvProgressionPlaces = findViewById(R.id.tvProgressionPlaces);
        spinnerHeureDebut = findViewById(R.id.spinnerHeureDebut);
        // Initialisation du calendrier
        calendar = Calendar.getInstance();

        String[] heuresDebutOptions = {"16:00", "16:30", "17:00", "17:30", "18:00", "19:00", "19:00", "20:00", "20:30", "21:00"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, heuresDebutOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHeureDebut.setAdapter(adapter);


        // Gestion du clic sur le bouton de sélection de la date
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

// Formater le numéro de téléphone 999-999-9999
        etNumTelephone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                StringBuilder formattedNumber = new StringBuilder();
                int digitCount = 0;
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (Character.isDigit(c)) {
                        formattedNumber.append(c);
                        digitCount++;
                        // Ajouter un tiret après de 3 chiffres 2x et 4 1x
                        if (digitCount % 3 == 0 && digitCount < 9) {
                            formattedNumber.append('-');
                        }
                        if(digitCount > 9){
                            break;
                        }
                    }
                }
                etNumTelephone.removeTextChangedListener(this); // EVITER LA boucle infinie!!
                etNumTelephone.setText(formattedNumber.toString());
                etNumTelephone.setSelection(etNumTelephone.getText().length()); // CURSEUR A LA FIN
                etNumTelephone.addTextChangedListener(this); // REMET le TextWatcher
            }
        });


        //Test: Mise à jour de l'heure de fin lorsque la barre de progression change
        SeekBar seekBarPlaces = findViewById(R.id.seekBarPlaces);
        final TextView tvProgressionPlaces = findViewById(R.id.tvProgressionPlaces);
        seekBarPlaces.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String texte = getString(R.string.places_restantes, progress);
                tvProgressionPlaces.setText(texte);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        // Gestion du clic sur le bouton de réservation
        btnFaireReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faireReservation();
            }
        });
    }

    // Méthode pour formater le numéro de téléphone avec des tirets
    private String formatPhoneNumber(String numTelephone) {
        StringBuilder formattedNumber = new StringBuilder();
        int length = numTelephone.length();
        for (int i = 0; i < length; i++) {
            char digit = numTelephone.charAt(i);
            if (Character.isDigit(digit)) {
                formattedNumber.append(digit);
                if ((formattedNumber.length() == 3 || formattedNumber.length() == 7) && i < length - 1) {
                    formattedNumber.append('-');
                }
            }
        }
        return formattedNumber.toString();
    }

    // Afficher le DatePickerDialog
    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateText();
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    // Mettre à jour le champ de date avec la date sélectionnée
    private void updateDateText() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        etDate.setText(sdf.format(calendar.getTime()));
    }

    // Mettre à jour le champ de progression avec la valeur de la SeekBar
    private void updateProgressionText(int progress) {
        tvProgressionPlaces.setText(String.valueOf(progress));
    }

    // Effectuer la réservation
    private void faireReservation() {
        String dateReservation = etDate.getText().toString();
        int nombrePlaces = seekBarPlaces.getProgress();
        String heureDebut = spinnerHeureDebut.getSelectedItem().toString();
        String nom = etNomPersonne.getText().toString().trim();
        String numTelephone = etNumTelephone.getText().toString().trim();

        if (dateReservation.isEmpty()) {
            Toast.makeText(this, "Veuillez sélectionner une date.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nombrePlaces == 0) {
            Toast.makeText(this, "Veuillez sélectionner le nombre de places.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (heureDebut.isEmpty()) {
            Toast.makeText(this, "Veuillez sélectionner l'heure de début.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nom.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer un nom.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (numTelephone.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer un numéro de téléphone.", Toast.LENGTH_SHORT).show();
            return;
        }

        Reservation reservation = new Reservation(0, dateReservation, nombrePlaces, heureDebut, nom, numTelephone);

        // Affichage reserv dans LogCat pour test
        Toast.makeText(this, "Réservation effectuée!", Toast.LENGTH_SHORT).show();
        Log.d("Réservation", "Date: " + reservation.getDateReservation());
        Log.d("Réservation", "Heure de début: " + reservation.getBlocReservationDebut());
        Log.d("Réservation", "Heure de fin: " + reservation.getBlocReservationFin());
        Log.d("Réservation", "Nom: " + reservation.getNomPersonne());
        Log.d("Réservation", "Numéro de téléphone: " + reservation.getTelPersonne());
        Log.d("Réservation", "Nombre de places: " + reservation.getNbPlace());

        // Reset des champs
        resetFields();
    }

    // Méthode pour réinitialiser les champs de l'activité
    private void resetFields() {
        etDate.setText("");
        spinnerHeureDebut.setSelection(0);
        etNomPersonne.setText("");
        etNumTelephone.setText("");
        seekBarPlaces.setProgress(0);
        tvProgressionPlaces.setText(getString(R.string.places_restantes, 0));
    }
}
