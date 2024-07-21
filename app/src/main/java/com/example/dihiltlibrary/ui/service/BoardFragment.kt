package com.example.dihiltlibrary.ui.service

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dihiltlibrary.R
import com.example.dihiltlibrary.adapter.BoardAdapter
import com.example.dihiltlibrary.databinding.FragmentBoardBinding
import com.example.dihiltlibrary.ui.service.board.FreeBoardFragment
import com.example.dihiltlibrary.ui.service.board.MeetingBoardFragment
import com.example.dihiltlibrary.ui.service.board.ParentingBoardFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardFragment : Fragment() {
    private lateinit var binding: FragmentBoardBinding
    private lateinit var boardAdapter: BoardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBoardBinding.inflate(layoutInflater, container, false)
        boardAdapter = BoardAdapter(mutableListOf()) { title ->
            val action = BoardFragmentDirections.actionBoardFragmentToBoardResultFragment(title)
            findNavController().navigate(action)
        }
        replaceFragment(ParentingBoardFragment())

        binding.noticeTabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(p0: TabLayout.Tab?) {
                p0?.let {
                    when (it.position) {
                        0 -> replaceFragment(ParentingBoardFragment())
                        1 -> replaceFragment(FreeBoardFragment())
                        2 -> replaceFragment(MeetingBoardFragment())
                    }
                }
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })

        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.boardBtn.setOnClickListener {
            val action = BoardFragmentDirections.actionBoardFragmentToBoardWriteFragment()
            findNavController().navigate(action)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.notice_container, fragment)
            .commit()
    }
}