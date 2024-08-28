package com.samy.e_ramo.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.samy.e_ramo.R
import com.samy.e_ramo.databinding.ActivityMainBinding
import com.samy.e_ramo.pojo.model.DataModel
import com.samy.e_ramo.presentation.adapter.BestCouponsEgyptAdapter
import com.samy.e_ramo.presentation.adapter.BestDealAdapter
import com.samy.e_ramo.presentation.adapter.FeatureDealAdapter
import com.samy.e_ramo.presentation.adapter.RecentCategoriesAdapter
import com.samy.e_ramo.presentation.adapter.TopStoresAdapter
import com.samy.e_ramo.utils.NetworkState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ProductViewModel by viewModels()

    @Inject
    lateinit var topStoresAdapter: TopStoresAdapter

    @Inject
    lateinit var bestCouponsEgyptAdapter: BestCouponsEgyptAdapter

    @Inject
    lateinit var bestCouponsForYouAdapter: BestCouponsEgyptAdapter

    @Inject
    lateinit var bestDealAdapter: BestDealAdapter

    @Inject
    lateinit var newYearDealAdapter: BestCouponsEgyptAdapter

    @Inject
    lateinit var featureDealAdapter: FeatureDealAdapter

    @Inject
    lateinit var recentCategoriesAdapter: RecentCategoriesAdapter

    @Inject
    lateinit var motherDayAdapter: BestCouponsEgyptAdapter

    @Inject
    lateinit var todayDealAdapter: BestDealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try {
            setup()
            data()
            observe()
        } catch (e: Exception) {
            Log.d("mos", "e: ${e.message}")
        }
    }

    private fun setup() {
        statuesBar()
        binding.topStoresRV.adapter = topStoresAdapter
        binding.bestCouponsInEgyptRV.adapter = bestCouponsEgyptAdapter
        binding.bestCouponsForYouRV.adapter = bestCouponsForYouAdapter
        binding.bestDealsRV.adapter = bestDealAdapter
        binding.newYearOffersRV.adapter = newYearDealAdapter
        binding.featuredDealsRV.adapter = featureDealAdapter
        binding.recentCategoriesRV.adapter = recentCategoriesAdapter
        binding.motherOffersRV.adapter = motherDayAdapter
        binding.ToDayDealsRV.adapter = todayDealAdapter
    }

    private fun statuesBar() {
        val window = window
        val colorSchema = ContextCompat.getColor(this, R.color.white)

        window.statusBarColor = colorSchema
        window.navigationBarColor = colorSchema

        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars =
            !isDarkTheme()

    }

    private fun isDarkTheme(): Boolean {
        return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    private fun data() {
        viewModel.fetchData()
    }

    private fun observe() {
        lifecycleScope.launchWhenStarted {
            viewModel.dataStateFlow.collect {
                Log.d("mos", "it: ${it} ")
                when (it) {
                    is NetworkState.Idle -> {
                        return@collect
                    }

                    is NetworkState.Loading -> {
                        visProgress(true)
                    }

                    is NetworkState.Error -> {
                        visProgress(false)
                    }

                    is NetworkState.Result<*> -> {
                        visProgress(false)
                        handleResult(it.response as DataModel)

                    }

                }

            }
        }
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

    private fun handleResult(dataModel: DataModel) {
        dataModel.data.forEach {
            Log.d("mos", "motherDayRV it.name:${it.name}")
            if (it.name == "Top stores")
                topStoresRV(it.data)
            else if (it.name == "Best coupons") {
                bestCouponsEgyptRV(it.data)
                todayDealsRV(it.data)
            } else if (it.name == "Best coupons for you")
                bestCouponsForYouRV(it.data)
            else if (it.name == "Best deals")
                bestDealRV(it.data)
            else if (it.name == "Featured deals") {
                newYearDealRV(it.data)
                featureDealRV(it.data)
                motherDayRV(it.data)
            } else if (it.name == "Recent categories")
                recentCategoriesRV(it.data)


        }
    }

    private fun topStoresRV(data: List<DataModel.DataX>) {
        topStoresAdapter.submitList(data.asReversed())
    }

    private fun bestCouponsEgyptRV(data: List<DataModel.DataX>) {
        bestCouponsEgyptAdapter.submitList(data.asReversed())
    }

    private fun bestCouponsForYouRV(data: List<DataModel.DataX>) {
        bestCouponsForYouAdapter.submitList(data.asReversed())
    }

    private fun bestDealRV(data: List<DataModel.DataX>) {
        bestDealAdapter.submitList(data.asReversed())
    }

    private fun newYearDealRV(data: List<DataModel.DataX>) {
        newYearDealAdapter.submitList(data.asReversed())
    }

    private fun featureDealRV(data: List<DataModel.DataX>) {
        featureDealAdapter.submitList(data)
    }

    private fun recentCategoriesRV(data: List<DataModel.DataX>) {
        recentCategoriesAdapter.submitList(data.asReversed())
    }

    private fun motherDayRV(data: List<DataModel.DataX>) {
        motherDayAdapter.submitList(data)
    }

    private fun todayDealsRV(data: List<DataModel.DataX>) {
        todayDealAdapter.submitList(data)
    }

}