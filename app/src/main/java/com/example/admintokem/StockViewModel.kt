package com.example.admintokem

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StockViewModel : ViewModel() {

    var _listStock : MutableLiveData<MutableList<StockModel>> = MutableLiveData(listStockAnyar)


    val listStock : LiveData<MutableList<StockModel>>
        get() = _listStock



    fun decrement(konteks: Context,posisi: Int){
        if (_listStock.value?.get(posisi)!!.counter <= 0){
            Toast.makeText(konteks, "Tidak Bisa Ngurangi Lagi", Toast.LENGTH_SHORT).show()
        }else{
            _listStock.value?.get(posisi)!!.counter -= 1
        }

    }

    fun increment(posisi: Int){
        _listStock.value?.get(posisi)!!.counter +=1
    }

}