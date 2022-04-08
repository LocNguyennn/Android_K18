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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
            btnBack.setOnClickListener {
                val controller = findNavController()
                controller.navigateUp()
            }
            btnSignUp.setOnClickListener {
                val controller = findNavController()
                controller.navigate(R.id.action_signInFragment_to_signUpFragment)
            }
        }
    }

    private fun listenerSuccessEvent() {
        viewModel.isSuccessEvent.observe(viewLifecycleOwner) {
            if (it) {
                val intentLogin = Intent(context, Home::class.java)
                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
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
                val controller = findNavController()
                controller.navigate(R.id.action_signInFragment_to_homeFragment)
            }
        }
    }
//    fun <T> LiveData<T>.observeOnceAfterInit(owner: LifecycleOwner, observer: (T) -> Unit) {
//        var firstObservation = true
//
//        observe(owner, object: Observer<T>
//        {
//            override fun onChanged(value: T) {
//                if(firstObservation)
//                {
//                    firstObservation = false
//                }
//                else
//                {
//                    removeObserver(this)
//                    observer(value)
//                }
//            }
//        })
//    }
//    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
//        observe(lifecycleOwner, object : Observer<T> {
//            override fun onChanged(t: T?) {
//                observer.onChanged(t)
//                removeObserver(this)
//            }
//        })
//
//    }

    private fun listenerErrorEvent() {
        viewModel.isErrorEvent.observe(viewLifecycleOwner) { errMess ->
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setTitle("Error")
            dialog.setMessage(errMess)
            dialog.show()
        }
    }
}