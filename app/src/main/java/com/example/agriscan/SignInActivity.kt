package com.example.agriscan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.agriscan.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    private val PREFS_NAME = "MyPrefs"
    private val KEY_REMEMBER_ME = "remember_me"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean(KEY_REMEMBER_ME, false)) {
            goToMainActivity()
        }

        val emailFromSignUp = intent.getStringExtra("email")
        val passwordFromSignUp = intent.getStringExtra("password")

        if (!emailFromSignUp.isNullOrEmpty() && !passwordFromSignUp.isNullOrEmpty()) {
            binding.etEmail.setText(emailFromSignUp)
            binding.etPassword.setText(passwordFromSignUp)
            Toast.makeText(
                this,
                "Account created successfully. Please sign in.",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.etEmail.error = "Email is required"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.etPassword.error = "Password is required"
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (binding.cbRememberMe.isChecked) {
                        sharedPreferences.edit()
                            .putBoolean(KEY_REMEMBER_ME, true)
                            .apply()
                    }
                    goToMainActivity()
                } else {
                    val errorMessage = when {
                        task.exception?.message?.contains("There is no user record") == true -> "Email is not registered"
                        task.exception?.message?.contains("The password is invalid") == true -> "Incorrect password"
                        else -> task.exception.toString()
                    }
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    
    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}