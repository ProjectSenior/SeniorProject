package com.example.seniorproject


import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class WateringActivity : AppCompatActivity() {

    lateinit var scaleUp: Animation
    lateinit var scaleDown: Animation
    private lateinit var auth: FirebaseAuth
    private var userId: String? = null
    var statusCheck:Boolean=true; //for detect alertDialog
    var addScore:Int=0;
    var getScoreRandom:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watering)

        scaleUp = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.scale_up)
        scaleDown = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.scale_down)


        auth = FirebaseAuth.getInstance() // user
        val currentUser = FirebaseAuth.getInstance().currentUser //tamnameJa
        userId = currentUser?.uid


        var btnPress = findViewById<ImageView>(R.id.btnPress)
        var database = FirebaseDatabase.getInstance().reference.child("User").child(userId.toString()).child("score")
        var humidity = findViewById<TextView>(R.id.moistureInput)
        var temp = findViewById<TextView>(R.id.tempInput)
        var moisture = findViewById<TextView>(R.id.soilmoistureInput)
        var databaseInfo = FirebaseDatabase.getInstance().reference.child("Plant").child(userId.toString())
        var getData = object :ValueEventListener{
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
            var dateLimit = object : ValueEventListener{

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDataChange(snapshot: DataSnapshot) {
                    var randomScoreText = Random.nextInt((11-1))
                    var dateCheck = snapshot.child("currentDateLimit").value
                    var moistureCheck = snapshot.child("moisture").value

                    //get current date
                    val current = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val formatted = current.format(formatter)
                    println(statusCheck)
                    if(moistureCheck.hashCode() > 40 && dateCheck.toString()!=formatted){
                        val mAlertDialog = AlertDialog.Builder(this@WateringActivity)
                        mAlertDialog.setTitle("คะแนน")
                        getScoreRandom=randomScoreText
                        mAlertDialog.setMessage("คุณได้รับคะแนน " + getScoreRandom + " คะแนน")
                        mAlertDialog.setIcon(R.mipmap.ic_leaf)
                        mAlertDialog.show()
                        println("moisture more than 40")
                        getRandomData()
                        statusCheck=false;
                        databaseInfo.child("currentDateLimit").setValue(formatted)
                    }
                    else if(dateCheck.toString()==formatted || statusCheck){
                        val mAlertDialog = AlertDialog.Builder(this@WateringActivity)
                        println("test False!!!!!!")
                        mAlertDialog.setTitle("คะแนน")
                        mAlertDialog.setMessage("การสุ่มไม่ตรงเงื่อนไข หรือ วันนี้คุณได้รับคะแนนไปแล้ว \n"+"--> สามารถสุ่มอีกครั้งได้ในวันพรุ่งนี้\n\n"+"หมายเหตุ : สิทธิในการสุ่มคือต้องมีความชื้นในดินมากกว่า 40%\n"+"    สามารถสุ่มได้เพียงวันละ 1 ครั้งเท่านั้น")
                        mAlertDialog.setIcon(R.mipmap.ic_leaf)
                        mAlertDialog.show()
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                }

            }
            databaseInfo.addListenerForSingleValueEvent(dateLimit)
        }

        //test
        //go back to dashboard
        var btnBackToDashboard = findViewById<View>(R.id.backWatering)
        btnBackToDashboard.setOnClickListener {
            btnBackToDashboard.startAnimation(scaleDown)
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
    }
fun getRandomData(){

    auth = FirebaseAuth.getInstance() // user
    val currentUser = FirebaseAuth.getInstance().currentUser //tamnameJa
    userId = currentUser?.uid

    var database = FirebaseDatabase.getInstance().reference.child("User").child(userId.toString()).child("score")

    var getScore = object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            addScore= snapshot.value.hashCode()
            println("addScore : "+addScore)
            addScore = addScore + getScoreRandom
            println(addScore)
            database.setValue(addScore)
        }

        override fun onCancelled(error: DatabaseError) {

        }
    }
    database.addListenerForSingleValueEvent(getScore)
}

}