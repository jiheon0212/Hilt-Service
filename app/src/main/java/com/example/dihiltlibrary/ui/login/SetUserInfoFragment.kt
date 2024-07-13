package com.example.dihiltlibrary.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dihiltlibrary.databinding.FragmentSetUserInfoBinding

class SetUserInfoFragment : Fragment() {
    private lateinit var binding: FragmentSetUserInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetUserInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}