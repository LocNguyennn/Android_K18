package com.example.android_w1.fragment

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
import com.example.android_w1.*
import com.example.android_w1.databinding.FragmentProfileBinding
import com.example.android_w1.factory.SignUpProfileViewModelFactory
import com.example.android_w1.viewModel_Adapter.SignUpProfileViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    private lateinit var viewModel: SignUpProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, SignUpProfileViewModelFactory(activity?.application as MyApp)).get(
            SignUpProfileViewModel::class.java)
        binding.apply {
            binding.user = viewModel.loadUserInfo()
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
                    viewModel.saveUserName(txtView.text.toString().trim())
                }
                else if(txtView.hint.equals("Email")){
                    viewModel.saveEmail(txtView.text.toString().trim())
                }
                else if(txtView.hint.equals("Password")){
                    viewModel.savePassword(txtView.text.toString().trim())
                }
                binding.invalidateAll()
                binding.user = viewModel.loadUserInfo()
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
            viewModel.rememberMe(false)
            val controller = findNavController()
            controller.navigate(R.id.action_profileFragment_to_welcomeFragment)
        }
    }
}