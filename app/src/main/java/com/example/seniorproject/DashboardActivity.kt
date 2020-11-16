package com.example.seniorproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlin.random.Random

class DashboardActivity : AppCompatActivity() {
    private var userId: String? = null
    private lateinit var auth: FirebaseAuth

    //for create animation
    lateinit var scaleUp: Animation;
    lateinit var scaleDown: Animation;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        scaleUp = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.scale_up)
        scaleDown = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.scale_down)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser?.uid

        var database = FirebaseDatabase.getInstance().reference.child("Plant").child(user.toString())

        //go to Watering page
        var btnWatering = findViewById<View>(R.id.btnWatering)
        btnWatering.setOnClickListener {
            btnWatering.startAnimation(scaleDown)
            val intent = Intent(this, WateringActivity::class.java)
            startActivity(intent)
        }

        //go to Infomation page
        var btnInfo = findViewById<View>(R.id.btnInfo)
        btnInfo.setOnClickListener {
            btnInfo.startAnimation(scaleDown)
            val intent = Intent(this, InfomationActivity::class.java)
            startActivity(intent)
        }

//        //go to Profile page
        var btnProfile = findViewById<View>(R.id.btnProfile)
        btnProfile.setOnClickListener {
            btnProfile.startAnimation(scaleDown)
            val intent = Intent(this, PlantActivity::class.java)
            startActivity(intent)
        }

//        //go to EditProfile page
        var btnResume = findViewById<View>(R.id.btnResume)
        btnResume.setOnClickListener {
            btnResume.startAnimation(scaleDown)
            val intent = Intent(this, EditPlantActivity::class.java)
            startActivity(intent)
        }

        //go to Scoreboard
        var btnScore = findViewById<View>(R.id.btnScore)
        btnScore.setOnClickListener {
            btnScore.startAnimation(scaleDown)
            val intent = Intent(this, ScoreboardActivity::class.java)
            startActivity(intent)
        }
        //Logout
        var btnExit = findViewById<View>(R.id.btnExit)
        btnExit.setOnClickListener {
            btnExit.startAnimation(scaleDown)
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //getImage
        var imageShow = findViewById<ImageView>(R.id.getImageView)
        var getImage = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var getdata = snapshot.child("imageUrl").value
                Picasso.get().load(getdata.toString().toUri()).into(imageShow)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        database.addValueEventListener(getImage)

    }
}