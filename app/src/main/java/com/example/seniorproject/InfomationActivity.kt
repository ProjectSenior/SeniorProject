package com.example.seniorproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import com.github.mikephil.charting.charts.LineChart

class InfomationActivity : AppCompatActivity() {

    //for create animation
    lateinit var scaleUp: Animation;
    lateinit var scaleDown: Animation;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infomation)

        var volumeReportChart = findViewById<LineChart>(R.id.reportingChart)
        volumeReportChart.setTouchEnabled(true);
        volumeReportChart.setPinchZoom(true);

        scaleUp = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.scale_up)
        scaleDown = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.scale_down)

        //go back to dashboard
        var btnBackToDashboard = findViewById<View>(R.id.backInfomation)
        btnBackToDashboard.setOnClickListener {
            btnBackToDashboard.startAnimation(scaleDown)
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
    }
}