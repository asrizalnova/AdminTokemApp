package com.example.admintokem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottomNavigation)


        val stockFragment = StockFragment()
        val chatFragment = ChatFragment()
        bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.stock -> changFragment(stockFragment)
                R.id.pesan -> changFragment(chatFragment)

                else -> {
                    val gagal = Intent(this,MainActivity::class.java)
                    startActivity(gagal)
                }
            }
            true
        }


    }

    fun changFragment(fragment: Fragment){
        val transaksi = supportFragmentManager.beginTransaction()
        transaksi.replace(R.id.fragmentContainerView,fragment)
        transaksi.commit()
    }
}