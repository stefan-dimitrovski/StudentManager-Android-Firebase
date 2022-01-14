package com.stefan.lab4.ui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.stefan.lab4.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val emailValue = binding.etUsername.text.toString()
            val passwordValue = binding.etPassword.text.toString()

            if (emailValue.isNullOrEmpty() || passwordValue.isNullOrEmpty()) {
                Toast.makeText(this, "Fields Empty!", Toast.LENGTH_SHORT).show()
            } else {
                login(emailValue, passwordValue)
            }
        }

        binding.btnRegister.setOnClickListener {
            val emailValue = binding.etUsername.text.toString()
            val passwordValue = binding.etPassword.text.toString()

            if (emailValue.isNullOrEmpty() || passwordValue.isNullOrEmpty()) {
                Toast.makeText(this, "Fields Empty!", Toast.LENGTH_SHORT).show()
            } else {
                register(emailValue, passwordValue)
            }
        }

    }

    private fun register(emailValue: String, passwordValue: String) {
        mAuth.createUserWithEmailAndPassword(emailValue, passwordValue)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Register success.",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun login(emailValue: String, passwordValue: String) {
        mAuth.signInWithEmailAndPassword(emailValue, passwordValue)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    navigateOut()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()

        if(checkIfLoggedIn()) {
            navigateOut()
        }

    }

    private fun checkIfLoggedIn(): Boolean {
        return mAuth.currentUser != null
    }

    private fun navigateOut() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}