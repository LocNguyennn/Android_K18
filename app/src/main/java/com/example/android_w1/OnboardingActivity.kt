package com.example.android_w1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding_one)
        Handler().postDelayed({

            val intent = Intent(this, Onboarding2Activity::class.java)
            startActivity(intent)
            finish()

        }, 2000)
    }
}