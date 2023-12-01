package com.trx.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.trx.R
import com.trx.models.Message

class ChatScreenAdapter(private val context: Context,private val messageList: MutableList<Message>)
    : Adapter<ViewHolder>() {

    private val itemSender = 1
    private val itemReceiver = 2

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        return if(FirebaseAuth.getInstance().currentUser!!.uid == currentMessage.sendersID)
            itemSender
        else
            itemReceiver
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if(viewType == itemSender){
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_sender_message,parent,false)
            SenderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_reciever_message,parent,false)
            RecieverViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentMessage = messageList[position]

        //Holder For Sender
        if(holder.javaClass == SenderViewHolder::class.java){

            val viewHolder = holder as SenderViewHolder
            holder.sentMessage.text = currentMessage.message
        //Holder For Receiver
        } else {

            val viewHolder = holder as RecieverViewHolder
            holder.receivedMessage.text = currentMessage.message

        }

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    class SenderViewHolder(itemView: View)
        : ViewHolder(itemView){
            val sentMessage : TextView = itemView.findViewById<TextView>(R.id.sent_message)
    }

    class RecieverViewHolder(itemView: View)
        : ViewHolder(itemView){
            val receivedMessage : TextView = itemView.findViewById<TextView>(R.id.received_message)
        }

}