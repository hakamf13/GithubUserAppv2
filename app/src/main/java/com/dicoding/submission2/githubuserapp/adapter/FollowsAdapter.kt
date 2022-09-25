package com.dicoding.submission2.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission2.githubuserapp.databinding.ItemRowUserBinding
import com.dicoding.submission2.githubuserapp.datasource.ItemsItem

class FollowsAdapter(private val listUser: List<ItemsItem>) : RecyclerView.Adapter<FollowsAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val userDetail = listUser[position]
        Glide.with(holder.itemView.context)
            .load(userDetail.avatarUrl)
            .circleCrop()
            .into(holder.binding.imgItemAvatar)
        holder.binding.tvItemUsername.text = userDetail.login
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.absoluteAdapterPosition])
        }
        if (position == listUser.lastIndex){
            val param = holder.itemView.layoutParams as RecyclerView.LayoutParams
            param.bottomMargin = 80
            holder.itemView.layoutParams = param
        } else if (position == 0) {
            val param = holder.itemView.layoutParams as RecyclerView.LayoutParams
            param.topMargin = 160
            holder.itemView.layoutParams = param
        }
    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }
}