package com.example.greekchap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var userRecycle:RecyclerView
    private lateinit var userLiast:ArrayList<User>
    private lateinit var adapter:UserAdapter
    private lateinit var mAuth :FirebaseAuth
    private lateinit var mDbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        mDbRef=FirebaseDatabase.getInstance().getReference()
        userLiast = ArrayList()
        adapter =UserAdapter(this,userLiast)

        userRecycle =findViewById(R.id.recycleView)
        userRecycle.layoutManager = LinearLayoutManager(this)
        userRecycle.adapter =adapter


        mDbRef.child("user").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userLiast.clear()
                for (possnapt in snapshot.children){
                    val currentuser =possnapt.getValue(User::class.java)

                    if(mAuth.currentUser?.uid != currentuser?.uId){
                        userLiast.add(currentuser!!)
                    }
                  //  userLiast.add(currentuser!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        } )


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==R.id.menu){
            mAuth.signOut()
            val intent =Intent(this@MainActivity,Login::class.java)
            finish()
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

}