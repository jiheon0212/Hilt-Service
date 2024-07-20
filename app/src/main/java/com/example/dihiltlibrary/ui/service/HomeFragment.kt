package com.example.dihiltlibrary.ui.service

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dihiltlibrary.adapter.HomePostAdapter
import com.example.dihiltlibrary.databinding.FragmentHomeBinding
import com.example.dihiltlibrary.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: PostViewModel by viewModels()
    private val homePostAdapter = HomePostAdapter(mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        binding.homeRecycler.apply {
            adapter = homePostAdapter
            layoutManager = LinearLayoutManager(context)
        }
        binding.homeSearchView.setOnQueryTextListener(object:
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                filterPost(newText)
                return true
            }
        })

        viewModel.post.observe(viewLifecycleOwner) { list ->
            homePostAdapter.fetchPost(list)
            binding.homeRecycler.scrollToPosition(list.size - 1)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeBtn.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddPostFragment()
            findNavController().navigate(action)
        }
    }
    private fun filterPost(query: String?) {
        val filteredPostList = viewModel.post.value?.filter { post ->
            post.postContent?.contentName!!.contains(query ?: "", ignoreCase = true)
        }?.toMutableList()
        homePostAdapter.fetchPost(filteredPostList!!)
    }
}