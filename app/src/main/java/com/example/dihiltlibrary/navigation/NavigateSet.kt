package com.example.dihiltlibrary.navigation

import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.dihiltlibrary.MainActivity
import com.example.dihiltlibrary.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Singleton

@Singleton
object NavigateSet {
    fun navigateToService(activity: FragmentActivity?) {
        activity?.findViewById<FragmentContainerView>(R.id.login_container)?.visibility = View.GONE

        activity?.findViewById<FragmentContainerView>(R.id.service_container)?.visibility = View.VISIBLE
        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)?.visibility = View.VISIBLE

        val navController = (activity?.supportFragmentManager?.findFragmentById(R.id.service_container) as NavHostFragment).navController
        navController.navigate(R.id.postFragment)
    }

    fun popBackAndReturn(navController: NavController) {
        navController.popBackStack()
    }
}