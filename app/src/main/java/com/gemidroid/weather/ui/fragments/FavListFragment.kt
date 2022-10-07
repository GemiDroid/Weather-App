package com.gemidroid.weather.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gemidroid.weather.R
import com.gemidroid.weather.databinding.FragmentFavListBinding
import com.gemidroid.weather.ui.adapters.FavListAdapter
import com.gemidroid.weather.ui.viewmodels.WeatherViewModel
import com.gemidroid.data.model.Weather
import com.gemidroid.data.model.WeatherState
import com.gemidroid.utils.Constants.IS_FROM_FAV
import com.gemidroid.utils.Constants.LOCATION_NAME
import com.gemidroid.utils.gone
import com.gemidroid.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavListFragment : Fragment() {

    private var _binding: FragmentFavListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var weatherViewModel: WeatherViewModel

    private val favListAdapter by lazy {
        FavListAdapter(emptyList()) {
            findNavController().navigate(
                R.id.action_favListFragment_to_homeFragment, bundleOf(
                    IS_FROM_FAV to true, LOCATION_NAME to it.location?.region
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherViewModel.getWeatherFavItems()

        binding.recWeatherFav.adapter = favListAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            weatherViewModel.getWeatherItems.collect {
                when (it) {
                    is WeatherState.Success<*> -> {
                        val data = it.data as? List<Weather>
                        if (!data.isNullOrEmpty()) {
                            favListAdapter.appendList(data)
                            hideEmptyView()
                        } else {
                            displayEmptyView()
                        }
                    }
                    is WeatherState.Error -> {}
                    else -> {}
                }
            }
        }
    }

    private fun displayEmptyView() {
        binding.txtNoFav.visible()
    }

    private fun hideEmptyView() {
        binding.txtNoFav.gone()
    }

}