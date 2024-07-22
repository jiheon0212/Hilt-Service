package com.example.dihiltlibrary.ui.service.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dihiltlibrary.GetTime.getCurrentTime
import com.example.dihiltlibrary.R
import com.example.dihiltlibrary.data.Board
import com.example.dihiltlibrary.databinding.FragmentBoardWriteBinding
import com.example.dihiltlibrary.navigation.NavigateSet.popBackAndReturn
import com.example.dihiltlibrary.viewmodel.BoardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardWriteFragment : Fragment() {
    private lateinit var binding: FragmentBoardWriteBinding
    private val viewModel: BoardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBoardWriteBinding.inflate(layoutInflater, container, false)
        viewModel.uploadStatus.observe(viewLifecycleOwner) { complete ->
            if (complete) popBackAndReturn(findNavController())
            else Toast.makeText(context, "board upload failed by error", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.boardWriteBtn.setOnClickListener {
            val title = binding.boardTitleEditText.text.toString()
            val selectedRadioButtonId = binding.boardWriteRadioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                val selectedRadioButton = activity?.findViewById<RadioButton>(selectedRadioButtonId)
                val selectedCategory = selectedRadioButton?.text.toString()

                viewModel.writeBoard(
                    Board(
                    id = viewModel.currentUserId,
                    title = title,
                    category = selectedCategory,
                    timestamp = getCurrentTime())
                )
            }
        }
    }
}