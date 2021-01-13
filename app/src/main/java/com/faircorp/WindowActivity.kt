package com.faircorp

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.faircorp.service.RemoteRoomApiService
import com.faircorp.service.RemoteWindowApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WindowActivity : ProgressBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        hideAllAndShowProgressBar()

        val windowId = intent.getLongExtra(Constants.EXTRA_WINDOW_ID.toString(), 0)
        var roomId:Long? = null

        lifecycleScope.launch(context = Dispatchers.IO) {
            runCatching { RemoteWindowApiService().windowApiService.findById(windowId).execute() }
                .onSuccess {
                    withContext(context = Dispatchers.Main) {
                        val window = it.body()
                        if (window != null) {
                            findViewById<TextView>(R.id.windowNameTextView).text = window.name
                            findViewById<TextView>(R.id.windowStatusTextView).text = window.windowStatus.toString()
                            roomId = window.roomId
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
            runCatching { roomId?.let { RemoteRoomApiService().roomApiService.findById(it).execute() } }
                .onSuccess {
                    withContext(context = Dispatchers.Main) {
                        val room = it?.body()
                        if (room != null) {
                            findViewById<TextView>(R.id.windowRoomTextView).text = room.name
                            findViewById<TextView>(R.id.windowCurrentTempTextView).text = room.currentTemperature.toString()
                            findViewById<TextView>(R.id.windowTargetTempTextView).text = room.targetTemperature.toString()
                        }
                        showAllAndHideProgressBar()
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) {
                        Toast.makeText(
                                applicationContext,
                                "Error on windows loading $it",
                                Toast.LENGTH_LONG
                        ).show()
                        findViewById<TextView>(R.id.windowNameTextView).text = "FAILED"
                        findViewById<TextView>(R.id.windowRoomTextView).text = "FAILED"
                        findViewById<TextView>(R.id.windowCurrentTempTextView).text = "FAILED"
                        findViewById<TextView>(R.id.windowTargetTempTextView).text = "FAILED"
                        findViewById<TextView>(R.id.windowStatusTextView).text = "FAILED"
                    }
                }
        }

    }

    fun flipWindowStatus(view: View) {
        val windowId = intent.getLongExtra(Constants.EXTRA_WINDOW_ID.toString(), 0)
        val statusView = findViewById<TextView>(R.id.windowStatusTextView)

        statusView.text = "Updating..."

        lifecycleScope.launch(context = Dispatchers.IO) {
            runCatching { RemoteWindowApiService().windowApiService.flipById(windowId).execute() }
                    .onSuccess {
                        withContext(context = Dispatchers.Main) {
                            val window = it.body()
                            if (window != null) {
                                statusView.text = window.windowStatus.toString()
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
                        statusView.text = "FAILED"
                    }
        }
    }
}