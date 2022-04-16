package com.example.android_w1.fragment

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
import com.example.android_w1.factory.LoginViewModelFactory
import com.example.android_w1.factory.SignUpProfileViewModelFactory
import com.example.android_w1.viewModel_Adapter.LoginViewModel

class SignInFragment : Fragment() {
    private lateinit var binding : FragmentSignInBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this, LoginViewModelFactory(activity?.application as MyApp)).get(
            LoginViewModel::class.java)
        binding = FragmentSignInBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                viewModel.rememberMe(true)
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