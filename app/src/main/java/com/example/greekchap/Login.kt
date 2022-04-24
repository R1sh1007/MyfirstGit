package com.example.greekchap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var editemail:EditText
    private lateinit var editPassword:EditText
    private lateinit var logBtn: Button
    private lateinit var sinup:Button
   private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        editemail=findViewById(R.id.email)
        editPassword=findViewById(R.id.pass)
        logBtn=findViewById(R.id.login)
        sinup=findViewById(R.id.sinup)

        sinup.setOnClickListener{
            val intent = Intent(this,Singup::class.java)
            startActivity(intent)
        }

        logBtn.setOnClickListener{
            val email = editemail.text.toString()
            val pass=editPassword.text.toString()

            login(email,pass)
        }


    }

    private fun login(email: String, pass: String) {
        mAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent =Intent(this@Login,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@Login,"User Does Not Exit", Toast.LENGTH_LONG).show()
                }
            }
    }

}