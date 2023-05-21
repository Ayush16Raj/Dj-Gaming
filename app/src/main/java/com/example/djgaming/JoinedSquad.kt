package com.example.djgaming

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class JoinedSquad : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var squadList:ArrayList<ChooseSquadData>
    private lateinit var  JoinedSquadAdapter:JoinedSquadAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar4: ProgressBar
    lateinit var Ref: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joined_squad)

        progressBar4 = findViewById<ProgressBar>(R.id.progressBar9)
        progressBar4.visibility = View.VISIBLE
        Ref = intent.getStringExtra("Ref").toString()
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView9)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        squadList = arrayListOf<ChooseSquadData>()
        database = FirebaseDatabase.getInstance().getReference("All orders").child(Ref)
        fetchData()
    }

    private fun fetchData() {
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for (snapshot in snapshot.children) {
                        val matchData = snapshot.getValue(ChooseSquadData::class.java)
                        squadList.add(matchData!!)
                    }


                    JoinedSquadAdapter = JoinedSquadAdapter(squadList,this@JoinedSquad)
                    recyclerView.adapter = JoinedSquadAdapter
                    JoinedSquadAdapter.notifyDataSetChanged()


                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@JoinedSquad,error.toString(), Toast.LENGTH_SHORT).show()

            }
        })
        progressBar4.visibility = View.GONE
    }
}