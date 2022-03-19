package com.example.cryptoflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.cryptoflow.placeholder.Coin
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    var coins : MutableList<Coin> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomMenu = findViewById<BottomNavigationView>(R.id.activity_main_bottom_navigation)


        // custom menu to select fragments
        bottomMenu.selectedItemId = R.id.action_home
        bottomMenu.setOnItemSelectedListener { menu ->
            when(menu.itemId){
                R.id.action_convert -> {
                    setFragment(ConvertFrag())
                    true
                }
                R.id.action_home -> {
                    setFragment(AllCryptoFrag())
                    true
                }
                R.id.action_news -> {
                    setFragment(MediaFrag())
                    true
                }
                else -> false
            }
        }
        // End MENU
    }

    // fragment management

    private fun setFragment(fragment : Fragment){
        val frag = supportFragmentManager.beginTransaction()
        frag.replace(R.id.fragmentContainerView,fragment)
        frag.commit()
    }
}


