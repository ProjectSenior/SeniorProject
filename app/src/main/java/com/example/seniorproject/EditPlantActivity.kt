package com.example.seniorproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage


class EditPlantActivity : AppCompatActivity() {
    private var userId: String? = null
    private lateinit var auth: FirebaseAuth
    //lateinit var photo :ImageView
    lateinit var filepath : Uri
    var downloadUri: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_plant)
        val currentUser = FirebaseAuth.getInstance().currentUser
        userId = currentUser?.getUid()
        auth = FirebaseAuth.getInstance()

        var saveInfoBtn  = findViewById<Button>(R.id.saveBtn)
        var chooseFile = findViewById<Button>(R.id.chooseBtn)
        var upload = findViewById<Button>(R.id.uploadbtn)
        var photo = findViewById<ImageView>(R.id.editimageView)


        saveInfoBtn.setOnClickListener {
            //nope
        }

        chooseFile.setOnClickListener {
            filechooser()
        }
        upload.setOnClickListener {
            uploadFile()
        }

    }
    var EXTRA_URI  = ""
    private fun uploadFile() {
        var database = FirebaseDatabase.getInstance().reference.child("Plant") //create path
        var editNamePlant= findViewById<EditText>(R.id.editNameplant)
        var editDateStart = findViewById<EditText>(R.id.editDateStart)
        var editDateStartApp = findViewById<EditText>(R.id.editDatestartApp)
        var editAnno = findViewById<EditText>(R.id.editAnnotation)
        var namePlant = editNamePlant.text.toString()
        var dateStart = editDateStart.text.toString()
        var dateStartApp = editDateStartApp.text.toString()
        var anno = editAnno.text.toString()
        if (filepath!=null){
            var pd = ProgressDialog(this)
            pd.setTitle("Uploading")
            pd.show()
            var imageRef = FirebaseStorage.getInstance().reference.child("images/" + userId + ".jpg")
            imageRef.putFile(filepath)
                .addOnSuccessListener { p0 ->
                    pd.dismiss()
                    Toast.makeText(applicationContext, "File Upload", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener{ p0 ->
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
                                    dateStartApp,
                                    anno,downloadUri))
                } else {
                    // Handle failures
                    // ...
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
        if(requestCode==111 && resultCode == Activity.RESULT_OK && data != null){
            filepath = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
            photo.setImageBitmap(bitmap)
            println("++++++++++++++++++++++++")
            println(filepath.path)
        }
    }



}