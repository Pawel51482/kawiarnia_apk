package com.example.kawiarnia

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var goToRegisterButton: Button
    private lateinit var goToRegisterText: TextView

    private lateinit var registerEmailEditText: EditText
    private lateinit var registerPasswordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var goToLoginButton: Button
    private lateinit var goToLoginText: TextView

    private lateinit var loginLayout: View
    private lateinit var registerLayout: View

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicjalizacja FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Powiązanie widoków dla logowania
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginButton)
        goToRegisterButton = findViewById(R.id.goToRegisterButton)
        goToRegisterText = findViewById(R.id.goToRegisterText)

        // Powiązanie widoków dla rejestracji
        registerEmailEditText = findViewById(R.id.registerEmail)
        registerPasswordEditText = findViewById(R.id.registerPassword)
        confirmPasswordEditText = findViewById(R.id.confirmPassword)
        registerButton = findViewById(R.id.registerButton)
        goToLoginButton = findViewById(R.id.goToLoginButton)
        goToLoginText = findViewById(R.id.goToLoginText)

        // Powiązanie z layoutami
        loginLayout = findViewById(R.id.loginLayout)
        registerLayout = findViewById(R.id.registerLayout)

        // Akcja logowania
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Zalogowano pomyślnie", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Błąd logowania: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Wszystkie pola muszą być wypełnione", Toast.LENGTH_SHORT).show()
            }
        }

        // Przejście do rejestracji
        goToRegisterButton.setOnClickListener {
            loginLayout.visibility = View.GONE
            registerLayout.visibility = View.VISIBLE
        }

        goToRegisterText.setOnClickListener {
            loginLayout.visibility = View.GONE
            registerLayout.visibility = View.VISIBLE
        }

        // Akcja rejestracji
        registerButton.setOnClickListener {
            val email = registerEmailEditText.text.toString().trim()
            val password = registerPasswordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Zarejestrowano pomyślnie", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Błąd rejestracji: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Wszystkie pola muszą być wypełnione oraz hasła muszą się zgadzać", Toast.LENGTH_SHORT).show()
            }
        }

        // Powrót do logowania
        goToLoginButton.setOnClickListener {
            registerLayout.visibility = View.GONE
            loginLayout.visibility = View.VISIBLE
        }

        goToLoginText.setOnClickListener {
            registerLayout.visibility = View.GONE
            loginLayout.visibility = View.VISIBLE
        }
    }
}
