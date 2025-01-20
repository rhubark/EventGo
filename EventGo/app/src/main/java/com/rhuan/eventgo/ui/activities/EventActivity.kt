package com.rhuan.eventgo.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.rhuan.eventgo.R

class EventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_event)


        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.setGraph(R.navigation.base_navigation, intent.extras)

    }
}