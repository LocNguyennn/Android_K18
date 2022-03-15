package com.example.android_w1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        val btnSignUp = findViewById<Button>(R.id.btnSignUp_SignUpPage)
        val btnLogin = findViewById<TextView>(R.id.btnLogin_SignUpPage)
        val intentLogin = Intent(this@SignUpActivity, LoginActivity::class.java)
        btnSignUp.setOnClickListener {
            //code nhận các thuộc tính edit text ở đây

            startActivity(intentLogin)
        }

        btnLogin.setOnClickListener {
            startActivity(intentLogin)
        }

    }
}