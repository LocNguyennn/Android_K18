package com.example.android_w1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User(val fullName : String, val email: String, val password: String) : Parcelable {
}