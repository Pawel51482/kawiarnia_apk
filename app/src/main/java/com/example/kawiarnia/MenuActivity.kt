package com.example.kawiarnia

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class MenuActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var coffeePriceTextView: TextView
    private lateinit var getPriceLatteButton: Button
    private lateinit var getPriceEspressoButton: Button
    private lateinit var getPriceCappuccinoButton: Button
    private lateinit var getPriceAmericanoButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Znajdź przycisk powrotu
        val buttonBack: Button = findViewById(R.id.button_back)
        buttonBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Inicjalizacja Firestore
        db = FirebaseFirestore.getInstance()

        // Powiązanie widoków
        coffeePriceTextView = findViewById(R.id.coffeePriceTextView)
        getPriceLatteButton = findViewById(R.id.getPriceLatteButton)
        getPriceEspressoButton = findViewById(R.id.getPriceEspressoButton)
        getPriceCappuccinoButton = findViewById(R.id.getPriceCappuccinoButton)
        getPriceAmericanoButton = findViewById(R.id.getPriceAmericanoButton)

        // Ustawienie kliknięcia przycisków do pobierania cen
        getPriceLatteButton.setOnClickListener { getCoffeePrice("latte") }
        getPriceEspressoButton.setOnClickListener { getCoffeePrice("espresso") }
        getPriceCappuccinoButton.setOnClickListener { getCoffeePrice("cappuccino") }
        getPriceAmericanoButton.setOnClickListener { getCoffeePrice("americano") }
    }

    private fun getCoffeePrice(coffeeName: String) {
        // Odwołanie do kolekcji "coffe" i dokumentu odpowiedniej kawy
        db.collection("coffe")
            .document(coffeeName)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val price = document.getDouble("cena")
                    val formattedPrice = String.format("%.2f", price ?: 0.0)
                    coffeePriceTextView.text = "Cena $coffeeName: $formattedPrice zł"
                } else {
                    coffeePriceTextView.text = "Brak danych dla kawy $coffeeName"
                }
            }
            .addOnFailureListener { exception ->
                coffeePriceTextView.text = "Błąd: ${exception.message}"
            }
    }
}
