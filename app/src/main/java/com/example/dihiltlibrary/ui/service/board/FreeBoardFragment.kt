package com.example.dihiltlibrary.ui.service.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dihiltlibrary.R
import com.example.dihiltlibrary.adapter.BoardAdapter
import com.example.dihiltlibrary.databinding.FragmentFreeBoardBinding
import com.example.dihiltlibrary.ui.service.BoardFragmentDirections
import com.example.dihiltlibrary.viewmodel.BoardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FreeBoardFragment : Fragment() {
    private lateinit var binding: FragmentFreeBoardBinding
    private lateinit var boardAdapter: BoardAdapter
    private val viewModel: BoardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFreeBoardBinding.inflate(layoutInflater, container, false)
        viewModel.updateBoard("Free")

        boardAdapter = BoardAdapter(mutableListOf()) { board ->
            val action = BoardFragmentDirections.actionBoardFragmentToBoardResultFragment(board)
            findNavController().navigate(action)
        }
        binding.freeRecycler.apply {
            adapter = boardAdapter
            layoutManager = LinearLayoutManager(context)
        }
        viewModel.boardList.observe(viewLifecycleOwner) { list ->
            boardAdapter.fetchPostTitleList(list)
        }

        return binding.root
    }
}