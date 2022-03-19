package com.example.android_w1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User(var fullName : String, var email: String, var password: String) : Parcelable {
}