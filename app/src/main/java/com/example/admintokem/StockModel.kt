package com.example.admintokem

data class StockModel(
    val id: String = "",
    val nama: String = "",
    val gambar: String = "",
    var stock: Int = 0,
    val harga: Int = 0,
    var counter: Int = 0
)
