package com.example.android_w1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.widget.AppCompatButton

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding_one)
        val btnNext = findViewById<AppCompatButton>(R.id.btnNext)
        btnNext.setOnClickListener {
            val intent = Intent(this@OnboardingActivity, Onboarding2Activity::class.java)
            startActivity(intent)
        }
    }
}