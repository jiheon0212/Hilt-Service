package com.example.dihiltlibrary.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.dihiltlibrary.R
import com.example.dihiltlibrary.databinding.FragmentFirebaseLoginBinding
import com.example.dihiltlibrary.viewmodel.FirebaseLoginViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirebaseLoginFragment : Fragment() {
    private lateinit var binding: FragmentFirebaseLoginBinding
    private val viewModel: FirebaseLoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirebaseLoginBinding.inflate(layoutInflater, container, false)

        viewModel.userUid.observe(viewLifecycleOwner) { userUid ->
            viewModel.getUserBasicInfo(userUid)
        }
        // user uid가 firestore document에 존재한다면 -> main flow 진입 / 존재하지 않는다면 -> setUserInfo fragment 진입
        viewModel.userBasicInfo.observe(viewLifecycleOwner) { userBasicInfo ->
            if (userBasicInfo != null) navigateService() else {
                val action = FirebaseLoginFragmentDirections.actionFirebaseLoginFragmentToSetUserInfoFragment()
                findNavController().navigate(action)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginFragBtnLogin.setOnClickListener {
            viewModel.loginAnonymously()
        }
    }

    private fun navigateService() {
        activity?.findViewById<FragmentContainerView>(R.id.login_container)?.visibility = View.GONE

        activity?.findViewById<FragmentContainerView>(R.id.service_container)?.visibility = View.VISIBLE
        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)?.visibility = View.VISIBLE

        val navController = (activity?.supportFragmentManager?.findFragmentById(R.id.service_container) as NavHostFragment).navController
        navController.navigate(R.id.homeFragment)
    }
}