package com.example.djgaming

import android.annotation.SuppressLint
import android.content.Context
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
private lateinit var database: DatabaseReference
class EnrolledMatchAdapter(val matchList: ArrayList<MatchData>, val context1: Context): RecyclerView.Adapter<EnrolledMatchAdapter.UserMatchViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserMatchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.enrolled_match_list, parent, false)
        return UserMatchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return matchList.size
    }

    override fun onBindViewHolder(holder: UserMatchViewHolder, position: Int) {
        val currentItem = matchList[position]
        var  auth = Firebase.auth
      var  user = auth.currentUser
        database = Firebase.database.reference

        holder.txtReferenceId.setText("Reference Id: "+ currentItem.referenceId)
        holder.txtMatchDate.setText("Match Date: "+ currentItem.matchDate)
        holder.txtMatchTime.setText("Match Time: "+ currentItem.matchTime)
        holder.txtSlots.setText("Slots: "+ currentItem.slots)
        holder.txtEntryFee.setText("Entry Fee: "+ currentItem.entryFee)
        holder.txtPoolPrize.setText("Prize Pool: "+ currentItem.poolPrize)
        holder.txtMatchCategory.setText("Match Category: "+ currentItem.category)
        Picasso.get().load(currentItem.imgThumbnail).into(holder.userImgThumbnail2)

        holder.btnEditSqd.setOnClickListener {
            val intent = Intent(context1,EditSquad::class.java)
            intent.putExtra("Ref",currentItem.referenceId)
            context1.startActivity(intent)
        }
        holder.btnRemove.setOnClickListener {
            user?.let { it1 -> currentItem.referenceId?.let { it2 ->
                database.child("orders").child(it1.uid).child(
                    it2
                ).removeValue()
            } }
        }

        currentItem.referenceId?.let {
            database.child("matchData").child(it).addValueEventListener(object :
                ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                  var roomId = snapshot.child("tournamentRoomId").getValue(String::class.java)
                    var roomPass = snapshot.child("tournamentRoomPassword").getValue(String::class.java)
                    holder.txtRoomId.setText("Room Id: "+ roomId)
                    holder.txtRoomPass.setText("Room Password: "+ roomPass)

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context1,error.toString(), Toast.LENGTH_SHORT).show()

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
        val btnEditSqd: Button
        val btnRemove:Button


        init {
            // Define click listener for the ViewHolder's View
            userImgThumbnail2 = itemView.findViewById(R.id.imgThumbnail5)
            txtMatchDate = itemView.findViewById(R.id.txtMatchDate5)
            txtMatchTime = itemView.findViewById(R.id.txtMatchTime5)
            txtSlots = itemView.findViewById(R.id.txtslots5)
            txtEntryFee = itemView.findViewById(R.id.txtEntryFee5)
            txtPoolPrize = itemView.findViewById(R.id.txtPoolPrize5)
            txtMatchCategory = itemView.findViewById(R.id.txtMatchCategory5)
            txtReferenceId = itemView.findViewById(R.id.txtReferenceId5)
            txtRoomId = itemView.findViewById(R.id.txtRoomId5)
            txtRoomPass = itemView.findViewById(R.id.txtRoomPassword5)
            btnEditSqd = itemView.findViewById(R.id.btnEditSquad)
            btnRemove = itemView.findViewById(R.id.btnRemove)
        }

    }

}