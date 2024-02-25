package com.example.nayabazar.view.Adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nayabazar.R
import com.example.nayabazar.databinding.CateItemBinding
import com.example.nayabazar.model.Category

class categoryAdapter : RecyclerView.Adapter<categoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CateItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currList  = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this)
                .load(currList.icon)
                .placeholder(R.drawable.progress_animation)
                .centerCrop()
                .into(holder.binding.catImage)

            holder.binding.catName.text=currList.name
            holder.binding.catImage.background = ColorDrawable(Color.parseColor(currList.colour))
            holder.itemView.setOnClickListener{
                itemClicked?.let { it(currList) }
            }
        }
    }
    private var itemClicked : ((Category) -> Unit) ? = null
    fun clickListener (listener : (Category)-> Unit){
         itemClicked = listener
    }
    private val differCallback = object : DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
    var differ = AsyncListDiffer(this,differCallback)

    class ViewHolder (var binding: CateItemBinding):RecyclerView.ViewHolder(binding.root)

    }