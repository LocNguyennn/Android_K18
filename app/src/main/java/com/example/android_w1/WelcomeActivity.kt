package com.example.android_w1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.widget.AppCompatButton

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome)
        val btnSignUp = findViewById<AppCompatButton>(R.id.btnSignUp)
        val btnLogin = findViewById<AppCompatButton>(R.id.btnLogin)

        btnSignUp.setOnClickListener {
            val intentSignUp = Intent(this@WelcomeActivity, SignUpActivity::class.java)
            startActivity(intentSignUp)
        }
        btnLogin.setOnClickListener {
            val intentLogin = Intent(this@WelcomeActivity, LoginActivity::class.java)
            startActivity(intentLogin)
        }
    }
}