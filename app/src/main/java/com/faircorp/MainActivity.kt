package com.faircorp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /** Called when the user taps the button */
    fun openWindow(view: View) {
        // Extract view value
        val windowId: Long = findViewById<TextView>(R.id.windowIdTextNumberSigned).text.toString().toLong()
        // Callback to button click
        val intent = Intent(this, WindowActivity::class.java).apply {
            putExtra(Constants.EXTRA_WINDOW_ID, windowId)
        }
        startActivity(intent)
    }

    fun openRoom(view: View) {
        // Extract view value
        val roomId: Long = findViewById<TextView>(R.id.roomIdTextNumberSigned).text.toString().toLong()
        // Callback to button click
        val intent = Intent(this, RoomActivity::class.java).apply {
            putExtra(Constants.EXTRA_ROOM_ID, roomId)
        }
        startActivity(intent)
    }


}