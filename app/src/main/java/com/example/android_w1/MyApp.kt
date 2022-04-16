package com.example.android_w1

import android.app.Application

class MyApp : Application() {
    lateinit var prefs : MySharedPreferences
    override fun onCreate() {
        super.onCreate()
        prefs = MySharedPreferences()
        prefs.init(this)
    }
}