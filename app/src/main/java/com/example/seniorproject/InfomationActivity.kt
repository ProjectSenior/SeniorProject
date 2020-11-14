package com.example.seniorproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class InfomationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infomation)

        //go back to dashboard
        var btnBackToDashboard = findViewById<View>(R.id.backInfomation)
        btnBackToDashboard.setOnClickListener {
            val intent = Intent(this,DashboardActivity :: class.java)
            startActivity(intent)
        }
    }
}