package com.example.dihiltlibrary.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dihiltlibrary.data.CurrentStatus
import com.example.dihiltlibrary.databinding.CurrentStatusOfflineBinding
import com.example.dihiltlibrary.databinding.CurrentStatusOnlineBinding

class StatusAdapter(private var currentStatus: MutableList<CurrentStatus>, private val onItemClick: (String) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ON = 1
    private val VIEW_TYPE_OFF = 2

    inner class StatusOnlineViewHolder(val binding: CurrentStatusOnlineBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val id = currentStatus[adapterPosition].id
                    onItemClick(id!!)
                }
            }
        }
    }
    inner class StatusOfflineViewHolder(val binding: CurrentStatusOfflineBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val id = currentStatus[adapterPosition].id
                    onItemClick(id!!)
                }
            }
        }
    }

    // todo - user basic info 객체를 받아와서 id -> user nickname으로 교체해주기
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val statusData = currentStatus[position]
        val (id, _) = statusData

        if (holder.itemViewType == VIEW_TYPE_ON){
            (holder as StatusOnlineViewHolder).binding.apply {
                tvUserName.text = id
            }
        } else {
            (holder as StatusOfflineViewHolder).binding.apply {
                tvUserName.text = id
            }
        }
    }

    override fun getItemCount(): Int = currentStatus.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val onBinding = CurrentStatusOnlineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val offBinding = CurrentStatusOfflineBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return if (viewType == VIEW_TYPE_ON) {
            StatusOnlineViewHolder(onBinding)
        } else {
            StatusOfflineViewHolder(offBinding)
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if (currentStatus[position].status == "online") VIEW_TYPE_ON else VIEW_TYPE_OFF
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCurrentUserList(newList: MutableList<CurrentStatus>) {
        currentStatus = newList
        notifyDataSetChanged()
    }
}