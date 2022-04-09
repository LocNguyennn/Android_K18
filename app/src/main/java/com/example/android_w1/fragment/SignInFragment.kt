package com.example.android_w1.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.android_w1.*
import com.example.android_w1.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private lateinit var binding : FragmentSignInBinding
    private lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding = FragmentSignInBinding.inflate(inflater,container,false)
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
            binding.btnBack.setOnClickListener {
                val controller = findNavController()
//                controller.navigate(R.id.ac)
            }
        }
        binding.btnSignUp.setOnClickListener {
//            val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager2)
            val controller = findNavController()
            controller.navigate(R.id.action_signInFragment_to_signUpFragment)
//            viewPager?.currentItem = 1
        }
    }

    private fun listenerSuccessEvent() {
        viewModel.isSuccessEvent.observe(viewLifecycleOwner) {
            if (it) {
                val intentLogin = Intent(context, Home::class.java)
                val controller = findNavController()
                controller.navigate(R.id.action_signInFragment_to_homeFragment)
//                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                var fullName = viewModel.user.fullName
                bundle.putParcelable(
                    Constants.KEY_USER, User(
                        fullName,
                        binding.emailInput.text.toString().trim(),
                        binding.passwordInput.text.toString().trim()
                    )
                )
                intentLogin.putExtras(bundle)

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