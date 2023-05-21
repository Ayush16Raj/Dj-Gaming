package com.example.djgaming

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EditSquad : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    lateinit var user: FirebaseUser



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_squad)

        database = Firebase.database.reference
        auth = Firebase.auth
        user = auth.currentUser!!
       var  referenceId = intent.getStringExtra("Ref")

        var p1N = findViewById<EditText>(R.id.p1N)
        var p2N = findViewById<EditText>(R.id.p2N)
        var p3N = findViewById<EditText>(R.id.p3N)
        var p4N = findViewById<EditText>(R.id.p4N)
        var p1Id = findViewById<EditText>(R.id.p1Id)
        var p2Id = findViewById<EditText>(R.id.p2Id)
        var p3Id = findViewById<EditText>(R.id.p3Id)
        var p4Id = findViewById<EditText>(R.id.p4Id)
        val btnJoin = findViewById<Button>(R.id.btnJoin)




        btnJoin.setOnClickListener {


            val update = hashMapOf<String, Any>(
                "p1N" to p1N.text.toString(),
                "p2N" to p2N.text.toString(),
                "p3N" to p3N.text.toString(),
                "p4N" to p4N.text.toString(),
                "p1Id" to p1Id.text.toString(),
                "p2Id" to p2Id.text.toString(),
                "p3Id" to p3Id.text.toString(),
                "p4Id" to p4Id.text.toString()
            )
            database.child("All orders").child(referenceId.toString()).child(user.uid).updateChildren(update)
                .addOnSuccessListener {
                    Toast.makeText(
                        applicationContext,
                        "Joined Tournament Successfully",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    p1N.text.clear()
                    p1Id.text.clear()
                    p2N.text.clear()
                    p2Id.text.clear()
                    p3N.text.clear()
                    p3Id.text.clear()
                    p4N.text.clear()
                    p4Id.text.clear()
                }.addOnFailureListener {
                    Toast.makeText(
                        applicationContext,
                        "Something went wrong",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }}
        }

    }

