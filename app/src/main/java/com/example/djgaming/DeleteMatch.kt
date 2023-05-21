package com.example.djgaming

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class DeleteMatch : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var matchArrayList:ArrayList<MatchData>
    private lateinit var matchAdapter:MatchListAdapter
    private lateinit var recyclerView:RecyclerView
    private lateinit var progressBar4:ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_match)
         progressBar4 = findViewById<ProgressBar>(R.id.progressBar4)
        progressBar4.visibility = View.VISIBLE
         recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        matchArrayList = arrayListOf<MatchData>()
        database = FirebaseDatabase.getInstance().getReference("matchData")
        fetchData()


    }

    private fun fetchData() {
        database.addValueEventListener(object :ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for (snapshot in snapshot.children) {
                        val matchData = snapshot.getValue(MatchData::class.java)
                        matchArrayList.add(matchData!!)
                    }


                    matchAdapter = MatchListAdapter(matchArrayList,this@DeleteMatch)
                    recyclerView.adapter = matchAdapter
                    matchAdapter.notifyDataSetChanged()


                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DeleteMatch,error.toString(),Toast.LENGTH_SHORT).show()

            }
        })
        progressBar4.visibility = View.GONE
    }

}