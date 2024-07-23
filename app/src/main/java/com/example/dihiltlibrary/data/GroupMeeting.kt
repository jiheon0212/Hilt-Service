package com.example.dihiltlibrary.data

data class GroupMeeting(
    val position: Location? = null, // 좌표
    val localName: AddressData? = null, // 좌표에 해당하는 지역명
    val time: String? = null, // 약속 시간
    val participant: List<UserBasicInfo>? = null // 참석자 information
)
