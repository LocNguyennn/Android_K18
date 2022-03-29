package com.example.android_w1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.android_w1.databinding.ProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ProfileBinding
    private lateinit var viewModel : UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.profile)
        val bundle = intent.extras
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.apply {
            viewModel.user = DataStore("","","")
            binding.user = viewModel.user
            txtEmail.setOnClickListener {
                showDialog(txtEmail)
            }
            txtPassword.setOnClickListener {
                showDialog(txtPassword)
            }
            txtFullName.setOnClickListener {
                showDialog(txtFullName)
            }
            backToMenu()
        }
    }


    private fun showDialog(txtView : TextView){
        val builder = AlertDialog.Builder(this)
        val dialogLayout =layoutInflater.inflate(R.layout.profile_dialog,null)
        val txtBox =dialogLayout.findViewById<EditText>(R.id.editText)
        txtBox.setText(txtView.text)
        with(builder){
            setTitle("Change ${txtView.hint}")
            setMessage("${txtView.hint}")
            setPositiveButton("Save"){dialog, which ->
                txtView.text = txtBox?.text.toString()
                if(txtView.hint.equals("Full name")){
                    viewModel.user.fullName = txtView.text.toString()
                    binding.invalidateAll()
                }
                else if(txtView.hint.equals("Email")){
                    viewModel.user.email = txtView.text.toString()
                    binding.invalidateAll()
                }
                else if(txtView.hint.equals("Password")){
                    viewModel.user.password = txtView.text.toString()
                    binding.invalidateAll()
                }
                dialog.dismiss()
            }
            setNegativeButton("Cancel"){dialog, which ->
                dialog.dismiss()

            }
            setView(dialogLayout)
            show()
        }
    }
    private fun backToMenu(){
        binding.btnBack.setOnClickListener {
            val intentLogin = Intent(this@ProfileActivity, MainMenuActivity::class.java)
            startActivity(intentLogin)
        }
    }
}