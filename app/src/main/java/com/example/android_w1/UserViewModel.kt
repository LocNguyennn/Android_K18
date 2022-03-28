package com.example.android_w1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

enum class Error {
    ERROR_EMAIL,
    ERROR_PASSWORD,
}

class Resp(val isSuccess: Boolean, val error: Error?)

class UserViewModel : ViewModel() {
    var user: User = User("", "", "")
    private var _isSuccessEvent: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccessEvent: LiveData<Boolean>
        get() = _isSuccessEvent

    private var _isErrorEvent: MutableLiveData<String> = MutableLiveData()
    val isErrorEvent: LiveData<String>
        get() = _isErrorEvent

    fun checkValidEmailAndPassword(email: String, password: String) {
        //kiem tra format email
        val isValidEmail = isEmailValid(email)
        if (!isValidEmail) {
            _isErrorEvent.postValue("Invalid email")
            return
        }
        //password length > 8 && < 10
        val isValidPassword = isPasswordValid(password)
        if (!isValidPassword) {
            _isErrorEvent.postValue("Password must have at least 8 character (including uppercase, lowercase, special character)")
            return
        }
        _isSuccessEvent.postValue(true)
    }

    fun checkTrueUser(email: String, password: String) {
        //kiem tra format email
        val isTrueEmail = isTrueEmail(email)
        val isValidEmail = isEmailValid(email)
        if (!isValidEmail) {
            _isErrorEvent.postValue("Invalid Email")
            return
        } else if (!isTrueEmail) {
            _isErrorEvent.postValue("Wrong email")
            return
        }
        //password length > 8 && < 10
        val isTruePassword = isTruePassword(password)
        if (!isTruePassword) {
            _isErrorEvent.postValue("Wrong password")
            return
        }
        _isSuccessEvent.postValue(true)
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        val regex =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()])(?=\\S+$).{8,}$")
        return regex.matcher(password).matches()

    }

    private fun isTrueEmail(email: String): Boolean {
        return email.equals(user.email)
    }

    private fun isTruePassword(password: String): Boolean {
        return password.equals(user.password)
    }
}