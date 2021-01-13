package com.faircorp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.model.RoomAdapter
import com.faircorp.model.SimpleWindowAdapter
import com.faircorp.model.WindowAdapter
import com.faircorp.model.WindowDto
import com.faircorp.service.RemoteRoomApiService
import com.faircorp.service.RemoteWindowApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomActivity : ProgressBarActivity(), OnWindowSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        hideAllAndShowProgressBar()

        val recyclerView = findViewById<RecyclerView>(R.id.roomWindowListRecycleView)
        val adapter = SimpleWindowAdapter(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        val roomId = intent.getLongExtra(Constants.EXTRA_ROOM_ID.toString(), 0)
        var windowIds : List<Long>? = null
        var windows : MutableList<WindowDto>? = mutableListOf()
        lifecycleScope.launch(context = Dispatchers.IO) {

            runCatching { roomId?.let { RemoteRoomApiService().roomApiService.findById(it).execute() } }
                .onSuccess {
                    withContext(context = Dispatchers.Main) {
                        val room = it?.body()
                        if (room != null) {
                            findViewById<TextView>(R.id.roomIdTextView).text = room.id.toString()
                            findViewById<TextView>(R.id.roomNameTextView).text = room.name
                            findViewById<TextView>(R.id.roomCurrentTempTextView).text = room.currentTemperature.toString()
                            findViewById<TextView>(R.id.roomTargetTempTextView).text = room.targetTemperature.toString()
                            findViewById<TextView>(R.id.roomFloorTextView).text = room.floor.toString()
                            windowIds = room.windowIds
                        }
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "Error on windows loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                        findViewById<TextView>(R.id.roomIdTextView).text = "FAILED"
                        findViewById<TextView>(R.id.roomNameTextView).text = "FAILED"
                        findViewById<TextView>(R.id.roomCurrentTempTextView).text = "FAILED"
                        findViewById<TextView>(R.id.roomTargetTempTextView).text = "FAILED"
                        findViewById<TextView>(R.id.roomFloorTextView).text = "FAILED"
                    }
                }
            for (i in windowIds!!.size downTo 1) {
                runCatching { windowIds!![i-1]?.let { RemoteWindowApiService().windowApiService.findById(it).execute() } }
                    .onSuccess {
                        withContext(context = Dispatchers.Main) {
                            val window = it?.body()
                            if (window != null && windows != null) {
                                windows.add(window)
                            }
                            if (i == 1) {
                                if (windows != null) {
                                    adapter.update(windows)
                                }
                                showAllAndHideProgressBar()
                            }
                        }
                    }
                    .onFailure {
                        withContext(context = Dispatchers.Main) {
                            Toast.makeText(
                                applicationContext,
                                "Error on windows loading $it",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }


    }
    override fun onWindowSelectedListener(id: Long) {
        val intent = Intent(this, WindowActivity::class.java).putExtra(
            Constants.EXTRA_WINDOW_ID,
            id
        )
        startActivity(intent)
    }
}