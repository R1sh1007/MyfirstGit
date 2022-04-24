package com.example.greekchap

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Singup : AppCompatActivity() {
    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var passw: EditText
    private lateinit var sinup: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef :DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singup)

        mAuth = FirebaseAuth.getInstance()
        editEmail=findViewById(R.id.email)
        editName=findViewById(R.id.name)
        passw=findViewById(R.id.pass)
        sinup=findViewById(R.id.sinup)

        sinup.setOnClickListener{
            val email = editEmail.text.toString()
            val pass=passw.text.toString()
            val name=editName.text.toString()

            sinup(name,email,pass)
        }
    }

    private fun sinup(name:String,email: String, pass: String) {

        mAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,email, mAuth.currentUser?.uid!!)
                 val intent =Intent(this@Singup,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                   Log.e("Error", task.getException().toString())
                }
            }

    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
    mDbRef=FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name,email,uid))
    }
}