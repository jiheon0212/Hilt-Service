package com.example.dihiltlibrary.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dihiltlibrary.data.Board
import com.example.dihiltlibrary.databinding.BoardTitleHolderBinding

class BoardAdapter(private var boardTitleList: MutableList<Board>, private val onItemClick: (Board) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class BoardViewHolder(val binding: BoardTitleHolderBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val board = boardTitleList[adapterPosition]
                    onItemClick(board)
                }
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun fetchPostTitleList(newList: MutableList<Board>) {
        boardTitleList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = BoardTitleHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BoardViewHolder(binding)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val boardTitle = boardTitleList[position].title
        val boardWriter = boardTitleList[position].id
        (holder as BoardViewHolder).binding.apply {
            boardTitleTv.text = boardTitle
            boardWriterTv.text = boardWriter
        }
    }
    override fun getItemCount(): Int = boardTitleList.size
}