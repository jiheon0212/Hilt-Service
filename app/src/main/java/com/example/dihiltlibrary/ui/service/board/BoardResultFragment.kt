package com.example.dihiltlibrary.ui.service.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.dihiltlibrary.databinding.FragmentBoardResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardResultFragment : Fragment() {
    private lateinit var binding: FragmentBoardResultBinding
    private val args: BoardResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBoardResultBinding.inflate(layoutInflater, container, false)
        val board = args.board
        binding.boardResultId.text = board.id
        binding.boardResultTitle.text = board.title
        binding.boardResultText.text = board.content?.text
        binding.boardResultImage // glide image insert
        binding.boardResultTimestamp.text = board.timestamp

        return binding.root
    }
}