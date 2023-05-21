package com.example.djgaming

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


private lateinit var auth: FirebaseAuth
val db = Firebase.firestore




class SplashScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed( {

            auth = Firebase.auth
            val user = auth.currentUser
            if(user != null){


                val userid = user.uid
                // get usertype
                val docRef = db.collection("users").document(userid)
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            val userType = document.data!!["userType"]
                            Log.d("show userType","$userType")

                            if(userType=="dj"){
                                val intent = Intent(this@SplashScreen,CustomRoom::class.java)
                                startActivity(intent)
                                finish()
                            }else{
                                val intent = Intent(this@SplashScreen,MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }


                        } else {

                        }
                    }.addOnFailureListener {
                        Toast.makeText(applicationContext,"failed",Toast.LENGTH_SHORT).show()
                    }
            }else{
                val intent = Intent(this@SplashScreen,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }


        }, 1500)
    }
}