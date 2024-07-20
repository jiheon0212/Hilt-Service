package com.example.dihiltlibrary

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.dihiltlibrary.databinding.ActivityMainBinding
import com.example.dihiltlibrary.viewmodel.FirebaseLoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navControllerMain: NavController
    private val viewModel: FirebaseLoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostMainFragment = supportFragmentManager.findFragmentById(R.id.service_container) as NavHostFragment
        navControllerMain = navHostMainFragment.navController
        binding.bottomNavigationView.setupWithNavController(navControllerMain)

        navControllerMain.addOnDestinationChangedListener { _, destination, _ ->
            if (checkControllerView()) {
                when (destination.id) {
                    R.id.postFragment, R.id.chatRoomFragment, R.id.settingsFragment, R.id.boardFragment, R.id.tradeFragment -> {
                        binding.bottomNavigationView.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.bottomNavigationView.visibility = View.GONE
                    }
                }
            } else binding.bottomNavigationView.visibility = View.GONE
        }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!navControllerMain.popBackStack()) {
                    finish()
                } else {
                    return
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        viewModel.setUserStatus(false)
    }

    override fun onResume() {
        super.onResume()
        if (checkControllerView()) viewModel.setUserStatus(true) else viewModel.setUserStatus(false)
    }

    private fun checkControllerView(): Boolean {
        val currentContainerViewType = binding.serviceContainer.visibility
        return if (currentContainerViewType == View.VISIBLE) true else false
    }
}