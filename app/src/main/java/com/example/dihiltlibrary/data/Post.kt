package com.example.dihiltlibrary.data

data class Post(
    val postTimestamp: String = "",
    val postWriter: String? = "",
    val postContent: Content? = null,
    val stars: List<Stars> = emptyList(),
    val reply: List<Reply> = emptyList(),
){
    val starsCount: Int get() = stars.size
    val replyCount: Int get() = reply.size
}
data class Content(
    val contentName: String = "",
    val contentImage: String = "",
    val contentText: String = ""
)
data class Stars(
    val name: String? = null,
    val isTrigger: Boolean? = null,
)
data class Reply(
    val replyId: String? = null,
    val name: String? = null,
    val value: String? = null,
)
