package com.example.djgaming

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class JoinedSquadAdapter(val chooseSquadList: ArrayList<ChooseSquadData>, val context2: Context): RecyclerView.Adapter<JoinedSquadAdapter.ReferenceViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReferenceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.joined_squad_list, parent, false)
        return ReferenceViewHolder(view)


    }

    override fun getItemCount(): Int {
        return chooseSquadList.size
    }

    override fun onBindViewHolder(holder: ReferenceViewHolder, position: Int) {
        val currentItem = chooseSquadList[position]

        holder.p1N.setText(currentItem.p1N)
        holder.p2N.setText(currentItem.p2N)
        holder.p3N.setText(currentItem.p3N)
        holder.p4N.setText(currentItem.p4N)
        holder.p1Id.setText(currentItem.p1Id)
        holder.p2Id.setText(currentItem.p2Id)
        holder.p3Id.setText(currentItem.p3Id)
        holder.p4Id.setText(currentItem.p4Id)
    }

    class ReferenceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


        var p1N = itemView.findViewById<TextView>(R.id.p1N2)
        var p2N = itemView.findViewById<TextView>(R.id.p2N2)
        var p3N = itemView.findViewById<TextView>(R.id.p3N2)
        var p4N = itemView.findViewById<TextView>(R.id.p4N2)
        var p1Id = itemView.findViewById<TextView>(R.id.p1Id2)
        var p2Id = itemView.findViewById<TextView>(R.id.p2Id2)
        var p3Id = itemView.findViewById<TextView>(R.id.p3Id2)
        var p4Id = itemView.findViewById<TextView>(R.id.p4Id2)


    }
}