package com.example.seniorproject

import android.content.Intent
import android.os.Bundle
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

    var arraylist: ArrayList<User>? = null
    var adapter: ArrayAdapter<User>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser?.uid
        var textView = findViewById<TextView>(R.id.showUser)
        var datebaseRef = FirebaseDatabase.getInstance().getReference("User")
        var database = FirebaseDatabase.getInstance().reference.child("User") //create path
        var backBtn = findViewById<ImageView>(R.id.backInfomation)

        backBtn.setOnClickListener {
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


                    sb.append(" $usernamedb  " + "                          $scoredb\n")
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