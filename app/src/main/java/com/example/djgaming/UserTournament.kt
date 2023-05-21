package com.example.djgaming

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class UserTournament : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var matchArrayList:ArrayList<MatchData>
    private lateinit var matchAdapter:UserMatchListAdapter
   private lateinit var recyclerView: RecyclerView
   private lateinit var progressBar7: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_tournament)

        var enrolledMatches = findViewById<CardView>(R.id.enrolledMatches)
        enrolledMatches.setOnClickListener {
            var intent = Intent(this@UserTournament,EnrolledMatches::class.java)
            startActivity(intent)
        }

        progressBar7 = findViewById(R.id.progressBar7)
        progressBar7.visibility = View.VISIBLE
        recyclerView = findViewById(R.id.recyclerView3)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        matchArrayList = arrayListOf<MatchData>()
        database = FirebaseDatabase.getInstance().getReference("matchData")
        fetchData()
    }

    private fun fetchData() {
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for (snapshot in snapshot.children) {
                        val matchData = snapshot.getValue(MatchData::class.java)
                        matchArrayList.add(matchData!!)
                    }


                    matchAdapter = UserMatchListAdapter(matchArrayList,this@UserTournament)
                    recyclerView.adapter = matchAdapter
                    matchAdapter.notifyDataSetChanged()


                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@UserTournament,error.toString(), Toast.LENGTH_SHORT).show()

            }
        })
        progressBar7.visibility = View.GONE
    }

}