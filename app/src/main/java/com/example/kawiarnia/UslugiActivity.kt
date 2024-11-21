package com.example.kawiarnia
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class UslugiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uslugi)

        // Znajdź przycisk powrotu
        val buttonBack: Button = findViewById(R.id.button_back)
        buttonBack.setOnClickListener {
            onBackPressed() // Powrót do poprzedniej aktywności
        }

        // Znajdź przycisk do przejścia do rezerwacji
        val reservationButton: Button = findViewById(R.id.button_reservation)
        reservationButton.setOnClickListener {
            // Przejście do aktywności rezerwacji
            val intent = Intent(this, RezerwacjaActivity::class.java)
            startActivity(intent)
        }
    }
}
