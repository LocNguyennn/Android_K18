package com.example.android_w1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import com.example.android_w1.databinding.SignupBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding : SignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.signup)
        binding.apply {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            btnLogin.setOnClickListener {
                startActivity(intent)
            }
           btnSignUp.setOnClickListener {
                // code nhận thông tin đăng kí ở đâyy

                var user = User(editTextTextPersonName.text.toString().trim(),
                    editTextTextEmailAddress.text.toString().trim(),
                    editTextTextPassword.text.toString().trim())
               val bundle = Bundle()
               bundle.putParcelable(Constants.KEY_USER,user)
               intent.putExtras(bundle)
               Toast.makeText(this@SignUpActivity,"Sign Up Successful",Toast.LENGTH_SHORT).show()
               startActivity(intent)
            }
        }
    }
}