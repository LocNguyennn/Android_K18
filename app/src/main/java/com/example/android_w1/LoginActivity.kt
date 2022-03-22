package com.example.android_w1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.android_w1.databinding.LoginBinding
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginBinding
    private lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.login)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.btnSignUp.setOnClickListener {
            val intentSignUp = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intentSignUp)
        }
//        val bundle = intent.extras
//        bundle?.let {
//            val user: User? = it.getParcelable(Constants.KEY_USER)
//            user?.let {
//                viewModel.user.fullName = user.fullName
//                viewModel.user.email = user.email
//                viewModel.user.password = user.password
//            }
//        }
        viewModel.user = DataStore("","","")
        binding.apply {
            btnLogin.setOnClickListener {
                viewModel.checkTrueUser(
                    emailInput.text.toString().trim(),
                    passwordInput.text.toString().trim()
                )
            }
            listenerSuccessEvent()
            listenerErrorEvent()
            val btnBack = findViewById<ImageView>(R.id.btnBack)
            btnBack.setOnClickListener {
                val intentWelcome = Intent(this@LoginActivity, WelcomeActivity::class.java)
                startActivity(intentWelcome)
            }

        }
    }

    private fun listenerSuccessEvent() {
        viewModel.isSuccessEvent.observe(this) {
            if (it) {
                val intentLogin = Intent(this@LoginActivity, ProfileActivity::class.java)
                val bundle = Bundle()
                var fullName = viewModel.user.fullName
                bundle.putParcelable(
                    Constants.KEY_USER, User(
                        fullName,
                        binding.emailInput.text.toString().trim(),
                        binding.passwordInput.text.toString().trim()
                    )
                )
                intentLogin.putExtras(bundle)
                startActivity(intentLogin)
            }
        }
    }

    private fun listenerErrorEvent() {
        viewModel.isErrorEvent.observe(this) { errMess ->
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Error")
            dialog.setMessage(errMess)
            dialog.show()
        }
    }
}