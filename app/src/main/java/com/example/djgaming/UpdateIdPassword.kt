package com.example.djgaming

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private lateinit var database: DatabaseReference

class UpdateIdPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_id_password)

        database = Firebase.database.reference

        val progressBar3 = findViewById<ProgressBar>(R.id.progressBar3)
        var etupdateReferenceId = findViewById<EditText>(R.id.etupdateReferenceId)
        var etupdateRoomId = findViewById<EditText>(R.id.etupdateRoomId)
        var etupdateRoomPassword = findViewById<EditText>(R.id.etupdateRoomPassword)
        var btnUpdate = findViewById<Button>(R.id.btnUpdateRoom)

        btnUpdate.setOnClickListener {

            progressBar3.visibility = View.VISIBLE

            var updateReferenceId = etupdateReferenceId.text.toString()
            var updateRoomId = etupdateRoomId.text.toString()
            var updateRoomPassword = etupdateRoomPassword.text.toString()

            if (TextUtils.isEmpty(updateReferenceId)) {
                etupdateReferenceId.setError("Enter Reference Id")
                etupdateReferenceId.requestFocus()
            } else if (TextUtils.isEmpty(updateRoomId)) {
                etupdateRoomId.setError("Enter Room Id")
                etupdateRoomId.requestFocus()
            } else if (TextUtils.isEmpty(updateRoomPassword)) {
                etupdateRoomPassword.setError("Enter Room Password")
                etupdateRoomPassword.requestFocus()

            } else {
                database.child("matchData").child(updateReferenceId).get().addOnSuccessListener {

                    if (it.exists()) {


                            val update = hashMapOf<String, Any>(
                                "tournamentRoomId" to updateRoomId,
                                "tournamentRoomPassword" to updateRoomPassword
                            )

                            database.child("matchData").child(updateReferenceId)
                                .updateChildren(update)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        applicationContext,
                                        "Id,Password Updated",
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                    etupdateRoomId.text.clear()
                                    etupdateRoomPassword.text.clear()
                                    etupdateReferenceId.text.clear()


                                }.addOnFailureListener {
                                    Toast.makeText(
                                        applicationContext,
                                        "Something went wrong",
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                }

                    }else {
                        Toast.makeText(
                            applicationContext,
                            "Wrong Reference Id",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }

                }
            }
            progressBar3.visibility = View.GONE

        }
    }
}