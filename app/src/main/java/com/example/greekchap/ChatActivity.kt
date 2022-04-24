package com.example.greekchap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messaseBox : EditText
    private lateinit var sentButton : ImageView
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageList :ArrayList<Message>
    private lateinit var mDbRef : DatabaseReference
    var reciverRoom :String ?= null
    var senderRoom : String ? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")
        val reciverUId = intent.getStringExtra("uid")
        mDbRef = FirebaseDatabase.getInstance().getReference()
        val senderuid= FirebaseAuth.getInstance().currentUser?.uid
        senderRoom=  reciverUId + senderuid
        reciverRoom = senderuid + reciverUId
        supportActionBar?.title =name
        chatRecyclerView = findViewById(R.id.chatList)
        messaseBox = findViewById(R.id.message_box)
        sentButton = findViewById(R.id.sent_btn)
        messageList = ArrayList()
        messageAdapter =MessageAdapter(this,messageList)

        chatRecyclerView.layoutManager =LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter

        mDbRef.child("chat").child(senderRoom!!).child("messages").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                messageList.clear()
                for (postsnap in snapshot.children){
                    val message = postsnap.getValue(Message::class.java)
                    messageList.add(message!!)
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        sentButton.setOnClickListener{
            val message =messaseBox.text.toString()
            val messObject= Message(message,senderuid)
            mDbRef.child("chat").child(senderRoom!!).child("messages").push()
                .setValue(messObject).addOnSuccessListener {
                    mDbRef.child("chat").child(reciverRoom!!).child("messages").push()
                        .setValue(messObject)
                }
            messaseBox.setText("")
        }

    }
}