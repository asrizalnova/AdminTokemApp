package com.example.admintokem

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore

class AdapterStock(val listStock: MutableList<StockModel>,val konteks: Context,val viewModel : StockViewModel, val firestore: FirebaseFirestore,val viewLifecycle: LifecycleOwner): RecyclerView.Adapter<AdapterStock.StockViewHolder>() {

    class StockViewHolder(baris: View) : RecyclerView.ViewHolder(baris){
        val tvNamaBunga = baris.findViewById<TextView>(R.id.textViewNamaBunga)
        val tvHargaBunga = baris.findViewById<TextView>(R.id.textViewHarga)
        val tvStockBunga = baris.findViewById<TextView>(R.id.textViewStok)
        val tvCounter = baris.findViewById<TextView>(R.id.tvCounter)
        val btnIncrement = baris.findViewById<Button>(R.id.btnTambah)
        val btnDecrement= baris.findViewById<Button>(R.id.btnMinus)
        val btnTambah = baris.findViewById<Button>(R.id.buttonTambah)
        val gambarBunga = baris.findViewById<ImageView>(R.id.GambarBunga)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.layout_stock,parent,false)
        return StockViewHolder(layout)
    }

    override fun getItemCount(): Int {
       return  listStock.size
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val bind = listStock[position]
        holder.tvNamaBunga.text =bind.nama
        holder.tvHargaBunga.text = bind.harga.toString()

        holder.tvCounter.text = bind.counter.toString()

        Glide.with(konteks).load(bind.gambar).placeholder(R.drawable.ic_launcher_foreground).into(holder.gambarBunga)

        holder.btnIncrement.setOnClickListener {
            viewModel.increment(position)
            notifyDataSetChanged()
        }

        holder.btnDecrement.setOnClickListener {
            viewModel.decrement(konteks,position)
            notifyDataSetChanged()
        }

        holder.btnTambah.setOnClickListener {
            updateDataFirestore(bind.id,bind.counter,bind.stock)
            notifyDataSetChanged()
        }

        viewModel._listStock.observe(viewLifecycle){newvalue ->
            holder.tvStockBunga.text = newvalue[position].stock.toString()
        }

    }

    fun updateDataFirestore(id: String,stock: Int,stockAsli : Int){
        firestore.collection("bunga").document(id).update("Stok",stock+stockAsli).addOnSuccessListener {
            Toast.makeText(konteks, "Berhasil Update", Toast.LENGTH_SHORT).show()
            notifyDataSetChanged()

        }.addOnFailureListener {
            Toast.makeText(konteks, "Gagal Update", Toast.LENGTH_SHORT).show()
        }

    }
}