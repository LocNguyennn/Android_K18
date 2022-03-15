package com.example.android_w1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import org.w3c.dom.Text

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)
        val bundle = intent.extras
        val txtFullName = findViewById<TextView>(R.id.txtFullName)
        val txtEmail = findViewById<TextView>(R.id.txtEmail)
        val txtPassword = findViewById<TextView>(R.id.txtPassword)
        val txtProfle = findViewById<TextView>(R.id.profile)
        bundle?.let{
           val user : User? = it.getParcelable("user")
            user?.let{
                txtFullName.text = user.fullName
                txtEmail.text = user.email
                txtPassword.text = user.password
                txtProfle.text = txtFullName?.text
            }
        }
        txtEmail.setOnClickListener {
            showDialog(txtEmail,txtProfle)
        }
        txtPassword.setOnClickListener {
            showDialog(txtPassword,txtProfle)
        }
        txtFullName.setOnClickListener {
            showDialog(txtFullName,txtProfle)
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