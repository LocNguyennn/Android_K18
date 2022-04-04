package com.example.android_w1.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_w1.databinding.FragmentViewPagerBinding
import com.example.android_w1.onboarding.ViewPagerAdapter
import com.example.android_w1.onboarding.screens.Onboarding1
import com.example.android_w1.onboarding.screens.Onboarding2
import com.example.android_w1.onboarding.screens.Onboarding3

class ViewPagerFragment : Fragment() {
    lateinit var binding : FragmentViewPagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewPagerBinding.inflate(inflater,container,false)

        val fragmentList = arrayListOf<Fragment>(
            Onboarding1(),
            Onboarding2(),
            Onboarding3()
        )
        val adapter = ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager,lifecycle)
        binding.viewPager.adapter = adapter
        return binding.root
    }

}