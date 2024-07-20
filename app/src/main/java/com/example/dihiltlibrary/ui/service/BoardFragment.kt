package com.example.dihiltlibrary.ui.service

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dihiltlibrary.databinding.FragmentBoardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardFragment : Fragment() {
    private lateinit var binding: FragmentBoardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBoardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}