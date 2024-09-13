package com.samy.rick.presentation.main

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.samy.e_ramo.R
import com.samy.e_ramo.databinding.ActivityMainBinding
import com.samy.rick.utils.DataState
import com.samy.rick.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ProductViewModel by viewModels()


    @Inject
    lateinit var bestCouponsEgyptAdapter: BestCouponsEgyptAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        try {
            setup()
            data()
            observe()
        } catch (e: Exception) {
            Log.d("mos", "e: ${e.message}")
        }
    }

    private fun setup() {
//        binding.bestCouponsInEgyptRV.adapter = bestCouponsEgyptAdapter
    }


    private fun data() {
//        viewModel.fetchData()
    }

    private fun observe() {
        /*lifecycleScope.launchWhenStarted {
            viewModel.dataStateFlow.collect {
                Log.d("mos", "it: ${it} ")
                when (it) {

                    is DataState.Loading -> {
                        visProgress(true)
                    }

                    is DataState.Error -> {
                        Log.d("mos", "error: ${it.msg}")
                        visProgress(false)
                        binding.mainConstraintLayout.visibility = View.INVISIBLE
                        Utils.showNoInternetDialog(this@MainActivity,
                            onPositive = {
                                val intent = intent
                                finish()
                                startActivity(intent)
                            }
                        )
                    }

                    is DataState.Result<*> -> {
                        visProgress(false)
                        handleResult(it.response as DataModel)

                    }

                }

            }
        }*/
    }

    private fun visProgress(s: Boolean) {
        if (s) {
            binding.progressLayout.startShimmer()
            binding.progressLayout.visibility = View.VISIBLE
            binding.mainConstraintLayout.visibility = View.INVISIBLE
        } else {
            binding.progressLayout.stopShimmer()
            binding.progressLayout.visibility = View.GONE
            binding.mainConstraintLayout.visibility = View.VISIBLE
        }

    }
/*

    private fun handleResult(dataModel: DataModel) {
//        topStoresAdapter.submitList(data)
    }
*/




}