package com.samy.rick.presentation.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.samy.e_ramo.R
import com.samy.e_ramo.databinding.ActivityMainBinding
import com.samy.rick.presentation.authentication.login.LoginActivity
import com.samy.rick.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()


    @Inject
    lateinit var adapter: CharacterAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbarSetup()
        setupRecyclerView()
        observeViewModel()
        swipeRefreshOnClick()

    }

    private fun swipeRefreshOnClick() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            // Refresh data when -to-refresh is triggered
            viewModel.fetchCharacters()
        }
    }


    private fun toolbarSetup() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary) // Replace `R.color.colorPrimary` with your toolbar color
        val toolbar: androidx.appcompat.widget.Toolbar = binding.toolbar
        setSupportActionBar(toolbar)


        toolbar.inflateMenu(R.menu.menu)

        // Set up the action for the logout button
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_logout -> {
                    // Handle logout action here
                    performLogout()
                    true
                }

                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    fun performLogout() {
        startActivity(
            Intent(this@MainActivity,LoginActivity::class.java)
        )
        finish()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)  // 2 items per row
        binding.recyclerView.adapter = adapter

    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { dataState ->
                Log.d("mos samy", "state: $dataState")
                when (dataState) {
                    is DataState.Idle -> {
                        visProgress(false)
                    }

                    is DataState.Loading -> {
                        visProgress(true)
                    }

                    is DataState.Result -> {
                        visProgress(false)
                        Log.d("mos samy", "state: ${dataState.data}")
                        adapter.submitList(dataState.data.results)
                    }

                    is DataState.Error -> {
                        visProgress(false)
                    }
                }
            }
        }
    }

    private fun visProgress(s: Boolean) {
        if (s) {
            binding.progressLayout.startShimmer()
            binding.progressLayout.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.INVISIBLE
        } else {
            binding.progressLayout.stopShimmer()
            binding.progressLayout.visibility = View.GONE
            binding.swipeRefreshLayout.isRefreshing = false
            binding.recyclerView.visibility = View.VISIBLE
        }

    }

}