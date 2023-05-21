package com.example.djgaming

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserRoom : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_room)
        var progressBar = findViewById<ProgressBar>(R.id.progressBar)


        progressBar.visibility = View.VISIBLE

        database = Firebase.database.reference

        var roomID = findViewById<TextView>(R.id.roomID)
        var roomPassword = findViewById<TextView>(R.id.roomPassword)

        database.child("admin").get().addOnSuccessListener {
            progressBar.visibility = View.GONE
if(it.exists()){
    val inputRoomId = it.child("roomId").value
    val inputroomPassword = it.child("roomPassword").value
    roomID.text = inputRoomId.toString()
    roomPassword.text = inputroomPassword.toString()
}

        }.addOnFailureListener{

        }

        val copyId = findViewById<ImageView>(R.id.copyId)
        val copyPassword = findViewById<ImageView>(R.id.copyPassword)

        val clipboard: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        copyId.setOnClickListener {

            val clip = ClipData.newPlainText("label", roomID.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(applicationContext, "Copied to ClipBoard", Toast.LENGTH_SHORT).show()
        }
        copyPassword.setOnClickListener {
            val clip = ClipData.newPlainText("label", roomPassword.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(applicationContext, "Copied to ClipBoard", Toast.LENGTH_SHORT).show()
        }



    }
}