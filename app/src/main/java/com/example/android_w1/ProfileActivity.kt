package com.example.android_w1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_w1.databinding.ProfileBinding
import org.w3c.dom.Text

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ProfileBinding
    private lateinit var viewModel : UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.profile)
        val bundle = intent.extras
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.apply {
            bundle?.let {
                if(viewModel.user.isNull()) {
                    val user: User? = it.getParcelable(Constants.KEY_USER)
                    user?.let {
                        viewModel.user.fullName = user.fullName
                        viewModel.user.email = user.email
                        viewModel.user.password = user.password
                    }
                }
            }
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
            btnBack.setOnClickListener {
                val intentLogin = Intent(this@ProfileActivity, LoginActivity::class.java)
                startActivity(intentLogin)
            }
        }
    }


    fun showDialog(txtView : TextView){
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
}