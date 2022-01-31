package com.practice.kmmapp.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.practice.kmmapp.Greeting
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.kmmapp.DatabaseDriverFactory
import com.practice.kmmapp.SpaceXSDK
import com.practice.kmmapp.android.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val launchesRvAdapter = LaunchesRvAdapter(listOf())
    private val sdk = SpaceXSDK(DatabaseDriverFactory(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.launchesListRv.adapter = launchesRvAdapter
        binding.launchesListRv.layoutManager = LinearLayoutManager(this)

        binding.swipeContainer.setOnRefreshListener {
            binding.swipeContainer.isRefreshing = false
            displayLaunches(true)
        }

        displayLaunches(false)
    }

    private fun displayLaunches(needReload: Boolean) {
        binding.progressBar.isVisible = true
        lifecycleScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                sdk.getLaunches(needReload)
            }.onSuccess {
                withContext(Dispatchers.Main){
                    launchesRvAdapter.launches = it
                    launchesRvAdapter.notifyDataSetChanged()
                }
            }.onFailure {
                withContext(Dispatchers.Main){
                    Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
            withContext(Dispatchers.Main){
                binding.progressBar.isVisible = false
            }
        }
    }

}
