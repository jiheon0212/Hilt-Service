package com.example.dihiltlibrary.ui.service

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dihiltlibrary.GetTime.getCurrentTime
import com.example.dihiltlibrary.data.Content
import com.example.dihiltlibrary.data.Post
import com.example.dihiltlibrary.databinding.FragmentAddPostBinding
import com.example.dihiltlibrary.navigation.NavigateSet.popBackAndReturn
import com.example.dihiltlibrary.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPostFragment : Fragment() {
    private lateinit var binding: FragmentAddPostBinding
    private val viewmodel: PostViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPostBinding.inflate(layoutInflater, container, false)
        viewmodel.uploadStatus.observe(viewLifecycleOwner) { complete ->
            if (complete) popBackAndReturn(findNavController())
            else Toast.makeText(context, "post upload failed by error", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addPostBtn.setOnClickListener {
            val title = binding.addEditText.text.toString()
            val value = Post(
                getCurrentTime(),
                viewmodel.currentUserId,
                postContent = Content(contentName = title)
            )
            viewmodel.uploadPost(value)
        }
    }
}