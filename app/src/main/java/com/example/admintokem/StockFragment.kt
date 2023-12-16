package com.example.admintokem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class StockFragment : Fragment() {
    private lateinit var recylerStock: RecyclerView
    private lateinit var firestore: FirebaseFirestore
    private val stockViewModel: StockViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recylerStock = view.findViewById(R.id.recyclerStock)
        firestore = FirebaseFirestore.getInstance()

        GlobalScope.launch { getDataFromFirestore() }

        stockViewModel.listStock.observe(viewLifecycleOwner) {neValue ->
            recylerStock.layoutManager = LinearLayoutManager(requireContext())
            recylerStock.adapter = AdapterStock(neValue,requireContext(),stockViewModel,firestore,viewLifecycleOwner)
        }

    }

    suspend fun getDataFromFirestore(){
        val database = firestore.collection("bunga").get().await()
        withContext(Dispatchers.IO){
            database?.let {document ->
                val listKembang = document.map { doc ->
                    StockModel(
                        doc.id,
                        doc.getString("Nama_bunga")?: "",
                        doc.getString("Gambar")?:"",
                        (doc["Stok"] as? Number)?.toInt()?:0,
                        (doc["Harga"] as? Number)?.toInt()?: 0,

                    )
                }

                stockViewModel._listStock.postValue(listKembang.toMutableList())


            }
        }
    }


}