package com.example.android_w1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val btnSignUp = findViewById<AppCompatButton>(R.id.btnSignUp_loginPage) // button sign up
        btnSignUp.setOnClickListener {
            val intentSignUp = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intentSignUp)
        }

        val btnLogin = findViewById<AppCompatButton>(R.id.btnLogin_LoginPage) // button login
        btnLogin.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.apply {
                title = "Login"
                setMessage("Incorrect email or password")
                setNegativeButton("Return") { dialog, _ ->
                    dialog.dismiss()
                }
            }
            val email = findViewById<TextInputEditText>(R.id.emailInput).text.toString().trim()
            val password = findViewById<TextInputEditText>(R.id.passwordInput).text.toString().trim()
            if (email.equals("ronaldo@gmail.com") && password.equals("123456")) {
                val user = User("",email, password)
                val intentLogin = Intent(this@LoginActivity, ProfileActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelable("user", user)
                intentLogin.putExtras(bundle)
                startActivity(intentLogin)
            }
            else {
                builder.show()
            }

        }

    }
}