package com.example.nayabazar.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.nayabazar.R
import com.example.nayabazar.databinding.FragmentAdrressBinding
import com.example.nayabazar.model.Address
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AdrressFragment : Fragment(R.layout.fragment_adrress) {
    private lateinit var binding: FragmentAdrressBinding
    @Inject
    lateinit var address: Address
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdrressBinding.bind(view)
        binding.apply {
            btnConformAddress.setOnClickListener {
                val fullNameInput = etNamee.text.toString().trim()
                val phoneNumberInput = etPhonee.text.toString().trim()
                val addressTittleInput = etAdressTittle.text.toString().trim()
                val cityInput = etCity.text.toString().trim()
                val streetAddressInput = etAdresss.text.toString().trim()

                address.apply {
                    phoneNumber = phoneNumberInput
                    addressTittle = addressTittleInput
                    name = fullNameInput
                    city = cityInput
                    address = streetAddressInput
                }
                findNavController().navigateUp()

            }
        }

    }


}