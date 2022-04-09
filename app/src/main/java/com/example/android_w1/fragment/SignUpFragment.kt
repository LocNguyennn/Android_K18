package com.example.android_w1.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android_w1.DataStore
import com.example.android_w1.R
import com.example.android_w1.UserViewModel
import com.example.android_w1.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private lateinit var binding : FragmentSignUpBinding
    private lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Log.e("SignUpFragment","onViewCreated")
        binding.apply {
            btnLogin.setOnClickListener {
                val controller = findNavController()
                controller.navigate(R.id.action_signUpFragment_to_signInFragment)
            }
            btnSignUp.setOnClickListener {
                // code nhận thông tin đăng kí ở đây
                viewModel.checkValidEmailAndPassword(
                    edtEmail.text.toString().trim(),
                    edtPassword.text.toString().trim()
                )
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // lắng nghe sự kiện đki thành công
        listenerSuccessEvent()
        // lắng nghe sự kiện báo lỗi
        listenerErrorEvent()
    }
    private fun listenerSuccessEvent() {
        viewModel.isSuccessEvent.observe(viewLifecycleOwner) {
            if (it) {
                DataStore(binding.edtFullName.text.toString().trim(),
                    binding.edtEmail.text.toString().trim(),
                    binding.edtPassword.text.toString().trim())
                Log.e("SignUpFragment:", " mk = ${binding.edtPassword.text.toString().trim()}");
                findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
//                findNavController().popBackStack()
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