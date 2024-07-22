package com.example.dihiltlibrary.data

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

data class Board(
    val id: String? = null,
    val category: String? = null,
    val title: String = "",
    val content: BoardContent? = null,
    val timestamp: String = "",
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString() ?: "",
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) parcel.readParcelable(BoardContent::class.java.classLoader, BoardContent::class.java)
        else parcel.readParcelable(BoardContent::class.java.classLoader),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(category)
        parcel.writeString(title)
        parcel.writeParcelable(content, flags)
        parcel.writeString(timestamp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Board> {
        override fun createFromParcel(parcel: Parcel): Board {
            return Board(parcel)
        }

        override fun newArray(size: Int): Array<Board?> {
            return arrayOfNulls(size)
        }
    }
}

data class BoardContent(
    val text: String = "",
    val image: String = "",
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BoardContent> {
        override fun createFromParcel(parcel: Parcel): BoardContent {
            return BoardContent(parcel)
        }

        override fun newArray(size: Int): Array<BoardContent?> {
            return arrayOfNulls(size)
        }
    }
}
