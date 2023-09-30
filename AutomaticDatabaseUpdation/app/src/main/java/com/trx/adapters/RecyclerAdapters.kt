package com.trx.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.trx.R
import com.trx.database.StampsEntity

class RecyclerAdapters(private val stamps : List<StampsEntity>) : Adapter<RecyclerAdapters.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recyclerview_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.serial.text = stamps[position].id.toString()
        holder.timeStamp.text = stamps[position].tStamp
        var color = "#CCCCCC"
        if(position % 2 == 0){
            color = "#EEEEEE"
        }
        holder.container.setBackgroundColor(Color.parseColor(color))
    }

    override fun getItemCount(): Int {
        return stamps.size
    }

    class MyViewHolder(itemView: View) : ViewHolder(itemView){
        var serial: TextView = itemView.findViewById<TextView>(R.id.serial)
        var timeStamp: TextView = itemView.findViewById<TextView>(R.id.timestamp)
        var container: LinearLayout = itemView.findViewById<LinearLayout>(R.id.container)
    }

}