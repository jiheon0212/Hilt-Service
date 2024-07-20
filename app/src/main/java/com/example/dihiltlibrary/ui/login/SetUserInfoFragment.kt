package com.example.dihiltlibrary.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.dihiltlibrary.data.UserBasicInfo
import com.example.dihiltlibrary.databinding.FragmentSetUserInfoBinding
import com.example.dihiltlibrary.navigation.NavigateSet
import com.example.dihiltlibrary.viewmodel.FirebaseLoginViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SetUserInfoFragment : Fragment() {
    private lateinit var binding: FragmentSetUserInfoBinding
    private val viewModel: FirebaseLoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetUserInfoBinding.inflate(layoutInflater, container, false)
        viewModel.userUploadStatus.observe(viewLifecycleOwner) { status ->
            if (status) NavigateSet.navigateToService(activity) else return@observe
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setUserBtn.setOnClickListener {
            val nickname = binding.setUserName.text.toString()
            val userBasicInfo = UserBasicInfo(
                name = nickname,
                age = 1,
                sex = "",
                image = ""
            )
            viewModel.uploadUserBasicInfo(userBasicInfo)
        }
    }
}