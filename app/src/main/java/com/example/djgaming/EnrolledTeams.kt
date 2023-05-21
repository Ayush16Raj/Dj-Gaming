package com.example.djgaming

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

private lateinit var database: DatabaseReference
private lateinit var referenceIdList:ArrayList<ReferenceData>
private lateinit var referenceAdapter: ReferenceAdapter
lateinit var recyclerView: RecyclerView
lateinit var progressBar5: ProgressBar
lateinit var gridLayoutManager: GridLayoutManager

class EnrolledTeams : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enrolled_teams)

        progressBar5 = findViewById<ProgressBar>(R.id.progressBar5)
        progressBar5.visibility = View.VISIBLE
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView2)
        gridLayoutManager = GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.setHasFixedSize(true)

        referenceIdList = arrayListOf<ReferenceData>()
        database = FirebaseDatabase.getInstance().getReference("matchData")
        fetchData()


    }
    private fun fetchData() {
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for (snapshot in snapshot.children) {
                        val referenceData = snapshot.getValue(ReferenceData::class.java)
                        referenceIdList.add(referenceData!!)
                    }


                    referenceAdapter = ReferenceAdapter(referenceIdList,this@EnrolledTeams)
                    recyclerView.adapter = referenceAdapter
                    referenceAdapter.notifyDataSetChanged()


                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@EnrolledTeams,error.toString(), Toast.LENGTH_SHORT).show()

            }
        })
        progressBar5.visibility = View.GONE
    }
}