package com.example.djgaming

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ReferenceAdapter(val referenceIdList: ArrayList<ReferenceData>,val context2: Context): RecyclerView.Adapter<ReferenceAdapter.ReferenceViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReferenceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reference_id_list, parent, false)
        return ReferenceViewHolder(view)


    }

    override fun getItemCount(): Int {
        return referenceIdList.size
    }

    override fun onBindViewHolder(holder: ReferenceViewHolder, position: Int) {
        val currentItem = referenceIdList[position]

        holder.textViewReference.text = currentItem.referenceId
        holder.cardRefId.setOnClickListener {
            var intent = Intent(context2,JoinedSquad::class.java)
            intent.putExtra("Ref",currentItem.referenceId)
            context2.startActivity(intent)
        }
    }

    class ReferenceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val textViewReference: TextView
        val cardRefId: CardView

        init {
            // Define click listener for the ViewHolder's View

            textViewReference = itemView.findViewById(R.id.textViewReference)
            cardRefId = itemView.findViewById(R.id.cardRefId)

        }

    }
}