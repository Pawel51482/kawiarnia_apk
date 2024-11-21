package com.example.kawiarnia

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RezerwacjaActivity : AppCompatActivity() {

    private lateinit var datePicker: DatePicker
    private lateinit var timeSpinner: Spinner
    private lateinit var tableSpinner: Spinner
    private lateinit var reserveButton: Button
    private lateinit var errorText: TextView
    private lateinit var buttonBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rezerwacja)

        // Powiązanie widoków
        datePicker = findViewById(R.id.date_picker)
        timeSpinner = findViewById(R.id.time_spinner)
        tableSpinner = findViewById(R.id.table_spinner)
        reserveButton = findViewById(R.id.reserve_button)
        errorText = findViewById(R.id.error_text)
        buttonBack = findViewById(R.id.button_back)

        // Przycisk powrotu do UslugiActivity
        buttonBack.setOnClickListener {
            val intent = Intent(this, UslugiActivity::class.java)
            startActivity(intent)
            finish() // Zamknij RezerwacjaActivity, aby użytkownik nie wrócił do niego po naciśnięciu przycisku powrotu
        }

        // Przykładowe dane godzin do wyboru
        val hours = arrayOf("12:00", "14:00", "16:00", "18:00", "20:00")
        val timeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, hours)
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        timeSpinner.adapter = timeAdapter

        // Przykładowe numery stolików
        val tables = arrayOf("Stolik 1", "Stolik 2", "Stolik 3", "Stolik 4", "Stolik 5", "Stolik 6", "Stolik 7", "Stolik 8")
        val tableAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tables)
        tableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tableSpinner.adapter = tableAdapter

        // Obsługa kliknięcia przycisku rezerwacji
        reserveButton.setOnClickListener {

            // Sprawdzamy, czy użytkownik jest zalogowany
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser == null) {
                // Użytkownik nie jest zalogowany, przekierowujemy do logowania
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                return@setOnClickListener
            }

            // Pobieramy dane z formularza
            val selectedDate = "${datePicker.dayOfMonth}-${datePicker.month + 1}-${datePicker.year}"
            val selectedTime = timeSpinner.selectedItem.toString()
            val selectedTable = tableSpinner.selectedItem.toString()

            // Walidacja danych
            if (selectedTime.isEmpty() || selectedTable.isEmpty()) {
                errorText.text = "Wszystkie pola muszą być wypełnione"
                return@setOnClickListener
            }

            // Rezerwacja w Firestore
            val db = FirebaseFirestore.getInstance()
            val reservation = hashMapOf(
                "user_id" to currentUser.uid,
                "date" to selectedDate,
                "time" to selectedTime,
                "table" to selectedTable
            )

            // Sprawdzamy, czy rezerwacja już istnieje
            db.collection("reservations")
                .whereEqualTo("date", selectedDate)
                .whereEqualTo("time", selectedTime)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.isEmpty) {
                        // Brak rezerwacji na tę godzinę i datę, zapisujemy
                        db.collection("reservations")
                            .add(reservation)
                            .addOnSuccessListener {
                                errorText.text = "Rezerwacja udana!"
                            }
                            .addOnFailureListener {
                                errorText.text = "Błąd przy rezerwacji"
                            }
                    } else {
                        // Rezerwacja już istnieje na ten czas
                        errorText.text = "Ta godzina jest już zarezerwowana"
                    }
                }
                .addOnFailureListener {
                    errorText.text = "Błąd przy sprawdzaniu dostępności"
                }
        }
    }
}
