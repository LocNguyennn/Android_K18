package com.example.android_w1.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.android_w1.*
import com.example.android_w1.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private lateinit var sharePreferences : SharedPreferences
    private lateinit var binding : FragmentSignInBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding = FragmentSignInBinding.inflate(inflater,container,false)
        sharePreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user = DataStore("","","")
        binding.apply {
            btnLogin.setOnClickListener {
                viewModel.checkTrueUser(
                    emailInput.text.toString().trim(),
                    passwordInput.text.toString().trim()
                )
            }
            listenerSuccessEvent()
            listenerErrorEvent()
        }
        binding.btnSignUp.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    private fun listenerSuccessEvent() {
        viewModel.isSuccessEvent.observe(viewLifecycleOwner) {
            if (it) {
                var fullName = viewModel.user.fullName
                val editor : SharedPreferences.Editor = sharePreferences.edit()
                editor.putString("NAME",fullName)
                editor.putString("EMAIL",binding.emailInput.text.toString().trim())
                editor.putString("PASSWORD",binding.passwordInput.text.toString().trim())
                editor.putBoolean("CHECK",true)
                editor.apply()
                val controller = findNavController()
                controller.navigate(R.id.action_signInFragment_to_homeFragment)
            }
        }
    }

    private fun listenerErrorEvent() {
        viewModel.isErrorEvent.observe(viewLifecycleOwner) { errMess ->
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setTitle("Error")
            dialog.setMessage(errMess)
            dialog.show()
        }
    }
}