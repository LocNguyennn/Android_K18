package com.example.android_w1.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.android_w1.OnboardingActivity
import com.example.android_w1.R
import com.example.android_w1.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    lateinit var binding : FragmentSplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler().postDelayed({
            if(onBoardingFinished()) {// true
                findNavController().navigate(R.id.action_splash_to_welcomeFragment)
            }
            else{
                findNavController().navigate(R.id.action_splash_to_viewPagerFragment)
            }
        }, 2000) // 2secs

        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater,container,false)

        return binding.root
    }
    private fun onBoardingFinished() : Boolean{
        val sharePref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharePref.getBoolean("Finished",false)
    }

}