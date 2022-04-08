package com.example.android_w1.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.android_w1.ProfileActivity
import com.example.android_w1.R
import com.example.android_w1.databinding.FragmentHomeBinding

class Home : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openProfile()
    }

    private fun openProfile(){
        binding.ivProfile.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }

}