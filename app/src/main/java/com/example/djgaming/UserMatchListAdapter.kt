package com.example.djgaming

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

private lateinit var database: DatabaseReference

class UserMatchListAdapter(val matchList: ArrayList<MatchData>, val context1: Context): RecyclerView.Adapter<UserMatchListAdapter.UserMatchViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserMatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_match_list, parent, false)
        return UserMatchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return matchList.size
    }

    override fun onBindViewHolder(holder: UserMatchViewHolder, position: Int) {
        val currentItem = matchList[position]


        holder.txtReferenceId.setText("Reference Id: "+ currentItem.referenceId)
        holder.txtMatchDate.setText("Match Date: "+ currentItem.matchDate)
        holder.txtMatchTime.setText("Match Time: "+ currentItem.matchTime)
        holder.txtSlots.setText("Slots: "+ currentItem.slots)
        holder.txtEntryFee.setText("Entry Fee: "+ currentItem.entryFee)
        holder.txtPoolPrize.setText("Prize Pool: "+ currentItem.poolPrize)
        holder.txtMatchCategory.setText("Match Category: "+ currentItem.category)
        holder.txtRoomId.setText("Room Id: "+ currentItem.tournamentRoomId)
        holder.txtRoomPass.setText("Room Password: "+ currentItem.tournamentRoomPassword)
        Picasso.get().load(currentItem.imgThumbnail).into(holder.userImgThumbnail2)

var value = Integer.parseInt(currentItem.slots)

        var auth: FirebaseAuth = Firebase.auth
        var user: FirebaseUser = auth.currentUser!!
        database = FirebaseDatabase.getInstance().getReference("orders").child(user.uid)



        holder.cardViewBuy.setOnClickListener {

          database.addValueEventListener(object :
                ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(!snapshot.hasChild(currentItem.referenceId!!) && snapshot.childrenCount<value) {

                        var intent = Intent(context1,BuyTicket::class.java)
                        intent.putExtra("Room Id", currentItem.tournamentRoomId)
                        intent.putExtra("Room Password", currentItem.tournamentRoomPassword)
                        intent.putExtra("Reference Id", currentItem.referenceId)
                        intent.putExtra("Match Date", currentItem.matchDate)
                        intent.putExtra("Match Time", currentItem.matchTime)
                        intent.putExtra("Slots", currentItem.slots)
                        intent.putExtra("Entry Fee", currentItem.entryFee)
                        intent.putExtra("Prize Pool", currentItem.poolPrize)
                        intent.putExtra("Match Category", currentItem.category)
                        intent.putExtra("imgThumbnail", currentItem.imgThumbnail)
                        context1.startActivity(intent)
                    }else if(snapshot.hasChild(currentItem.referenceId!!) && snapshot.childrenCount>=value){
Toast.makeText(context1,"You have already purchased this ticket",Toast.LENGTH_SHORT).show()
                    }else if(!snapshot.hasChild(currentItem.referenceId!!) && snapshot.childrenCount>=value){
                        Toast.makeText(context1,"Sorry Tournament is full now",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context1,"You have already purchased this ticket",Toast.LENGTH_SHORT).show()

                    }


                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context1,"Something went wrong",Toast.LENGTH_SHORT).show()
                }
            })


        }


    }

    class UserMatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val userImgThumbnail2: ImageView
        val txtMatchDate: TextView
        val txtMatchTime: TextView
        val txtSlots: TextView
        val txtEntryFee: TextView
        val txtPoolPrize: TextView
        val txtMatchCategory: TextView
        val txtReferenceId : TextView
        val txtRoomId: TextView
        val txtRoomPass: TextView
        val cardViewBuy: CardView

        init {
            // Define click listener for the ViewHolder's View
            userImgThumbnail2 = itemView.findViewById(R.id.userImgThumbnail2)
            txtMatchDate = itemView.findViewById(R.id.userTxtMatchDate)
            txtMatchTime = itemView.findViewById(R.id.userTxtMatchTime)
            txtSlots = itemView.findViewById(R.id.userTxtslots)
            txtEntryFee = itemView.findViewById(R.id.userTxtEntryFee)
            txtPoolPrize = itemView.findViewById(R.id.userTxtPoolPrize)
            txtMatchCategory = itemView.findViewById(R.id.userTxtMatchCategory)
            cardViewBuy = itemView.findViewById(R.id.cardViewBuy)
            txtReferenceId = itemView.findViewById(R.id.userTxtReferenceId)
            txtRoomId = itemView.findViewById(R.id.userTxtRoomId)
            txtRoomPass = itemView.findViewById(R.id.userTxtRoomPassword)
        }

    }

}
