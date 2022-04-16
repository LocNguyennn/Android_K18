package com.example.android_w1.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.android_w1.MySharedPreferences
import com.example.android_w1.R
import com.example.android_w1.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    lateinit var binding : FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeBinding.inflate(inflater,container,false)
        if(onLoginSuccess()){
            findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply{
            btnSignUp.setOnClickListener {
                val controller = findNavController()
                controller.navigate(R.id.action_welcomeFragment_to_signUpFragment)
            }
            btnLogin.setOnClickListener {
                val controller = findNavController()
                controller.navigate(R.id.action_welcomeFragment_to_signInFragment)
            }
        }
    }
    private fun onLoginSuccess() : Boolean{
        val sharePreferences = requireActivity().getSharedPreferences("shared_preference", Context.MODE_PRIVATE)
        return sharePreferences.getBoolean("KEY_REMEMBER_ME",false)
    }

}