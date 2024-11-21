package com.example.kawiarnia

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var userEmailTextView: TextView
    private lateinit var loginButton: Button
    private lateinit var logoutButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicjalizacja FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Powiązanie widoków
        userEmailTextView = findViewById(R.id.userEmailTextView)  // TextView, w którym wyświetlisz e-mail użytkownika
        loginButton = findViewById(R.id.button_login)             // Przycisk do logowania
        logoutButton = findViewById(R.id.logoutButton)           // Przycisk do wylogowania

        // Sprawdzamy, czy użytkownik jest zalogowany
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Jeśli użytkownik jest zalogowany, wyświetlamy jego e-mail
            userEmailTextView.text = "Zalogowano jako: ${currentUser.email}"
            loginButton.visibility = View.GONE  // Ukrywamy przycisk logowania
            logoutButton.visibility = View.VISIBLE  // Pokazujemy przycisk do wylogowania
        } else {
            // Jeśli użytkownik nie jest zalogowany, pokazujemy tylko przycisk logowania
            userEmailTextView.text = "Nie jesteś zalogowany"
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
    }
}
