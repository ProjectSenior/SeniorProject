    package com.example.seniorproject

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.IOException
import com.google.android.gms.tasks.OnSuccessListener as OnSuccessListener1


class PlantActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var userId: String? = null
    lateinit var reff: DatabaseReference
    lateinit var filePath: Uri
    var curFile: Uri? = null
    //for create animation
    lateinit var scaleUp: Animation;
    lateinit var scaleDown: Animation;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant)
        auth = FirebaseAuth.getInstance() // user
        val currentUser = FirebaseAuth.getInstance().currentUser //tamnameJa
        userId = currentUser?.uid
        var database = FirebaseDatabase.getInstance().reference.child("Plant").child(userId.toString())
        var getstorage = FirebaseStorage.getInstance().getReference("Images/")
        var editImage = findViewById<ImageView>(R.id.editimageView)
        var namePlant = findViewById<TextView>(R.id.plantnameText)
        var dateofPlant = findViewById<TextView>(R.id.dateofplant)
        var infoAnnotationText = findViewById<TextView>(R.id.infoanno)
        scaleUp = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.scale_up)
        scaleDown = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.scale_down)
        //var storageRef = FirebaseStorage.getInstance("images/").reference


        //go back to dashboard
        var btnBackToDashboard = findViewById<View>(R.id.backPlant)
        btnBackToDashboard.setOnClickListener {
            btnBackToDashboard.startAnimation(scaleDown)
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }


        var getdataImage = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var getdata = snapshot.child("imageUrl").value
                val uri = Uri.parse(getdata.toString())
                Picasso.get().load(getdata.toString().toUri()).into(editImage)
                println("Picasso = " + getdata.toString().toUri())
                println("getdata = " + getdata)
                println("imageurl = " + getdata.toString())
                println(snapshot.value)
                //editImage.setImageURI(getdata)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addValueEventListener(getdataImage)

        var getdataNamePlant = object : ValueEventListener { // get data from firebase name
            override fun onDataChange(snapshot: DataSnapshot) {
                var name1 = snapshot.child("plantName").value
                if (name1 == null) {
                    namePlant.setText(" ")
                } else {
                    namePlant.setText(name1.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        database.addValueEventListener(getdataNamePlant)



        var getdataDateplant = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var datePlant = snapshot.child("date").value
                println("*****"+datePlant)
                if (datePlant == null) {
                    dateofPlant.text = " "

                } else {
                    dateofPlant.setText(datePlant.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addValueEventListener(getdataDateplant)   //เรียกการใช้งาน
        //database.addListenerForSingleValueEvent(getdataDateplant)


        var getdataInfoAnnotation = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var info = snapshot.child("anno").value
                if (info == null) {
                    infoAnnotationText.setText(" ")
                } else {
                    infoAnnotationText.setText(info.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addValueEventListener(getdataInfoAnnotation)   //เรียกการใช้งาน
    }

    private fun isSelected() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "SELECT PICTURE"), 1)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 &&
                data != null && data.data != null) {
            filePath = data.data!!
            var editImage = findViewById<ImageView>(R.id.editimageView)
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                editImage.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


}
