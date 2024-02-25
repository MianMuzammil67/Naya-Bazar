package com.example.nayabazar.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nayabazar.R
import com.example.nayabazar.databinding.FragmentCheckOutBinding
import com.example.nayabazar.model.Address
import com.example.nayabazar.model.Order
import com.example.nayabazar.model.Payment
import com.example.nayabazar.viewModel.CartViewModel
import com.example.nayabazar.viewModel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CheckOutFragment : Fragment(R.layout.fragment_check_out) {
    private lateinit var binding: FragmentCheckOutBinding
    private val shippingCost = 20
    private lateinit var cartViewModel : CartViewModel
    private lateinit var orderViewModel : OrderViewModel
    private val args: CheckOutFragmentArgs by navArgs()
    @Inject
    lateinit var address: Address
    @Inject
    lateinit var payment: Payment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCheckOutBinding.bind(view)
        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]
        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]
         val cartProduct = args.cartProducts

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cartViewModel.productPrice.collectLatest { priceSum ->
                    priceSum.let {
                        val price = String.format("%.2f", it)
                        binding.tvSubPrice.text = "$ $price"
                        val totalPrice = (it?.plus(shippingCost))
                        binding.tvPrice.text = "$ $totalPrice"
                        binding.tvShipPrice.text = "$ $shippingCost"
                    }
                }
            }
        }
        binding.apply {
            btnPlaceOrder.setOnClickListener {
                showDialog()


//                val order = Order(cartProduct.toList(), address, payment)
//                orderViewModel.placeOrder(order)
//                cartViewModel.emptyCart()
//                findNavController().navigate(R.id.action_checkOutFragment_to_fragment_order_confirmed)
            }
            val adress = address.address
            if (adress.isNotEmpty() || adress != "") {
                tvAddress.text = adress
            }
            imEdit.setOnClickListener {
                findNavController().navigate(R.id.action_checkOutFragment_to_adrressFragment)
            }
            rdCOD.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    tvCardNo.visibility = View.GONE
                    rdCard.isChecked = false
                    rdCOD.isChecked = true
                }
            }
            val cardNo = payment.cardNumber
            if (cardNo.isNotEmpty() || cardNo != "") {
                tvCardNo.text = buildString {
                    val cardNo = payment.cardNumber.takeLast(4)
                    repeat(payment.cardNumber.length - 4) { append("*") }
                    append(cardNo)
                }
            }
            rdCard.setOnCheckedChangeListener { _, isChecked ->
                rdCOD.isChecked = false
                if (isChecked) {
                   tvCardNo.visibility = View.VISIBLE
                }
            }
           tvCardNo.setOnClickListener {
                findNavController().navigate(R.id.action_checkOutFragment_to_paymentFragment)
            }
        }
    }
    private fun showDialog() {
        val cartProduct = args.cartProducts
        val dialog = context?.let { Dialog(it) }
        dialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.conform_dialogue)
            findViewById<Button>(R.id.btnConfirm).setOnClickListener {
                val order = Order(cartProduct.toList(), address, payment)
                orderViewModel.placeOrder(order)
                cartViewModel.emptyCart()
                findNavController().navigate(R.id.action_checkOutFragment_to_fragment_order_confirmed)
                dialog.dismiss()
            }
            findViewById<Button>(R.id.btnCancel).setOnClickListener {
                this.dismiss()
            }
            this.show()
        }
    }
    override fun onResume() {
        super.onResume()
        if (binding.rdCard.isChecked) {
            binding.tvCardNo.visibility = View.VISIBLE
//            val cardNo = payment.cardNumber
//            if (cardNo.isNotEmpty() || cardNo != "") {
//                binding.tvCardNo.text = cardNo
//            }
        }
    }
}