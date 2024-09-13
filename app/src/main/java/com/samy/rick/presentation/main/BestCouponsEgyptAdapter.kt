package com.samy.rick.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.samy.e_ramo.R
import com.samy.e_ramo.databinding.ItemBestCouponBinding
import javax.inject.Inject


class BestCouponsEgyptAdapter @Inject constructor() :
    ListAdapter<String, BestCouponsEgyptAdapter.ViewHolder>(DiffCallback()) {
    private val list =
        mutableListOf(
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false
        )

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String, newItem: String,
        ): Boolean = newItem == oldItem

        override fun areContentsTheSame(
            oldItem: String, newItem: String,
        ): Boolean = newItem == oldItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, Type: Int): ViewHolder =
        ViewHolder(
            ItemBestCouponBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: ItemBestCouponBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: String) {

            binding.upToUoOFF.text = "UP TO OFF"
            binding.heart.setOnClickListener {
                if (list[position] != true) {
                    list[position] = true
                    binding.heart.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.root.context,
                            R.drawable.heart
                        )
                    )
                } else {
                    list[position] = false
                    binding.heart.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.root.context,
                            R.drawable.love
                        )
                    )
                }
            }



            binding.root.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(data)
                }
            }


        }

    }


    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

}
