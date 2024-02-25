package com.example.nayabazar.view.fragments
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.nayabazar.R
import com.example.nayabazar.databinding.FragmentDetailBinding
import com.example.nayabazar.viewModel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

        val viewModel = ViewModelProvider(this)[CartViewModel::class.java]

//        binding.someLabel.apply {
//            text= "someDynamicString"
//           paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//
//        }



        args.product.let {
            context?.let { it1 ->
                Glide.with(it1).load(it.productImage)
                    .placeholder(R.drawable.loading)
                    .into(binding.productImage)
            }
            binding.productTittle.text = it.productName
            binding.productPrice.text = it.productPrice
            binding.productDescription.text = it.productDes
            binding.ratingBar.rating = it.productRating.toFloat()
        }
        binding.addToCartBtn.setOnClickListener {
            viewModel.addToCart(args.product)
            Toast.makeText(context, "Product Added to cart", Toast.LENGTH_SHORT).show()
            binding.addToCartBtn.isEnabled = false
        }

    }
}