package com.example.dihiltlibrary.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dihiltlibrary.databinding.FragmentFirebaseLoginBinding

class FirebaseLoginFragment : Fragment() {
    private lateinit var binding: FragmentFirebaseLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirebaseLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}