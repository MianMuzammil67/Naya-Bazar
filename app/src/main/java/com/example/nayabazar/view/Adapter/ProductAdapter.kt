package com.example.nayabazar.view.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nayabazar.R
import com.example.nayabazar.databinding.ProductItemBinding
import com.example.nayabazar.model.Product

class ProductAdapter() :RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ProductItemBinding.
        inflate(LayoutInflater
            .from(parent.context)
            ,parent
            ,false))
    }
    override fun getItemCount(): Int {
       return differ.currentList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentList = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(currentList.productImage).
            placeholder(R.drawable.progress_animation).
            into(holder.binding.image)
            holder.binding.apply {
                label.text = currentList.productName
                price.text = currentList.productPrice
            }
            setOnClickListener {
                _itemClicked?.let { it(currentList) }
            }
        }
    }
    private var _itemClicked :((Product)-> Unit )? = null

    fun clickListener(listener : (Product) -> Unit){
        _itemClicked = listener
    }

    private val differCallback = object:DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem        }
    }
    var differ = AsyncListDiffer(this,differCallback)
    inner class ViewHolder(var binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}