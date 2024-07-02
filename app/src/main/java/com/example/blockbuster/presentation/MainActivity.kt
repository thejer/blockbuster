package com.example.blockbuster.presentation

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.blockbuster.R
import com.example.blockbuster.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navigationView = binding.bottomNavigationView
        setUpNavigation(navigationView)
    }

    private fun setUpNavigation(navigationView: BottomNavigationView) {
        val navController = findNavController(R.id.nav_host_fragment_container)
        navigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.search -> {
                    navigationView.visibility = VISIBLE
                }

                R.id.watch_list -> {
                    navigationView.visibility = VISIBLE
                }

                else -> {
                    navigationView.visibility = GONE
                }
            }
        }
    }


}