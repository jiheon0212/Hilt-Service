package com.example.dihiltlibrary

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.dihiltlibrary.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navControllerMain: NavController
    private lateinit var navControllerLogin: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostMainFragment = supportFragmentManager.findFragmentById(R.id.service_container) as NavHostFragment
        navControllerMain = navHostMainFragment.navController
        binding.bottomNavigationView.setupWithNavController(navControllerMain)

        val navHostLoginFragment = supportFragmentManager.findFragmentById(R.id.login_container) as NavHostFragment
        navControllerLogin = navHostLoginFragment.navController

        binding.serviceContainer.visibility = View.GONE
        binding.bottomNavigationView.visibility = View.GONE
    }
}