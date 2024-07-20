package com.example.dihiltlibrary.ui.service

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dihiltlibrary.adapter.PostAdapter
import com.example.dihiltlibrary.adapter.StatusAdapter
import com.example.dihiltlibrary.databinding.FragmentPostBinding
import com.example.dihiltlibrary.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment : Fragment() {
    private lateinit var binding: FragmentPostBinding
    private val viewModel: PostViewModel by viewModels()
    private val postAdapter = PostAdapter(mutableListOf())
    private lateinit var statusAdapter: StatusAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostBinding.inflate(layoutInflater, container, false)
        // contact user icon -> target+current -> init chat room id -> safeargs 순서로 채팅방 구성 -> message 송수신
        statusAdapter = StatusAdapter(mutableListOf()) { id ->
            val roomId = initRoomId(id, viewModel.currentUserId!!)
            val action = PostFragmentDirections.actionPostFragmentToMessageFragment(roomId)
            findNavController().navigate(action)
        }

        binding.postRecycler.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(context)
        }
        binding.postUserStatusRecycler.apply {
            adapter = statusAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.postSearchView.setOnQueryTextListener(object:
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                filterPost(newText)
                return true
            }
        })

        viewModel.post.observe(viewLifecycleOwner) { list ->
            postAdapter.fetchPost(list)
            binding.postRecycler.scrollToPosition(list.size - 1)
        }

        viewModel.getUserStatus()
        viewModel.currentStatus.observe(viewLifecycleOwner) { list ->
            statusAdapter.updateCurrentUserList(list)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.postBtn.setOnClickListener {
            val action = PostFragmentDirections.actionPostFragmentToAddPostFragment()
            findNavController().navigate(action)
        }
    }

    private fun filterPost(query: String?) {
        val filteredPostList = viewModel.post.value?.filter { post ->
            post.postContent?.contentName!!.contains(query ?: "", ignoreCase = true)
        }?.toMutableList()
        postAdapter.fetchPost(filteredPostList!!)
    }

    private fun initRoomId(target: String, current: String): String {
        return if (target < current) target+current else current+target
    }
}