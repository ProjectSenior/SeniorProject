package com.example.seniorproject


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.random.Random

class WateringActivity : AppCompatActivity() {

    lateinit var scaleUp: Animation
    lateinit var scaleDown: Animation
    private lateinit var auth: FirebaseAuth
    private var userId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watering)
        scaleUp = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.scale_up)
        scaleDown = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.scale_down)
        var btnPress = findViewById<ImageView>(R.id.btnPress)
        auth = FirebaseAuth.getInstance() // user
        val currentUser = FirebaseAuth.getInstance().currentUser //tamnameJa
        userId = currentUser?.uid
        var database = FirebaseDatabase.getInstance().reference.child("User").child(userId.toString()).child("score")
        var randomScoreText = Random.nextInt((11-1))
        database.setValue(randomScoreText)
        var humidity = findViewById<TextView>(R.id.moistureInput)
        var temp = findViewById<TextView>(R.id.tempInput)
        var moisture = findViewById<TextView>(R.id.soilmoistureInput)
        var databaseInfo = FirebaseDatabase.getInstance().reference.child("Plant").child(userId.toString())
        var  getData = object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var humidityText = snapshot.child("humidity").value
                var tempText = snapshot.child("temp").value
                var moistureText =snapshot.child("moisture").value
                humidity.text = humidityText.toString()
                temp.text= tempText.toString()
                moisture.text=moistureText.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        databaseInfo.addValueEventListener(getData)
        //press watering and alert
        btnPress.setOnClickListener {
            println("Test Watering !!")
            btnPress.startAnimation(scaleDown)
            randomScore()
            val mAlertDialog = AlertDialog.Builder(this@WateringActivity)
            mAlertDialog.setTitle("คะแนน")
            mAlertDialog.setMessage("คุณได้รับคะแนน " + randomScoreText + " คะแนน")
            mAlertDialog.setIcon(R.mipmap.ic_leaf)
            mAlertDialog.show()

        }

        //go back to dashboard
        var btnBackToDashboard = findViewById<View>(R.id.backWatering)
        btnBackToDashboard.setOnClickListener {
            btnBackToDashboard.startAnimation(scaleDown)
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
    }


    fun randomScore(){

        println("ran: " + "${Random.nextInt((11-1))}")
    }
}