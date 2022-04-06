package com.example.android_w1.signInAndSignUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_w1.R
import com.example.android_w1.databinding.FragmentViewPagerSignUpSignInBinding
import com.example.android_w1.fragment.SignInFragment
import com.example.android_w1.fragment.SignUpFragment
import com.example.android_w1.onboarding.ViewPagerAdapter

class ViewPagerSignUpSignIn : Fragment() {
    private lateinit var binding : FragmentViewPagerSignUpSignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewPagerSignUpSignInBinding.inflate(inflater,container,false)
        val fragmentList = arrayListOf<Fragment>(
            SignInFragment(),
            SignUpFragment()
        )
        val adapter = ViewPagerAdapter(fragmentList,requireActivity().supportFragmentManager,lifecycle)
        binding.viewPager2.adapter = adapter
        return binding.root

    }

}