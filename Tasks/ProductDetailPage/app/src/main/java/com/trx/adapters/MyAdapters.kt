package com.trx.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.trx.R
import com.trx.activities.GunDetails
import com.trx.models.GunsModel
import kotlin.coroutines.coroutineContext

class MyAdapters (private val context: Context, private val gunsList : ArrayList<GunsModel>) : Adapter<MyAdapters.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_item, parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val gun = gunsList[position]
        holder.gunImage.setImageResource(gun.gunImage)
        holder.gunName.text = gun.gunName
        holder.scope.text = gun.scope
        holder.magzine.text = gun.magCapacity.toString()

        holder.btnBuy.setOnClickListener {
            context.startActivity(Intent(context,GunDetails::class.java))
        }

       /* if (position % 2 == 0) {
            holder.mainItem.setBackgroundColor(
                Color.parseColor("#EBEBEB")
            )
        } else {
            holder.mainItem.setBackgroundColor(
                Color.parseColor("#FFFFFF")
            )
        }*/

    }

    override fun getItemCount(): Int {
        return gunsList.size
    }

    class MyViewHolder (itemView: View) : ViewHolder(itemView){
        var mainItem = itemView.findViewById<View>(R.id.subMainItem)
        var gunImage = itemView.findViewById<ImageView>(R.id.gunImage)
        var gunName = itemView.findViewById<TextView>(R.id.gunName)
        var scope = itemView.findViewById<TextView>(R.id.scopeType)
        var magzine = itemView.findViewById<TextView>(R.id.MagsCapacity)
        var btnBuy = itemView.findViewById<Button>(R.id.btnBuy)
    }
}