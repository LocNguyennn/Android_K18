package com.example.android_w1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User(val email: String, val password: String) : Parcelable {
}