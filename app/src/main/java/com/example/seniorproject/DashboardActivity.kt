package com.example.seniorproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        auth = FirebaseAuth.getInstance()

        //go to Watering page
        var btnWatering = findViewById<View>(R.id.btnWatering)
        btnWatering.setOnClickListener {

            val intent = Intent(this,WateringActivity :: class.java)
            startActivity(intent)
        }

        //go to Infomation page
        var btnInfo = findViewById<View>(R.id.btnInfo)
        btnInfo.setOnClickListener {
            val intent = Intent (this,InfomationActivity :: class.java)
            startActivity(intent)
        }

//        //go to Profile page
        var btnProfile = findViewById<View>(R.id.btnProfile)
        btnProfile.setOnClickListener {
            val intent = Intent(this,PlantActivity :: class.java)
            startActivity(intent)
        }

//        //go to EditProfile page
        var btnResume = findViewById<View>(R.id.btnResume)
        btnResume.setOnClickListener {
            val intent = Intent(this,EditPlantActivity :: class.java)
            startActivity(intent)
        }

        //Logout
        var btnExit = findViewById<View>(R.id.btnExit)
        btnExit.setOnClickListener {
            auth.signOut()
            val intent = Intent(this,MainActivity :: class.java)
            startActivity(intent)
        }


    }
}