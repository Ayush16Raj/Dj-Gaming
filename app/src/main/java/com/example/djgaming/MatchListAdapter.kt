package com.example.djgaming

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

private lateinit var database: DatabaseReference

class MatchListAdapter(val matchList: ArrayList<MatchData>,val context1:Context): RecyclerView.Adapter<MatchListAdapter.MatchViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.match_list, parent, false)
        return MatchViewHolder(view)
    }

    override fun getItemCount(): Int {
       return matchList.size
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val currentItem = matchList[position]

        holder.txtRoomId.setText("Room Id: "+ currentItem.tournamentRoomId)
        holder.txtRoomPassword.setText("Room Password: "+ currentItem.tournamentRoomPassword)
        holder.txtReferenceId.setText("Reference Id: "+ currentItem.referenceId)
        holder.txtMatchDate.setText("Match Date: "+ currentItem.matchDate)
        holder.txtMatchTime.setText("Match Time: "+ currentItem.matchTime)
        holder.txtSlots.setText("Slots: "+ currentItem.slots)
        holder.txtEntryFee.setText("Entry Fee: "+ currentItem.entryFee)
        holder.txtPoolPrize.setText("Prize Pool: "+ currentItem.poolPrize)
        holder.txtMatchCategory.setText("Match Category: "+ currentItem.category)
        Picasso.get().load(currentItem.imgThumbnail).into(holder.imgThumbnail2)

        holder.btnDeleteTournament.setOnClickListener {
            var builder = AlertDialog.Builder(context1)





            with(builder)
            {
                setTitle("Dj Gaming")
                setMessage("Are you sure you want to delete this tournament")
                setCancelable(true)
                setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                    database = Firebase.database.reference
                    currentItem.referenceId?.let { it1 -> database.child("matchData").child(it1).removeValue() }
                        ?.addOnSuccessListener {
                            Toast.makeText(context,"Tournament Deleted",Toast.LENGTH_SHORT).show()
                        }?.addOnFailureListener{
                            Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()            }
                    currentItem.referenceId?.let { it1 -> database.child("All orders").child(it1).removeValue() }


                })

               notifyItemRemoved(position)
                setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
                })
                var dialog: AlertDialog? = null
                dialog = builder.create()
                if (dialog!=null){
                    dialog.show()
                }


            }

        }


    }

    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgThumbnail2: ImageView
        val txtRoomId: TextView
        val txtRoomPassword: TextView
        val txtMatchDate: TextView
        val txtMatchTime: TextView
        val txtSlots: TextView
        val txtEntryFee: TextView
        val txtPoolPrize: TextView
        val txtMatchCategory: TextView
        val txtReferenceId :TextView
        val btnDeleteTournament: Button

        init {
            // Define click listener for the ViewHolder's View
            imgThumbnail2 = itemView.findViewById(R.id.imgThumbnail2)
            txtRoomId = itemView.findViewById(R.id.txtRoomId)
            txtRoomPassword = itemView.findViewById(R.id.txtRoomPassword)
            txtMatchDate = itemView.findViewById(R.id.txtMatchDate)
            txtMatchTime = itemView.findViewById(R.id.txtMatchTime)
            txtSlots = itemView.findViewById(R.id.txtslots)
            txtEntryFee = itemView.findViewById(R.id.txtEntryFee)
            txtPoolPrize = itemView.findViewById(R.id.txtPoolPrize)
            txtMatchCategory = itemView.findViewById(R.id.txtMatchCategory)
            btnDeleteTournament = itemView.findViewById(R.id.btnDeleteTournament)
            txtReferenceId = itemView.findViewById(R.id.txtReferenceId)
        }

    }

}
