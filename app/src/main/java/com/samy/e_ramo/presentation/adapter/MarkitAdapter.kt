package com.samy.e_ramo.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samy.e_ramo.R
import com.samy.e_ramo.databinding.ItemImgBinding
import com.samy.e_ramo.pojo.model.StringDemo
import javax.inject.Inject


class MarkitAdapter @Inject constructor() :
    ListAdapter<StringDemo, MarkitAdapter.ViewHolder>(DiffCallback()) {
    class DiffCallback : DiffUtil.ItemCallback<StringDemo>() {
        override fun areItemsTheSame(
            oldItem: StringDemo, newItem: StringDemo,
        ): Boolean = newItem.id == oldItem.id

        override fun areContentsTheSame(
            oldItem: StringDemo, newItem: StringDemo,
        ): Boolean = newItem == oldItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, Type: Int): ViewHolder =
        ViewHolder(
            ItemImgBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ViewHolder(val binding: ItemImgBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: StringDemo, position: Int) {

            binding.tv.text = data.name
            if (position == 0) {
                binding.iv0.setImageResource(R.drawable.yello_circal)
                binding.iv1.setImageResource(R.drawable.gray_circal)
                binding.iv2.setImageResource(R.drawable.gray_circal)
                binding.iv3.setImageResource(R.drawable.gray_circal)
            } else if (position == 1) {
                binding.iv0.setImageResource(R.drawable.gray_circal)
                binding.iv1.setImageResource(R.drawable.yello_circal)
                binding.iv2.setImageResource(R.drawable.gray_circal)
                binding.iv3.setImageResource(R.drawable.gray_circal)
            } else if (position == 2) {
                binding.iv0.setImageResource(R.drawable.gray_circal)
                binding.iv1.setImageResource(R.drawable.gray_circal)
                binding.iv2.setImageResource(R.drawable.yello_circal)
                binding.iv3.setImageResource(R.drawable.gray_circal)
            } else if (position == 3) {
                binding.iv0.setImageResource(R.drawable.gray_circal)
                binding.iv1.setImageResource(R.drawable.gray_circal)
                binding.iv2.setImageResource(R.drawable.gray_circal)
                binding.iv3.setImageResource(R.drawable.yello_circal)
            }
            binding.root.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(data)
                }
            }


        }

    }


    private var onItemClickListener: ((StringDemo) -> Unit)? = null

    fun setOnItemClickListener(listener: (StringDemo) -> Unit) {
        onItemClickListener = listener
    }

}
