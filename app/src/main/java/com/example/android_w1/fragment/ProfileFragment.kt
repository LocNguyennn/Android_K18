package com.example.android_w1.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android_w1.DataStore
import com.example.android_w1.R
import com.example.android_w1.User
import com.example.android_w1.UserViewModel
import com.example.android_w1.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var sharePreferences : SharedPreferences
    private lateinit var binding : FragmentProfileBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        sharePreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val name = sharePreferences.getString("NAME","")
        val email = sharePreferences.getString("EMAIL","")
        val password = sharePreferences.getString("PASSWORD","")
        binding.apply {
            viewModel.user = User(name.toString(),email.toString(),password.toString())
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
            setLogOut()
        }
    }

    private fun showDialog(txtView : TextView){
        val builder = AlertDialog.Builder(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.profile_dialog,null)
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
    private fun setLogOut(){
        binding.btnLogOut.setOnClickListener {
            val editor : SharedPreferences.Editor = sharePreferences.edit()
            editor.clear()
            editor.apply()
            val controller = findNavController()
            controller.navigate(R.id.action_profileFragment_to_welcomeFragment)
        }
    }
}