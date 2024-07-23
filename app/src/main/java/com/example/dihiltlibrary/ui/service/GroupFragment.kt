package com.example.dihiltlibrary.ui.service

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dihiltlibrary.GetTime.getCurrentTime
import com.example.dihiltlibrary.R
import com.example.dihiltlibrary.adapter.MessageAdapter
import com.example.dihiltlibrary.data.Message
import com.example.dihiltlibrary.databinding.FragmentGroupBinding
import com.example.dihiltlibrary.viewmodel.ChatViewModel
import com.example.dihiltlibrary.viewmodel.GroupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class GroupFragment : Fragment() {
    private lateinit var binding: FragmentGroupBinding
    private val viewModel: GroupViewModel by viewModels()
    private val chatViewModel: ChatViewModel by viewModels()
    private val messageAdapter = MessageAdapter(mutableListOf())
    private lateinit var guName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupBinding.inflate(layoutInflater, container, false)
        binding.groupChatRecycler.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(context)
        }
        binding.groupChatLayout.setEndIconOnClickListener {
            val message = binding.groupChatEditText.text.toString()

            if (message.isEmpty()) {
                binding.groupChatLayout.error = "message is empty"
            } else {
                val timestamp = getCurrentTime()
                val sender = chatViewModel.currentUserId

                lifecycleScope.launch {
                    withContext(Dispatchers.Main) {
                        chatViewModel.sendMessage(Message(sender, message, timestamp), guName)
                        binding.groupChatEditText.setText("")
                        hideKeyboard(it)
                    }
                }
            }
        }

        viewModel.addressData.observe(viewLifecycleOwner) { address ->
            binding.groupLocationTv.text = address?.address

            if (binding.groupLocationTv.text != "") {
                lifecycleScope.launch {
                    guName = splitAddress(address?.address)

                    if (guName != "") {
                        chatViewModel.updateMessageList(guName)
                        binding.groupChatLayout.visibility = View.VISIBLE
                    } else return@launch
                }
            } else binding.groupChatLayout.visibility = View.GONE

        }
        chatViewModel.messageList.observe(viewLifecycleOwner) { list ->
            messageAdapter.fetchMessage(list)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.groupLocationBtn.setOnClickListener {
            viewModel.getLastLocation()
        }
    }
    private fun splitAddress(value: String?): String {
        val split = value?.split(" ")
        val result = split?.firstOrNull { it.endsWith("구") }

        return if (result != "") result!! else ""
    }
    private fun hideKeyboard(view: View) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}