package com.example.android_w1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.android_w1.databinding.SignupBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: SignupBinding
    private lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.signup)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding.apply {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            btnLogin.setOnClickListener {
                startActivity(intent)
            }

            btnSignUp.setOnClickListener {
                // code nhận thông tin đăng kí ở đây
                viewModel.checkValidEmailAndPassword(
                    edtEmail.text.toString().trim(),
                    edtPassword.text.toString().trim()
                )
            }
            // lắng nghe sự kiện đki thành công
            listenerSuccessEvent()
            // lắng nghe sự kiện báo lỗi
            listenerErrorEvent()
        }
    }

    private fun listenerSuccessEvent() {
        viewModel.isSuccessEvent.observe(this) {
            if (it) {
                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelable(
                    Constants.KEY_USER, User(
                        binding.edtFullName.text.toString().trim(),
                        binding.edtEmail.text.toString().trim(),
                        binding.edtPassword.text.toString().trim()
                    )
                )
                intent.putExtras(bundle)
                Toast.makeText(this@SignUpActivity, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                startActivity(intent)
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