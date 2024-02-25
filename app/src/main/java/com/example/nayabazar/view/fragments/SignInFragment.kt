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
import com.example.nayabazar.databinding.FragmentSignInBinding
import com.example.nayabazar.utils.Constants
import com.example.nayabazar.utils.Resource
import com.example.nayabazar.viewModel.AuthViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private lateinit var binding : FragmentSignInBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)
        val viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.btnSignIn.setOnClickListener {
            val email = binding.textInputLayout2.editText?.text.toString().trim()
            val password = binding.textInputLayout3.editText?.text.toString().trim()

            viewModel.sighIn(email,password)
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.signInFlow.collect {
                        flowResponce(it)
                    }
                }
            }
        }
        binding.tvForgotPass.setOnClickListener {
            // TODO
            Toast.makeText(context, "yet to Implement", Toast.LENGTH_SHORT).show()
//            findNavController().navigate()
        }
    }

    private fun flowResponce(res: Resource<FirebaseUser>?) {
        when (res) {
            is Resource.Success -> {
                Constants.hideProgressBar(binding.progressBar)
                Toast.makeText(context, "SignIn Successfully", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
            }
            is Resource.Loading -> {
                Constants.showProgressBar(binding.progressBar)
            }
            is Resource.Error -> {
                Constants.hideProgressBar(binding.progressBar)
                Toast.makeText(context, res.message, Toast.LENGTH_LONG).show()
                Log.d("signInFragment", res.message.toString())
            }
            else -> {
                Toast.makeText(context, "something went wrong", Toast.LENGTH_LONG).show()
            }
        }
    }

}