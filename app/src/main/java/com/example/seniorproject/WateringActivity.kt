package com.example.seniorproject


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.animation.AnimationUtils

class WateringActivity : AppCompatActivity() {

    lateinit var scaleUp: Animation;
    lateinit var scaleDown: Animation;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watering)
        scaleUp = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.scale_up)
        scaleDown = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.scale_down)
        var btnPress = findViewById<ImageView>(R.id.btnPress);

//        btnPress.setOnTouchListener(object :View.OnTouchListener{
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                if(event!!.action==MotionEvent.ACTION_DOWN){
//                    btnPress.startAnimation(scaleUp)
//                }
//                else if(event!!.action==MotionEvent.ACTION_UP){
//                    btnPress.startAnimation(scaleDown)
//                }
//            return v?.onTouchEvent(event) ?: true
//            }
//        })

        btnPress.setOnClickListener {
            println("Test Watering !!");
            btnPress.startAnimation(scaleDown)
        }

        //go back to dashboard
        var btnBackToDashboard = findViewById<View>(R.id.backWatering)
        btnBackToDashboard.setOnClickListener {
            btnBackToDashboard.startAnimation(scaleDown)
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
    }
}