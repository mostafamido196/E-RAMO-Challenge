package com.samy.rick.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samy.e_ramo.R
import com.samy.e_ramo.databinding.ItemCharacterBinding
import com.samy.rick.pojo.CharacterResult
import javax.inject.Inject



class CharacterAdapter @Inject constructor() :
    ListAdapter<CharacterResult, CharacterAdapter.ViewHolder>(DiffCallback()) {
    class DiffCallback : DiffUtil.ItemCallback<CharacterResult>() {
        override fun areItemsTheSame(
            oldItem: CharacterResult, newItem: CharacterResult,
        ): Boolean = newItem.id == oldItem.id

        override fun areContentsTheSame(
            oldItem: CharacterResult, newItem: CharacterResult,
        ): Boolean = newItem == oldItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, Type: Int): ViewHolder =
        ViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.bind(getItem(position))
        } catch (e: Exception) {
//            Log.d("hamoly", "onBindViewHolder e :$e")
//            Log.d("hamoly", "onBindViewHolder getItem(position) :${getItem(position)}")
//            Log.d("hamoly", "onBindViewHolder getItemViewType(position) :${getItemViewType(position)}")
        }
    }

    inner class ViewHolder(val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CharacterResult) {
            Glide.with(binding.imageView.context)
                .load(data.image)
                .placeholder(R.drawable.placeholder_image)  // Placeholder image
                .into(binding.imageView)

            binding.nameText.text = data.name
            binding.ageText.text = "Age: ${data.created}"
            binding.statusText.text = "Status: ${data.status}"

            binding.root.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(data)
                }
            }


        }

    }


    private var onItemClickListener: ((CharacterResult) -> Unit)? = null

    fun setOnItemClickListener(listener: (CharacterResult) -> Unit) {
        onItemClickListener = listener
    }

}
