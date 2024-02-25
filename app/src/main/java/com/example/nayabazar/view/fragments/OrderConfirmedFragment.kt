package com.example.nayabazar.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.nayabazar.R
import com.example.nayabazar.databinding.FragmentOrderConfirmedBinding

class OrderConfirmedFragment : Fragment(R.layout.fragment_order_confirmed) {

    private lateinit var binding: FragmentOrderConfirmedBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrderConfirmedBinding.bind(view)

        binding.btnshopping.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_order_confirmed_to_homeFragment)
        }


    }
}