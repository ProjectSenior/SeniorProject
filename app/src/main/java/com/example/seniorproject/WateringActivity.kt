package com.example.seniorproject


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView

class WateringActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watering)

        var btnPress = findViewById<ImageView>(R.id.btnPress);
        btnPress.setOnClickListener {
            println("Test Watering !!");
        }

        //go back to dashboard
        var btnBackToDashboard = findViewById<View>(R.id.backWatering)
        btnBackToDashboard.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
    }
}