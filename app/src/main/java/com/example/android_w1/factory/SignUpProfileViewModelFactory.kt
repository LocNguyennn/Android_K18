package com.example.android_w1.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_w1.MyApp
import com.example.android_w1.viewModel_Adapter.SignUpProfileViewModel

class SignUpProfileViewModelFactory(val app : MyApp) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpProfileViewModel::class.java)) {
            return SignUpProfileViewModel(app.prefs) as T
        }
        throw IllegalArgumentException("unknown view model")
    }
}