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
import com.example.android_w1.databinding.ProfileBinding
import org.w3c.dom.Text

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.profile)
        val bundle = intent.extras
        binding.apply {
            bundle?.let {
                val user: User? = it.getParcelable(Constants.KEY_USER)
                user?.let {
                    txtFullName.text = user.fullName
                    txtEmail.text = user.email
                    txtPassword.text = user.password
                    profile.text = txtFullName.text
                }
            }

            txtEmail.setOnClickListener {
                showDialog(txtEmail, profile)
            }
            txtPassword.setOnClickListener {
                showDialog(txtPassword, profile)
            }
            txtFullName.setOnClickListener {
                showDialog(txtFullName, profile)
            }
            btnBack.setOnClickListener {
                val intentLogin = Intent(this@ProfileActivity, LoginActivity::class.java)
                startActivity(intentLogin)
            }
        }
    }


    fun showDialog(txtView : TextView,txtProfile : TextView){
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
                    txtProfile.text = "Hi, ${txtView.text}"
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