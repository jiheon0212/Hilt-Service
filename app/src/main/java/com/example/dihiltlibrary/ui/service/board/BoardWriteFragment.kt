package com.example.dihiltlibrary.ui.service.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dihiltlibrary.R
import com.example.dihiltlibrary.databinding.FragmentBoardWriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardWriteFragment : Fragment() {
    private lateinit var binding: FragmentBoardWriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBoardWriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.boardWriteBtn.setOnClickListener {
            val title = binding.boardTitleEditText.text.toString()
        }
    }
}