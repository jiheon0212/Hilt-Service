package com.example.dihiltlibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dihiltlibrary.data.AddressData
import com.example.dihiltlibrary.data.Location
import com.example.dihiltlibrary.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository
): ViewModel() {
    private val _locationData = MutableLiveData<Location?>()
    val locationData: LiveData<Location?> = _locationData

    private val _addressData = MutableLiveData<AddressData?>()
    val addressData: LiveData<AddressData?> = _addressData

    fun getLastLocation() {
        viewModelScope.launch {
            _locationData.value = groupRepository.getLastLocation()
            _locationData.value?.let { location ->
                groupRepository.getAddressFromLocation(location).collect { addressData ->
                    _addressData.value = addressData
                }
            }
        }
    }
}