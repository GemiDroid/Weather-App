package com.gemidroid.weather.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.gemidroid.weather.ui.AlarmReceiver
import com.gemidroid.weather.R
import com.gemidroid.weather.databinding.HomeActivityBinding
import com.gemidroid.utils.WeatherAlarm.notifyUserWithWeather
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private var _binding: HomeActivityBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment_content_main)
        notifyUserWithWeather(Intent(this, AlarmReceiver::class.java))
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (navController.navigateUp())
            true
        else super.onSupportNavigateUp()
    }
}