package com.example.android_w1.viewModel_Adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_w1.MySharedPreferences
import com.example.android_w1.User
import java.util.regex.Pattern

class LoginViewModel(val sharedPrefs: MySharedPreferences) : ViewModel() {
    private var _isSuccessEvent: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccessEvent: LiveData<Boolean>
        get() = _isSuccessEvent

    private var _isErrorEvent: MutableLiveData<String> = MutableLiveData()
    val isErrorEvent: LiveData<String>
        get() = _isErrorEvent

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

    private fun isTrueEmail(emailInput: String): Boolean {
        val email = sharedPrefs.getEmail().toString()
        return emailInput.equals(email)
    }

    private fun isTruePassword(passwordInput: String): Boolean {
        val password = sharedPrefs.getPassword()
        return passwordInput.equals(password)
    }
    fun rememberMe(isRemembered : Boolean){
        sharedPrefs.saveRememberMe(isRemembered)
    }
}