package com.faircorp.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.OnRoomSelectedListener
import com.faircorp.R

class RoomAdapter(val listener: OnRoomSelectedListener) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {
    inner class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.roomListNameTextView)
        val curTemp: TextView = view.findViewById(R.id.roomListCurrentTempTextView)
        val tarTemp: TextView = view.findViewById(R.id.roomListTargetTempTextView)
        val floor: TextView = view.findViewById(R.id.roomListFloorTextView)
    }

    private val items = mutableListOf<RoomDto>()

    fun update(rooms: List<RoomDto>) {
        items.clear()
        items.addAll(rooms)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_rooms_item, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = items[position]
        holder.apply {
            name.text = room.name
            curTemp.text = room.currentTemperature?.toString()
            tarTemp.text = room.targetTemperature?.toString()
            floor.text = room.floor?.toString()
            itemView.setOnClickListener {
                listener.onRoomSelectedListener(room.id)
            }
        }
    }

    override fun onViewRecycled(holder: RoomViewHolder) {
        super.onViewRecycled(holder)
        holder.apply {
            itemView.setOnClickListener(null)
        }
    }
}