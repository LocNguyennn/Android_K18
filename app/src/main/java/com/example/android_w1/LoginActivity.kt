package com.example.android_w1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val btnSignUp = findViewById<AppCompatButton>(R.id.btnSignUp_loginPage) // button sign up
        btnSignUp.setOnClickListener {
            val intentSignUp = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intentSignUp)
        }

        val btnLogin = findViewById<AppCompatButton>(R.id.btnLogin_LoginPage) // button login
        btnLogin.setOnClickListener {

        }

    }
}