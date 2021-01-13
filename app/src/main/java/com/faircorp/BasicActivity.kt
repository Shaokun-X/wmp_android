package com.faircorp

import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

open class BasicActivity : AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menuWindowList -> startActivity(
                Intent(this, WindowListActivity::class.java)
            )
            R.id.menuRoomList -> startActivity(
                Intent(this, RoomListActivity::class.java)
            )
            R.id.menuWebsite -> startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mines-stetienne.fr"))
            )
            R.id.menuEmail -> startActivity(
                Intent(Intent.ACTION_SENDTO, Uri.parse("mailto://shaokun.xie@etu.emse.fr"))
            )
        }
        return super.onContextItemSelected(item)
    }
}