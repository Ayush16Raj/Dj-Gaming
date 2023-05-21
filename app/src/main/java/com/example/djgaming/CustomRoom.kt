package com.example.djgaming

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CustomRoom : AppCompatActivity() {



    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_room)

        database = Firebase.database.reference

        var logout = findViewById<Button>(R.id.btndjLogout)

        logout.setOnClickListener {

            var builder = AlertDialog.Builder(this)

            with(builder)
            {
                setTitle("Dj Gaming")
                setMessage("Are you sure you want to Logout")
                setCancelable(true)
                setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                    Firebase.auth.signOut()

                    var intent = Intent(this@CustomRoom, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                })

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
        var etroomID = findViewById<EditText>(R.id.etroomID)
        var etroomPassword = findViewById<EditText>(R.id.etroomPassword)

        val btnCreate = findViewById<Button>(R.id.btnCreate)

        btnCreate.setOnClickListener {

            var roomId = etroomID.text.toString()
            var roomPassword = etroomPassword.text.toString()


            if(TextUtils.isEmpty(roomId)){
                Toast.makeText(applicationContext,"Enter roomId", Toast.LENGTH_SHORT).show()

            }
            if(TextUtils.isEmpty(roomPassword)){
                Toast.makeText(applicationContext,"Enter roomPassword", Toast.LENGTH_SHORT).show()
            }


                val user = User(roomId, roomPassword)

                database.child("admin").setValue(user).addOnSuccessListener {
                    Toast.makeText(applicationContext,"Room ID , password updated", Toast.LENGTH_LONG).show()
                    etroomID.text.clear()
                    etroomPassword.text.clear()
                }





        }

        val createMatchCardView = findViewById<CardView>(R.id.createMatch)

        val updateMatchCardView = findViewById<CardView>(R.id.updateIdPass)

        val availableMatchCardView = findViewById<CardView>(R.id.availableMatch)

        val enrolledTeamsCardView = findViewById<CardView>(R.id.enrolledTeams)

        createMatchCardView.setOnClickListener {
            val intent = Intent(this@CustomRoom,CreateMatch::class.java)
            startActivity(intent)


        }
        updateMatchCardView.setOnClickListener {
            val intent = Intent(this@CustomRoom,UpdateIdPassword::class.java)
            startActivity(intent)

        }

        availableMatchCardView.setOnClickListener {
            val intent = Intent(this@CustomRoom,DeleteMatch::class.java)
            startActivity(intent)

        }

        enrolledTeamsCardView.setOnClickListener {
            val intent = Intent(this@CustomRoom,EnrolledTeams::class.java)
            startActivity(intent)
        }



    }
}