package com.example.djgaming

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import com.razorpay.PaymentResultListener
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.razorpay.Checkout
import com.squareup.picasso.Picasso
import org.json.JSONObject


private lateinit var auth: FirebaseAuth
private lateinit var database1: DatabaseReference
private lateinit var database2: DatabaseReference


class BuyTicket : AppCompatActivity(),PaymentResultListener {
    lateinit var MD: String
    lateinit var MT: String
    lateinit var RI :String
    lateinit var RP: String
    lateinit var Ref: String
    lateinit var slots: String
    lateinit var EF: String
    lateinit var PP: String
    lateinit var MC: String
    lateinit var status: String
    lateinit var URI: String
    lateinit var user:FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_ticket)


        auth = Firebase.auth
        Checkout.preload(applicationContext)

         user = auth.currentUser!!

database1 = Firebase.database.reference
        database2 = Firebase.database.reference



        var userImgThumbnail3 = findViewById<ImageView>(R.id.userImgThumbnail3)
        var userTxtMatchDate2 = findViewById<TextView>(R.id.userTxtMatchDate2)
        var userTxtMatchTime2 = findViewById<TextView>(R.id.userTxtMatchTime2)
        var userTxtReferenceId2 = findViewById<TextView>(R.id.userTxtReferenceId2)
        var userTxtslots2 = findViewById<TextView>(R.id.userTxtslots2)
        var userTxtEntryFee2 = findViewById<TextView>(R.id.userTxtEntryFee2)
        var userTxtPoolPrize2 = findViewById<TextView>(R.id.userTxtPoolPrize2)
        var userTxtMatchCategory2 = findViewById<TextView>(R.id.userTxtMatchCategory2)
        var userRoomId2 = findViewById<TextView>(R.id.userTxtRoomId2)
        var userRoomPass2 = findViewById<TextView>(R.id.userTxtRoomPassword2)
        var btnBookNow = findViewById<Button>(R.id.btnBookNow)

         MD = intent.getStringExtra("Match Date").toString()
         MT = intent.getStringExtra("Match Time").toString()
         RI = intent.getStringExtra("Room Id").toString()
         RP = intent.getStringExtra("Room Password").toString()
         Ref = intent.getStringExtra("Reference Id").toString()
         slots = intent.getStringExtra("Slots").toString()
         EF = intent.getStringExtra("Entry Fee").toString()
         PP = intent.getStringExtra("Prize Pool").toString()
         MC = intent.getStringExtra("Match Category").toString()


        userTxtMatchDate2.text = "Match Date: " + MD
        userTxtMatchTime2.text = "Match Time: " + MT
        userRoomId2.text = "Room Id: " + RI
        userRoomPass2.text = "Room Password: " + RP
        (userTxtReferenceId2).text = "Reference Id: " + Ref
        userTxtslots2.text = "Slots: " + slots
        userTxtEntryFee2.text = "Entry Fee: " + EF
        (userTxtPoolPrize2).text = "Prize Poole: " + PP
        userTxtMatchCategory2.text = "Match Category: " + MC
         URI = intent.getStringExtra("imgThumbnail").toString()

        Picasso.get().load(URI).into(userImgThumbnail3)


        btnBookNow.setOnClickListener {

            val activity: Activity = this
            val co = Checkout()
            co.setKeyID("rzp_test_VF0bYbEleM8ZuG")

            try {
                val options = JSONObject()
                options.put("name","DJ Gaming")
                options.put("description",Ref)
                //You can omit the image option to fetch the image from the dashboard
                options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
                options.put("theme.color", "#3399cc");
                options.put("currency","INR");
             //   options.put("order_id", "order_DBJOWzybf0sJbb");
                options.put("amount",Integer.parseInt(EF)*100)//pass amount in currency subunits

                val retryObj =  JSONObject();
                retryObj.put("enabled", true);
                retryObj.put("max_count", 4);
                options.put("retry", retryObj);

                val prefill = JSONObject()
                prefill.put("email",user.email)
              //  prefill.put("contact","9876543210")

                options.put("prefill",prefill)
                co.open(activity,options)
            }catch (e: Exception){
                Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }


        }
    }




    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(applicationContext, "Transaction Successful", Toast.LENGTH_SHORT).show()
        saveData()
    }
    override fun onPaymentError(p0: Int, p1: String?) {
Toast.makeText(applicationContext,"Transaction Failed",Toast.LENGTH_SHORT).show()    }

    private fun saveData() {
        val match_data = MatchData(MD,MT,Ref,EF,PP,"",URI,MC,RI,RP)
        database1.child("orders").child(user.uid).child(Ref).setValue(match_data)

        val chooseSquadData = ChooseSquadData(user.uid,user.displayName,"","","","","","","","")
        database2.child("All orders").child(Ref).child(user.uid).setValue(chooseSquadData)
    }

}
