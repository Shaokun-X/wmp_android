package com.faircorp.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.OnWindowSelectedListener
import com.faircorp.R

class WindowAdapter(val listener: OnWindowSelectedListener) : RecyclerView.Adapter<WindowAdapter.WindowViewHolder>() {

    inner class WindowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.windowListNameTextView)
        val room: TextView = view.findViewById(R.id.windowListRoomTextView)
        val status: TextView = view.findViewById(R.id.windowListStatusTextView)
    }

    private val items = mutableListOf<WindowDto>()

    fun update(windows: List<WindowDto>) {
        items.clear()
        items.addAll(windows)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WindowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_windows_item, parent, false)
        return WindowViewHolder(view)
    }

    override fun onBindViewHolder(holder: WindowViewHolder, position: Int) {
        val window = items[position]
        holder.apply {
            name.text = window.name
            status.text = window.windowStatus?.toString()
            room.text = window.roomId.toString()
            itemView.setOnClickListener {
                listener.onWindowSelectedListener(window.id)
            }
        }
    }

    override fun onViewRecycled(holder: WindowViewHolder) {
        super.onViewRecycled(holder)
        holder.apply {
            itemView.setOnClickListener(null)
        }
    }

}