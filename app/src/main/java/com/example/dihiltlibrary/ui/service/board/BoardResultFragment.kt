package com.example.dihiltlibrary.ui.service.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dihiltlibrary.R
import com.example.dihiltlibrary.databinding.FragmentBoardResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardResultFragment : Fragment() {
    private lateinit var binding: FragmentBoardResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBoardResultBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}