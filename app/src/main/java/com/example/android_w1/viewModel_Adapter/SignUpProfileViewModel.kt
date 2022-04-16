package com.example.android_w1.viewModel_Adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_w1.MySharedPreferences
import com.example.android_w1.User
import java.util.regex.Pattern


class SignUpProfileViewModel(val sharedPrefs: MySharedPreferences) : ViewModel() {
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

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        val regex =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()])(?=\\S+$).{8,}$")
        return regex.matcher(password).matches()
    }
    fun saveUserInfo(name: String, email : String ,password: String) {
        saveUserName(name)
        saveEmail(email)
        savePassword(password)
    }
    fun loadUserInfo() : User {
        val username = sharedPrefs.getUserName()
        val password = sharedPrefs.getPassword()
        val email = sharedPrefs.getEmail()
        return User(username?:"",email?:"",password?:"")
    }
    fun rememberMe(isRemembered : Boolean){
        sharedPrefs.saveRememberMe(isRemembered)
    }
    fun saveUserName(name : String){
        sharedPrefs.saveUserName(name)
    }
    fun saveEmail(email : String){
        sharedPrefs.saveEmail(email)
    }
    fun savePassword(password : String){
        sharedPrefs.savePassword(password)
    }

}