package com.example.android_w1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.widget.AppCompatButton

class Onboarding3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding_three)
        val btnNext = findViewById<AppCompatButton>(R.id.btnNext)
        btnNext.setOnClickListener {
            val intent = Intent(this@Onboarding3Activity, WelcomeActivity::class.java)
            startActivity(intent)
        }
    }
}