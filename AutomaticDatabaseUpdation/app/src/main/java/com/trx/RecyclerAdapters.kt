package com.trx

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class RecyclerAdapters(val stams : List<StampsEntity>) : Adapter<RecyclerAdapters.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val infalter : LayoutInflater = LayoutInflater.from(parent.context)
        val view = infalter.inflate(R.layout.recyclerview_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.serial.text = stams[position].id.toString()
        holder.timeStamp.text = stams[position].tStamp
        var color = "#CCCCCC"
        if(position % 2 == 0){
            color = "#EEEEEE"
        }
        holder.container.setBackgroundColor(Color.parseColor(color))
    }

    override fun getItemCount(): Int {
        return stams.size
    }

    class MyViewHolder(itemView: View) : ViewHolder(itemView){
        var serial = itemView.findViewById<TextView>(R.id.serial)
        var timeStamp = itemView.findViewById<TextView>(R.id.timestamp)
        var container = itemView.findViewById<LinearLayout>(R.id.container)
    }

}