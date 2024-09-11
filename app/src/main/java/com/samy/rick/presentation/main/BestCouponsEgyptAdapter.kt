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
import com.samy.rick.pojo.model.DataModel
import javax.inject.Inject


class BestCouponsEgyptAdapter @Inject constructor() :
    ListAdapter<DataModel.DataX, BestCouponsEgyptAdapter.ViewHolder>(DiffCallback()) {
    private val list =
        mutableListOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)

    class DiffCallback : DiffUtil.ItemCallback<DataModel.DataX>() {
        override fun areItemsTheSame(
            oldItem: DataModel.DataX, newItem: DataModel.DataX,
        ): Boolean = newItem.id == oldItem.id

        override fun areContentsTheSame(
            oldItem: DataModel.DataX, newItem: DataModel.DataX,
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

        fun bind(data: DataModel.DataX) {

            binding.upToUoOFF.text = "UP TO ${data.discount_range} OFF"
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


            Glide.with(binding.root.context)
                .load(data.brand_logo)
                .error(R.drawable.baseline_image_24)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.iv)

            binding.root.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(data)
                }
            }


        }

    }


    private var onItemClickListener: ((DataModel.DataX) -> Unit)? = null

    fun setOnItemClickListener(listener: (DataModel.DataX) -> Unit) {
        onItemClickListener = listener
    }

}
