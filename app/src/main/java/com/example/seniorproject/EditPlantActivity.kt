package com.example.seniorproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.Date as Date1
import java.util.Calendar

class EditPlantActivity : AppCompatActivity() {
    private var userId: String? = null
    private lateinit var auth: FirebaseAuth
    lateinit var filepath: Uri
    var downloadUri: String = ""
    var file: File? = null
    var formatDate = SimpleDateFormat("dd MMMM YYYY")

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_plant)
        val currentUser = FirebaseAuth.getInstance().currentUser
        userId = currentUser?.getUid()
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference.child("Plant").child(userId.toString())
        var getDatabase = FirebaseDatabase.getInstance().getReference("Plant").child(userId.toString())
        var chooseFile = findViewById<Button>(R.id.chooseBtn)
        var upload = findViewById<Button>(R.id.uploadbtn)
        var photo = findViewById<ImageView>(R.id.editimageView)
        var editNamePlant = findViewById<EditText>(R.id.editNameplant)
        var editAnno = findViewById<EditText>(R.id.editAnnotation)
        var namePlant = editNamePlant.text.toString()
        //var dateStart = editDateStart.text.toString()
        var anno = editAnno.text.toString()
        //var calendarText = findViewById<Button>(R.id.calenderText)
        val today = Calendar.getInstance()
        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DAY_OF_MONTH)
        var showTextCalender = findViewById<TextView>(R.id.showCalen)

        showTextCalender.setOnClickListener{

            val datepicker = DatePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,DatePickerDialog.OnDateSetListener{
                datePicker, i, i2, i3 -> val selectDate = Calendar.getInstance()
                selectDate.set(Calendar.YEAR,i)
                selectDate.set(Calendar.MONTH,i2)
                selectDate.set(Calendar.DAY_OF_MONTH,i3)
                if (selectDate ==null){
                    showTextCalender.setText("กดเพื่อเลือกวันที่")
                }
                else{
                    val date = formatDate.format(selectDate.time)
                showTextCalender.setText(date)
                }
            },today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH))
            datepicker.show()

        }

        var getImage = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var getdata = snapshot.child("imageUrl").value
                Picasso.get().load(getdata.toString().toUri()).into(photo)
                var getdataName = snapshot.child("plantName").value
                var getdataAnno = snapshot.child("anno").value
                var getdataUri = snapshot.child("imageUrl").value
                var getdataDate = snapshot.child("date").value
                var imageRef = FirebaseStorage.getInstance().reference.child("images/" + userId + ".jpg")
                if (getdataName == null && getdataDate == null && getdataAnno == null) {
                    editNamePlant.setText("")
                    showTextCalender.setText("กดเพื่อเลือกวันที่")
                    editAnno.setText("")
                } else {
                    editNamePlant.setText(getdataName.toString())
                    showTextCalender.setText(getdataDate.toString())
                    editAnno.setText(getdataAnno.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        database.addValueEventListener(getImage)

        chooseFile.setOnClickListener {
            filechooser()
        }
        upload.setOnClickListener {
            uploadFile()
            val intent = Intent(this, PlantActivity::class.java)
            startActivity(intent)
        }

        //go back to dashboard
        var btnBackToDashboard = findViewById<View>(R.id.backEditprofile)
        btnBackToDashboard.setOnClickListener {

            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun uploadFile() {
        var database = FirebaseDatabase.getInstance().reference.child("Plant") //create path
        var editNamePlant = findViewById<EditText>(R.id.editNameplant)
        var editDateStart = findViewById<TextView>(R.id.showCalen)
        var editAnno = findViewById<EditText>(R.id.editAnnotation)
        var namePlant = editNamePlant.text.toString()
        var dateStart = editDateStart.text.toString()
        var anno = editAnno.text.toString()


        if (filepath != null) {
            var pd = ProgressDialog(this)
            pd.setTitle("Uploading")
            pd.show()
            var imageRef = FirebaseStorage.getInstance().reference.child("images/" + userId + ".jpg")
            imageRef.putFile(filepath)
                    .addOnSuccessListener { p0 ->
                        pd.dismiss()
                        Toast.makeText(applicationContext, "File Upload", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener { p0 ->
                        pd.dismiss()
                        Toast.makeText(applicationContext, p0.message, Toast.LENGTH_LONG).show()
                    }
                    .addOnProgressListener { p0 ->
                        var progess = (100.0 * p0.bytesTransferred) / p0.totalByteCount
                        pd.setMessage("Uploaded ${progess.toInt()}%")
                    }
            println("------------------------------")
            println(imageRef.downloadUrl.toString())


            val uploadTask = imageRef.putFile(filepath!!)
            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                imageRef.downloadUrl

            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    downloadUri = task.result.toString()
                    println("Download = this " + downloadUri)
                    println(downloadUri)
                    println("///////")
                    database.child(userId.toString()).setValue(
                            PlantInfo(
                                    namePlant,
                                    dateStart,
                                    anno, downloadUri))


                } else {
                    println("ERROR")
                }
            }

        }
    }

    private fun filechooser() {

        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i, "Choose PHOTO"), 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var photo = findViewById<ImageView>(R.id.editimageView)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null) {
            filepath = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
            photo.setImageBitmap(bitmap)
            println("++++++++++++++++++++++++")
            println(filepath.path)
        }
    }


}