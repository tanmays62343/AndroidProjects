package com.trx.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.trx.activities.ChatScreenActivity
import com.trx.databinding.AddListRecyclerItemBinding
import com.trx.models.User

class MainAdapter(private val context : Context, private val usersList : MutableList<User>)
    : Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = AddListRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false
        )
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val user  = usersList[position]
        holder.bindView(user)
        holder.itemView.setOnClickListener {
            Intent(context,ChatScreenActivity::class.java).also {
                context.startActivity(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    class MainViewHolder(private val binding: AddListRecyclerItemBinding)
        : ViewHolder(binding.root){

        fun bindView(user : User){
            binding.model = user
        }

    }

}