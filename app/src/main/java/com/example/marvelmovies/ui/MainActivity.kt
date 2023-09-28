package com.example.marvelmovies.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.marvelmovies.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val downloadsFragment = DownloadsFragment()
        val categoriesFragment = CategoriesFragment()
        val moreFragment = MoreFragment()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        setCurrentFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_menu -> setCurrentFragment(homeFragment)
                R.id.downloads_menu -> setCurrentFragment(downloadsFragment)
                R.id.categories_menu -> setCurrentFragment(categoriesFragment)
                R.id.more_menu -> setCurrentFragment(moreFragment)

            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

}