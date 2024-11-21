package com.example.kawiarnia

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var loginButton: Button
    private lateinit var logoutButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicjalizacja FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Powiązanie widoków
        loginButton = findViewById(R.id.button_login)             // Przycisk do logowania
        logoutButton = findViewById(R.id.logoutButton)           // Przycisk do wylogowania

        // Sprawdzamy, czy użytkownik jest zalogowany
        val currentUser = auth.currentUser
        if (currentUser != null) {
            loginButton.visibility = View.GONE  // Ukrywamy przycisk logowania
            logoutButton.visibility = View.VISIBLE  // Pokazujemy przycisk do wylogowania
        } else {
            // Jeśli użytkownik nie jest zalogowany, pokazujemy tylko przycisk logowania
            loginButton.visibility = View.VISIBLE
            logoutButton.visibility = View.GONE
        }

        // Akcja kliknięcia w przycisk logowania
        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Akcja kliknięcia w przycisk wylogowania
        logoutButton.setOnClickListener {
            auth.signOut()
            // Po wylogowaniu przekierowujemy do ekranu logowania
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()  // Zakończenie MainActivity, aby użytkownik nie wrócił do ekranu głównego po wylogowaniu
        }


        val menuButton: Button = findViewById(R.id.button_menu)
        menuButton.setOnClickListener {
            // Przejście do MenuActivity
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        val uslugiButton:  Button = findViewById(R.id.button_uslugi)
        uslugiButton.setOnClickListener {
            val intent = Intent(this, UslugiActivity::class.java)
            startActivity(intent)
        }
    }
}
