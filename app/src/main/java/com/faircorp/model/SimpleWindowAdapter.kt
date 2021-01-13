package com.faircorp.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.OnWindowSelectedListener
import com.faircorp.R

class SimpleWindowAdapter(val listener: OnWindowSelectedListener) : RecyclerView.Adapter<SimpleWindowAdapter.SimpleWindowViewHolder>() {
    inner class SimpleWindowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.roomWindowNameTextView)
        val status: TextView = view.findViewById(R.id.roomWindowStatusTextView)
    }

    private val items = mutableListOf<WindowDto>()

    fun update(windows: List<WindowDto>) {
        items.clear()
        items.addAll(windows)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleWindowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_room_window_item, parent, false)
        return SimpleWindowViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleWindowViewHolder, position: Int) {
        val window = items[position]
        holder.apply {
            name.text = window.name
            status.text = window.windowStatus?.toString()
            itemView.setOnClickListener {
                listener.onWindowSelectedListener(window.id)
            }
        }
    }

    override fun onViewRecycled(holder: SimpleWindowViewHolder) {
        super.onViewRecycled(holder)
        holder.apply {
            itemView.setOnClickListener(null)
        }
    }

}