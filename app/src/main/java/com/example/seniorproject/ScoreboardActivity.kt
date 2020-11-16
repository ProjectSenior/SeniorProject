package com.example.seniorproject

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class ScoreboardActivity : AppCompatActivity() {
    class User(var name: String, var Email: String, var password: String)

    private lateinit var auth: FirebaseAuth

    //for create animation
    lateinit var scaleUp: Animation;
    lateinit var scaleDown: Animation;

    var arraylist: ArrayList<User>? = null
    var adapter: ArrayAdapter<User>? = null
    var numberOfInputWords: Int = 0;
    var s:String="best";
    var tabspace:String="";
    var testtext:String="*";
    var checknum:Int=0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser?.uid
        var textView = findViewById<TextView>(R.id.showUser)
        var datebaseRef = FirebaseDatabase.getInstance().getReference("User")
        var database = FirebaseDatabase.getInstance().reference.child("User") //create path
        var btnBackToDashboard = findViewById<ImageView>(R.id.backScoreboard)

        scaleUp = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.scale_up)
        scaleDown = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.scale_down)

        btnBackToDashboard.setOnClickListener {
            btnBackToDashboard.startAnimation(scaleDown)
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)

        }


        var getdata = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb = StringBuilder()
                for (i in snapshot.children) {
                    var usernamedb = i.child("name").getValue()
                    var emaildb = i.child("email").getValue()
                    var passworddb = i.child("password").getValue()
                    var scoredb = i.child("score").getValue()
                    var count = 0

                    val words = usernamedb.toString().trim()
                    numberOfInputWords = words.length
                    checknum=0
                    tabspace=""
                    for( i in numberOfInputWords until 18){
                        tabspace = tabspace+"-"
                        checknum+=1
                    }
                    println("UsernameDB Size: "+numberOfInputWords)
                    println("Size num: "+checknum)

                    sb.append("$usernamedb"+tabspace+"$scoredb\n")
//                    sb.append(" $usernamedb  " + "                          $scoredb\n")
                }
                textView.setText(sb)
            }

            override fun onCancelled(error: DatabaseError) {
                //Toast.makeText(this,"showtext"+name.toString(),Toast.LENGTH_LONG).show()
            }

        }
        database.addValueEventListener(getdata)

    }
}

