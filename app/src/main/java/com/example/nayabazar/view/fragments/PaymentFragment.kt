package com.example.nayabazar.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.nayabazar.R
import com.example.nayabazar.databinding.FragmentPaymentBinding
import com.example.nayabazar.model.Payment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PaymentFragment : Fragment(R.layout.fragment_payment) {
    private lateinit var binding: FragmentPaymentBinding
    @Inject lateinit var payment: Payment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentBinding.bind(view)
        binding.apply {
            bthNext.setOnClickListener{
                val cardHolderNameInput = etCardHolderName.editText?.text.toString().trim()
                val cardNumberInput = etCardNo.editText?.text.toString().trim()
                val expiryDateInput = etValidDate.editText?.text.toString().trim()
                val cvvInput = etCvv.editText?.text.toString().trim()

//                etValidDate.editText?.inputType = InputType.TYPE_CLASS_NUMBER

                payment.apply {
                    cashOnDelivery = false
                    cardHolderName = cardHolderNameInput
                    cardNumber = cardNumberInput
                    expiryDate = expiryDateInput
                    cardVerificationValue = cvvInput
                }
                findNavController().navigateUp()
            }
        }


    }


}
