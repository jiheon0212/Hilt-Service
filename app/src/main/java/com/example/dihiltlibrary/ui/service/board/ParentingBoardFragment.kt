package com.example.dihiltlibrary.ui.service.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dihiltlibrary.databinding.FragmentParentingBoardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParentingBoardFragment : Fragment() {
    private lateinit var binding: FragmentParentingBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentParentingBoardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}