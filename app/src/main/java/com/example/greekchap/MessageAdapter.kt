package com.example.greekchap

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context ,val messageList : ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECIVE=1
    val ITEM_SENT=2

    class SentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.sent_text)

    }

    class ReciveViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val reciveMassage = itemView.findViewById<TextView>(R.id.recive_text)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       if(viewType ==1){
           val view :View = LayoutInflater.from(context).inflate(R.layout.recived,parent,false)
           return MessageAdapter.ReciveViewHolder(view)
       }else {
           val view: View = LayoutInflater.from(context).inflate(R.layout.sent, parent, false)
           return MessageAdapter.SentViewHolder(view)
       }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage =messageList[position]
        if(holder.javaClass == SentViewHolder::javaClass){
            val viewHolder = holder as SentViewHolder
            viewHolder.sentMessage.text = currentMessage.message
        }else{
            val viewHolder = holder as ReciveViewHolder
            viewHolder.reciveMassage.text = currentMessage.message
        }
    }

    override fun getItemCount(): Int {
      return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage =messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderID)){
            return ITEM_SENT
        }else{
            return ITEM_RECIVE
        }
    }

}