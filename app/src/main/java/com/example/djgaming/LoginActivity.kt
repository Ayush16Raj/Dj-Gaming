package com.example.djgaming

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


private lateinit var auth: FirebaseAuth


class LoginActivity : AppCompatActivity() {
    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        var etEmail = findViewById<EditText>(R.id.etEmail)
        var etPassword = findViewById<EditText>(R.id.etPassword )
        var btnLogin = findViewById<Button>(R.id.loginButton)
        var gotoSignup = findViewById<TextView>(R.id.gotoSignup)
        var resetPassword = findViewById<TextView>(R.id.resetPassword)
        var progressBar = findViewById<ProgressBar>(R.id.progressBar)

        gotoSignup.setOnClickListener {
            val intent = Intent(this@LoginActivity,SignupActivity::class.java)
            startActivity(intent)
            finish()
        }


        btnLogin.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            var email = etEmail.text.toString()
            var password = etPassword.text.toString()


            if(TextUtils.isEmpty(email)){
                Toast.makeText(applicationContext,"Enter Email", Toast.LENGTH_SHORT).show()

            }
            if(TextUtils.isEmpty(password)){
                Toast.makeText(applicationContext,"Enter Password", Toast.LENGTH_SHORT).show()
            }


            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {

                        val user = auth.currentUser
                        val userid = user?.uid
                        // get usertype
                        val docRef = db.collection("users").document(userid!!)
                        docRef.get()
                            .addOnSuccessListener { document ->
                                if (document != null) {
                                    val userType = document.data!!["userType"]

                                    Toast.makeText(
                                        this, "Successfully Logined",
                                        Toast.LENGTH_SHORT
                                    ).show()


                                    if(userType=="dj"){
                                        val intent = Intent(this@LoginActivity,CustomRoom::class.java)
                                        startActivity(intent)
                                        finish()
                                    }else{
                                        val intent = Intent(this@LoginActivity,MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }

                                } else {

                                }
                            }
                            .addOnFailureListener { exception ->
                            }



                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(this, "Wrong Crential",
                            Toast.LENGTH_SHORT).show()

                    }
                }
        }

        resetPassword.setOnClickListener {
            var getEmail = etEmail.text.toString()

            if(TextUtils.isEmpty(getEmail)){
                Toast.makeText(applicationContext,"First enter your email", Toast.LENGTH_SHORT).show()

            }
            FirebaseAuth.getInstance().sendPasswordResetEmail(getEmail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext,"Email Sent", Toast.LENGTH_SHORT).show()
                    }
                }
        }



    }
}