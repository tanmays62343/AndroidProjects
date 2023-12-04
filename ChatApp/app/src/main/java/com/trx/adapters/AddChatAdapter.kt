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

class AddChatAdapter(private val context: Context, private val usersList: List<User>) :
    Adapter<AddChatAdapter.AddChatHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddChatHolder {

        val binding = AddListRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return AddChatHolder(binding)
    }

    override fun onBindViewHolder(holder: AddChatHolder, position: Int) {
        val currentUser = usersList[position]
        holder.bind(currentUser)
        holder.itemView.setOnClickListener{
            Intent(context,ChatScreenActivity::class.java).also {
                it.putExtra("name",currentUser.name)
                it.putExtra("uid",currentUser.uniqueID)
                context.startActivity(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    class AddChatHolder(private val binding : AddListRecyclerItemBinding) : ViewHolder(binding.root){

        fun bind(user: User){
            binding.model = user
        }

    }

}