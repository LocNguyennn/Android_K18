package com.example.android_w1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        val btnSignUp = findViewById<AppCompatButton>(R.id.btnSignUp_SignUpPage)
        val btnLogin = findViewById<AppCompatButton>(R.id.btnLogin_SignUpPage)
        val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
        btnLogin.setOnClickListener {
            startActivity(intent)
        }
        btnSignUp.setOnClickListener {
            // code nhận thông tin đăng kí ở đâyy


            startActivity(intent)
        }
    }
}