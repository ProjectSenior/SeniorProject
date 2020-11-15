package com.example.seniorproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    private lateinit var text_input: String
    private lateinit var text_password: String
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        var btnLogin = findViewById<Button>(R.id.btnLogin)
        var signup = findViewById<TextView>(R.id.signUp)

        btnLogin.setOnClickListener {
            doLogin()
        }

        signup.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


    }

    //for auto login if user already
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
//            if(currentUser.isEmailVerified) {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
//            }else{
//                Toast.makeText(baseContext,"Please verify your email address.",Toast.LENGTH_LONG).show()
//            }
        }
    }

    private fun doLogin() {
        var inputEmail = findViewById<TextView>(R.id.inputEmail)
        var inputPassword = findViewById<TextView>(R.id.inputPassword)
        if (inputEmail.text.toString().isEmpty()) {
            inputEmail.error = "Please enter email"
            inputEmail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher((inputEmail.text.toString())).matches()) {
            inputEmail.error = "Please enter valid email"
            return
        }
        if (inputPassword.text.toString().isEmpty()) {
            inputPassword.error = "Please enter password"
            inputPassword.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(inputEmail.text.toString(), inputPassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        updateUI(user)
                        Log.e("Action", "login success")
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Login failed.",
                                Toast.LENGTH_SHORT).show()
                        updateUI(null)

                        // ...
                    }

                    // ...
                }
    }


}