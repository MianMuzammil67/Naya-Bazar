package com.example.nayabazar.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.nayabazar.R
import com.example.nayabazar.databinding.FragmentProfileBinding
import com.example.nayabazar.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        val viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                binding.tvUserName.text = viewModel.currentUser?.displayName
            }
        }
        binding.btnLogOut.setOnClickListener {
            viewModel.signOut()
            Toast.makeText(context, "Log out successfully", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack(R.id.profileFragment, false)
            findNavController().navigate(R.id.action_profileFragment_to_signUpFragment)
        }
    }

}