package com.example.dihiltlibrary

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

    private val PERMISSIONS = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.INTERNET
    )
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!hasPermission()) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE)
        }

        val navHostMainFragment = supportFragmentManager.findFragmentById(R.id.service_container) as NavHostFragment
        navControllerMain = navHostMainFragment.navController
        binding.bottomNavigationView.setupWithNavController(navControllerMain)

        navControllerMain.addOnDestinationChangedListener { _, destination, _ ->
            if (checkControllerView()) {
                when (destination.id) {
                    R.id.postFragment, R.id.chatRoomFragment, R.id.groupFragment, R.id.boardFragment, R.id.tradeFragment -> {
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
    private fun hasPermission(): Boolean {
        for (permission in PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
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
        return currentContainerViewType == View.VISIBLE
    }
}