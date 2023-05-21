package com.example.djgaming


import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


class CreateMatch : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    lateinit var category: String
    lateinit var sImage: Uri
    var storageRef = Firebase.storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_match)



        database = Firebase.database.reference
        storageRef = FirebaseStorage.getInstance()


        val items = arrayOf("Select Map", "Erangel", "Livik", "Miramar", "TDM")
        val arrayadp = ArrayAdapter(
            this,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            items
        )


        val progressBar2 = findViewById<ProgressBar>(R.id.progressBar2)
        val etMatchDate = findViewById<EditText>(R.id.etMatchDate)
        val etMatchTime = findViewById<EditText>(R.id.etMatchTime)
        val etReferenceId = findViewById<EditText>(R.id.etReferenceId)
        val etEntryFee = findViewById<EditText>(R.id.etEntryFee)
        val etPoolPrize = findViewById<EditText>(R.id.etPoolPrize)
        val etSlots = findViewById<EditText>(R.id.etSlots)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val imgThumbnail = findViewById<ImageView>(R.id.imgThumbnail)
        val btnUploadThumbnail = findViewById<Button>(R.id.btnUploadThumbnail)
        val btnCreateTournament = findViewById<Button>(R.id.btncreateTournament)

        spinner.adapter = arrayadp

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                category = spinner.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }


        }
        val selectImageIntent = registerForActivityResult(ActivityResultContracts.GetContent())
        { uri ->
            imgThumbnail.setImageURI(uri)
            if (uri != null) {
                sImage = uri
            }

        }

        btnUploadThumbnail.setOnClickListener {
            selectImageIntent.launch("image/*")
        }







        fun uploadData(imgUri: Uri) {


            var matchDate = etMatchDate.text.toString()
            var matchTime = etMatchTime.text.toString()
            var referenceId = etReferenceId.text.toString()
            var entryFee = etEntryFee.text.toString()
            var poolPrize = etPoolPrize.text.toString()
            var slots = etSlots.text.toString()
            var tournamentRoomId = "not available"
            var tournamentRoomPassword = "not available"




            if (TextUtils.isEmpty(matchDate)) {
                etMatchDate.setError("Enter Match Date")
                etMatchDate.requestFocus()
            } else if (TextUtils.isEmpty(matchTime)) {
                etMatchTime.setError("Enter Match Time")
                etMatchTime.requestFocus()
            } else if (TextUtils.isEmpty(referenceId)) {
                etReferenceId.setError("Enter Reference Id")
                etReferenceId.requestFocus()

            } else if (TextUtils.isEmpty(entryFee)) {
                etEntryFee.setError("Enter Entry Fee")
                etEntryFee.requestFocus()
            } else if (TextUtils.isEmpty(poolPrize)) {
                etPoolPrize.setError("Enter Prize Pool")
                etPoolPrize.requestFocus()
            } else if (TextUtils.isEmpty(slots)) {
                etSlots.setError("Enter Slots")
                etSlots.requestFocus()
            } else if (imgThumbnail.drawable == null) {
                Toast.makeText(applicationContext, "No image Selected", Toast.LENGTH_LONG)
                    .show()
            } else {
                database.child("matchData").child(referenceId).get().addOnSuccessListener {

                    if (it.exists()) {
                        var builder = AlertDialog.Builder(this)

                        with(builder)
                        {
                            setTitle("Dj Gaming")
                            setMessage("Ye Reference Id pehle se hai dj bhai")
                            setCancelable(true)

                            setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->
                                dialogInterface.cancel()
                            })
                            var dialog: AlertDialog? = null
                            dialog = builder.create()
                            if (dialog!=null){
                                dialog.show()
                            }


                        }
                    } else {
                        val matchData = MatchData(
                            matchDate,
                            matchTime,
                            referenceId,
                            entryFee,
                            poolPrize,
                            slots,
                            imgUri.toString(),
                            category,
                            tournamentRoomId,
                            tournamentRoomPassword
                        )

                        database.child("matchData").child(referenceId).setValue(matchData)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    applicationContext,
                                    "Tournament Created",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                                etMatchDate.text.clear()
                                etMatchTime.text.clear()
                                etEntryFee.text.clear()
                                etSlots.text.clear()
                                etPoolPrize.text.clear()
                                etReferenceId.text.clear()
                                imgThumbnail.setImageResource(android.R.color.transparent)


                            }.addOnFailureListener {
                                Toast.makeText(
                                    applicationContext,
                                    "Fail to create",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                    }
                }
            }
        }
        btnCreateTournament.setOnClickListener {
            progressBar2.visibility = View.VISIBLE

            storageRef.getReference("images").child(System.currentTimeMillis().toString())
                .putFile(sImage)
                .addOnSuccessListener {task ->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener {
                      uploadData(it)
                      progressBar2.visibility = View.GONE
                  }.addOnFailureListener {
                       Toast.makeText(
                          applicationContext,
                          "Fail to download image url",
                          Toast.LENGTH_LONG
                      ).show()
                      progressBar2.visibility = View.GONE
                  }
                    }.addOnFailureListener {
                    Toast.makeText(
                        applicationContext,
                        "Fail to create tournament",
                        Toast.LENGTH_LONG
                    ).show()
                    progressBar2.visibility = View.GONE
                }


        }


    }
}