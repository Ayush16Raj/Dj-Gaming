package com.example.djgaming

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth





class SignupActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize Firebase Auth
        auth = Firebase.auth
        val db = Firebase.firestore


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        var etFullName = findViewById<EditText>(R.id.etFullName)
        var etEmail = findViewById<EditText>(R.id.etEmail)
        var etPassword = findViewById<EditText>(R.id.etPassword)
        var btnSignup = findViewById<Button>(R.id.signupButton)
        var gotoLogin = findViewById<TextView>(R.id.gotoLogin)
        var progressBar = findViewById<ProgressBar>(R.id.progressBar)
        var confirmPassword = findViewById<EditText>(R.id.etConfirmPassword)

        gotoLogin.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnSignup.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            var email = etEmail.text.toString()
            var password = etPassword.text.toString()
            var fullName = etFullName.text.toString()
            var cnfPassword = confirmPassword.text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Enter Email", Toast.LENGTH_SHORT).show()

            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "Enter Password", Toast.LENGTH_SHORT).show()
            }
            if (TextUtils.isEmpty(fullName)) {
                Toast.makeText(applicationContext, "Enter FullName", Toast.LENGTH_SHORT).show()
            }
            if (TextUtils.isEmpty(cnfPassword)) {
                Toast.makeText(applicationContext, "Enter Confirm Password", Toast.LENGTH_SHORT).show()
            }
            if(!password.equals(cnfPassword)){
                Toast.makeText(applicationContext, "confirm password not matching", Toast.LENGTH_SHORT).show()
            }


            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val userid = user?.uid
                                db.collection("users").document(userid!!)
                                    .set(UserModel(email,fullName,userid, "player"))
                                    .addOnSuccessListener {

                                        Toast.makeText(applicationContext, "Successfully Registered",
                                            Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this@SignupActivity,LoginActivity::class.java)
                                        startActivity(intent)
                                        finish()

                            }.addOnFailureListener { e ->

                            }

                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }

        }
    }
}
