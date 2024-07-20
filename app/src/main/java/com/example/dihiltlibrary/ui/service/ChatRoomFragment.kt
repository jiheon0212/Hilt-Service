package com.example.dihiltlibrary.ui.service

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dihiltlibrary.R
import com.example.dihiltlibrary.adapter.ChatRoomAdapter
import com.example.dihiltlibrary.databinding.FragmentChatRoomBinding
import com.example.dihiltlibrary.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatRoomFragment : Fragment() {
    private lateinit var binding: FragmentChatRoomBinding
    private val viewModel: ChatViewModel by viewModels()
    private val chatRoomAdapter = ChatRoomAdapter { roomId ->
        val action = ChatRoomFragmentDirections.actionChatRoomFragmentToMessageFragment(roomId)
        findNavController().navigate(action)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatRoomBinding.inflate(layoutInflater, container, false)
        binding.chatRoomRecycler.apply {
            adapter = chatRoomAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.updateChatRoom(viewModel.currentUserId!!)
        viewModel.roomIds.observe(viewLifecycleOwner) { list ->
            chatRoomAdapter.fetchChatroom(list)
            binding.chatRoomRecycler.scrollToPosition(list.size - 1)
        }

        return binding.root
    }
}