package com.example.android_w1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Onboarding2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding_one)
        Handler().postDelayed({

            val intent = Intent(this, Onboarding3Activity::class.java)
            startActivity(intent)
            finish()

        }, 2000)
    }
}