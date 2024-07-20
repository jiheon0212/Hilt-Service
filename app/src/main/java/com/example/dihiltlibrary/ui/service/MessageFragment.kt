package com.example.dihiltlibrary.ui.service

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dihiltlibrary.GetTime.getCurrentTime
import com.example.dihiltlibrary.adapter.MessageAdapter
import com.example.dihiltlibrary.data.Message
import com.example.dihiltlibrary.databinding.FragmentMessageBinding
import com.example.dihiltlibrary.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MessageFragment : Fragment() {
    private lateinit var binding: FragmentMessageBinding
    private val viewModel: ChatViewModel by viewModels()
    private val args: MessageFragmentArgs by navArgs()
    private val messageAdapter = MessageAdapter(mutableListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(layoutInflater, container, false)
        binding.messageRecycler.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.updateMessageList(args.roomId)
        viewModel.messageList.observe(viewLifecycleOwner) { list ->
            messageAdapter.fetchMessage(list)
            binding.messageRecycler.scrollToPosition(list.size - 1)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputLayout.setEndIconOnClickListener {
            val message = binding.etMessage.text.toString()

            if (message.isEmpty()) {
                binding.inputLayout.error = "message is empty"
            } else {
                val timestamp = getCurrentTime()
                val sender = viewModel.currentUserId

                lifecycleScope.launch {
                    withContext(Dispatchers.Main) {
                        viewModel.sendMessage(Message(sender, message, timestamp), args.roomId)
                        binding.etMessage.setText("")
                        hideKeyboard(it)
                    }
                }
            }
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}