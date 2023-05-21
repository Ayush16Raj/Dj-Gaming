package com.example.djgaming

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class EnrolledMatches : AppCompatActivity() {


    private lateinit var database: DatabaseReference
    private lateinit var matchArrayList:ArrayList<MatchData>
    private lateinit var matchAdapter:EnrolledMatchAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar4: ProgressBar
    private lateinit var auth: FirebaseAuth
    lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enrolled_matches)

        auth = Firebase.auth
        user = auth.currentUser!!

        progressBar4 = findViewById<ProgressBar>(R.id.progressBar8)
        progressBar4.visibility = View.VISIBLE
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView4)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        matchArrayList = arrayListOf<MatchData>()
        database = FirebaseDatabase.getInstance().getReference("orders").child(user.uid)
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


                    matchAdapter = EnrolledMatchAdapter(matchArrayList,this@EnrolledMatches)
                    recyclerView.adapter = matchAdapter
                    matchAdapter.notifyDataSetChanged()


                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@EnrolledMatches,error.toString(), Toast.LENGTH_SHORT).show()

            }
        })
        progressBar4.visibility = View.GONE
    }

}