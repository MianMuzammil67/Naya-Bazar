package com.example.nayabazar.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.nayabazar.R
import com.example.nayabazar.databinding.FragmentSignUpBinding
import com.example.nayabazar.utils.Constants
import com.example.nayabazar.utils.Resource
import com.example.nayabazar.viewModel.AuthViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private lateinit var binding: FragmentSignUpBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)

        val viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

//        val currentUser = viewModel.currentUser
//        if (currentUser != null) {
//            findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
//        }

        binding.btnSignUp.setOnClickListener {

            val userName = binding.textInputLayout.editText?.text.toString()
            val eMail = binding.textInputLayout2.editText?.text.toString()
            val password = binding.textInputLayout3.editText?.text.toString()

            viewModel.signUp(userName, eMail, password)

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.signUpFlow.collect {
                        flowResponce(it)
                    }
                }
            }
        }
        binding.tvSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    private fun flowResponce(res: Resource<FirebaseUser>?) {
        when (res) {
            is Resource.Success -> {
                Constants.hideProgressBar(binding.progressBar)
                Toast.makeText(context, "account Created Successfully", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
            }

            is Resource.Loading -> {
                Constants.showProgressBar(binding.progressBar)
            }

            is Resource.Error -> {
                Constants.hideProgressBar(binding.progressBar)
                Toast.makeText(context, res.message, Toast.LENGTH_LONG).show()
                Log.d("signupFragment", res.message.toString())
            }

            else -> {
                Toast.makeText(context, "something went wrong", Toast.LENGTH_LONG).show()

            }
        }
    }
}