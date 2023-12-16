package com.example.admintokem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterChat(val list: MutableList<chatModel>): RecyclerView.Adapter<AdapterChat.ChatViewHolder>() {

    class ChatViewHolder(baris: View): RecyclerView.ViewHolder(baris){
        val tvNamaPembeli = baris.findViewById<TextView>(R.id.tvNamaPembeli)
        val tvChat = baris.findViewById<TextView>(R.id.tvTextPembeli)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.layout_chat,parent,false)
        return ChatViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val bind = list[position]
        holder.tvChat.text = bind.pesan
        holder.tvNamaPembeli.text = bind.pengirim
    }
}