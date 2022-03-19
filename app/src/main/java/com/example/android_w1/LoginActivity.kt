package com.example.android_w1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.android_w1.databinding.LoginBinding
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : LoginBinding
    private lateinit var viewModel : UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.login)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

            binding.btnSignUp.setOnClickListener {
                val intentSignUp = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intentSignUp)
            }
            val bundle = intent.extras
            bundle?.let{
                val user : User? = it.getParcelable(Constants.KEY_USER)
                user?.let{
                    viewModel.user.fullName = user.fullName
                    viewModel.user.email = user.email
                    viewModel.user.password = user.password
                }
            }
            binding.user = viewModel.user
            binding.apply {
            btnLogin.setOnClickListener {
                val builder = AlertDialog.Builder(this@LoginActivity)
                builder.apply {
                    title = "Login"
                    setMessage("Incorrect email or password")
                    setNegativeButton("Return") { dialog, _ ->
                        dialog.dismiss()
                    }
                }
                if (emailInput.text.toString().trim().equals(user?.email) && passwordInput.text.toString().trim().equals(user?.password)) {
                    var fullName = viewModel.user.fullName
                    val user = User(fullName,emailInput.text.toString().trim(), passwordInput.text.toString().trim())
                    val intentLogin = Intent(this@LoginActivity, ProfileActivity::class.java)
                    val bundle = Bundle()
                    bundle.putParcelable(Constants.KEY_USER, user)
                    intentLogin.putExtras(bundle)
                    startActivity(intentLogin)
                }
                else {
                    builder.show()
                }

            }
            val btnBack = findViewById<ImageView>(R.id.btnBack)
            btnBack.setOnClickListener {
                val intentWelcome = Intent(this@LoginActivity,WelcomeActivity::class.java)
                startActivity(intentWelcome)
            }

        }
    }
}