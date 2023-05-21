package com.example.djgaming

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        val db = Firebase.firestore
        val user = auth.currentUser




        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_main)
        if (user != null) {


            var logout = findViewById<Button>(R.id.btnLogout)
            var showName = findViewById<TextView>(R.id.showName)
            var customRoom = findViewById<CardView>(R.id.customRoom)
            var gotoTournament = findViewById<CardView>(R.id.gotoTournament)

            val userid = user.uid


            val docRef = db.collection("users").document(userid)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val inputName = document.data!!["fullName"].toString()
                        showName.text = inputName

                    } else {

                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(applicationContext,"Something went wrong",Toast.LENGTH_SHORT).show()
                }

            logout.setOnClickListener {
                var builder = AlertDialog.Builder(this)

                with(builder)
                {
                    setTitle("Dj Gaming")
                    setMessage("Are you sure you want to Logout")
                    setCancelable(true)
                    setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                        Firebase.auth.signOut()

                        var intent = Intent(this@MainActivity, LoginActivity::class.java)
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


            customRoom.setOnClickListener {
                var intent = Intent(this@MainActivity,UserRoom::class.java)
                startActivity(intent)
            }
            var ytOpen = findViewById<CardView>(R.id.ytLogo)

            ytOpen.setOnClickListener {

                val intent =  Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/@djgaming1930"))
                startActivity(intent)
            }
            var sponserOpen =  findViewById<CardView>(R.id.sponserLogo)
            sponserOpen.setOnClickListener{
                val intent =  Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/@djgaming1930"))
                startActivity(intent)
            }
            gotoTournament.setOnClickListener {
                var intent = Intent(this@MainActivity,UserTournament::class.java)
                startActivity(intent)
            }


        }
    }
}