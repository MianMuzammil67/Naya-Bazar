package com.example.nayabazar.view.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nayabazar.R
import com.example.nayabazar.databinding.CartItemBinding
import com.example.nayabazar.model.Product
import com.example.nayabazar.viewModel.CartViewModel

class CartAdapter(private val viewModel: CartViewModel) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CartItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        var quantity = currentItem.productQuantity.toInt()

        holder.itemView.apply {
            Glide.with(this)
                .load(currentItem.productImage)
                .placeholder(R.drawable.progress_animation)
                .centerCrop()
                .into(holder.binding.ivImage)
        }
        holder.binding.apply {
            tvTittle.text = currentItem.productName
            tvPrice.text = currentItem.productPrice
            tvCount.text = quantity.toString()

            ivUp.setOnClickListener {
                quantity += 1
                tvCount.text = quantity.toString()
                viewModel.updateCart(currentItem, quantity)
            }
            ivDown.setOnClickListener {
                if (quantity > 1) {
                    quantity -= 1
                    tvCount.text = quantity.toString()
                    viewModel.updateCart(currentItem, quantity)
                } else {
                    Toast.makeText(
                        holder.itemView.context,
                        "Quantity can't be less than 1",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            ivDelete.setOnClickListener {
                viewModel.deleteFromCart(currentItem)
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
    var differ = AsyncListDiffer(this, differCallback)

    class ViewHolder(var binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)

}