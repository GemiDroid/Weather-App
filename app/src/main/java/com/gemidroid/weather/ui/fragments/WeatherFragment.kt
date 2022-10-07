package com.gemidroid.weather.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.gemidroid.weather.R
import com.gemidroid.weather.databinding.FragmentWeatherBinding
import com.gemidroid.weather.ui.adapters.LocationItemAdapter
import com.gemidroid.weather.ui.adapters.WeatherDaysAdapter
import com.gemidroid.weather.ui.viewmodels.WeatherViewModel
import com.gemidroid.data.datasource.sharedpref.ISharedPref
import com.gemidroid.data.model.Location
import com.gemidroid.data.model.Weather
import com.gemidroid.data.model.WeatherState
import com.gemidroid.utils.*
import com.gemidroid.utils.Constants.IS_FROM_FAV
import com.gemidroid.utils.Constants.LOCATION_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    @Inject
    lateinit var weatherViewModel: WeatherViewModel

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private var weather: Weather? = null
    private var isComingFromFav = false
    private var query: String = Locale.getDefault().displayCountry

    @Inject
    lateinit var shared: ISharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.weather_menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_favourites -> {
                query = Locale.getDefault().displayCountry
                findNavController().navigate(R.id.favListFragment)
            }
            R.id.item_temp_change -> {
                shared.isTempCeliusis = !shared.isTempCeliusis
                weatherViewModel.getWeatherItems(query)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isComingFromFav = arguments?.getBoolean(IS_FROM_FAV) == true
        if (isComingFromFav) {
            query = arguments?.getString(LOCATION_NAME) ?: ""
            binding.imgFav.apply {
                setImageResource(R.drawable.ic_favorite)
            }
            weatherViewModel.getWeatherFavItem(query)
        } else {
            toggleSearchView(binding)
            weatherViewModel.getWeatherItems(query)
        }

        binding.imgSearch.setOnClickListener {
            binding.showSearchBar(it)
            toggleSearchView(binding)
        }

        binding.imgFav.apply {
            setOnClickListener {
                weather?.let {
                    if (it.isFav == true) {
                        this.setImageResource(R.drawable.ic_un_favorite)
                        weatherViewModel.removeWeatherFromFav(it)
                    } else {
                        this.setImageResource(R.drawable.ic_favorite)
                        weatherViewModel.addWeatherToFav(it)
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {

                weatherViewModel.onSaveWeatherItem.collect {
                    when (it) {
                        is WeatherState.Success<*> -> {}
                        is WeatherState.Error -> {
                            Log.e("TAG", "onViewCreated: ${it.error.localizedMessage}")
                        }
                        else -> {}
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {

                weatherViewModel.onRemoveWeatherItem.collect {
                    when (it) {
                        is WeatherState.Success<*> -> {}
                        is WeatherState.Error -> {
                            Log.e("TAG", "onViewCreated: ${it.error.localizedMessage}")
                        }
                        else -> {}
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                weatherViewModel.getWeatherItems.collect {
                    when (it) {
                        is WeatherState.Success<*> -> {
                            val response = it.data as Weather
                            inflateUI(response, binding)
                        }
                        is WeatherState.Error -> {
                            isComingFromFav = false
                            weatherViewModel.getWeatherItems(query)
                        }
                        is WeatherState.Loading -> {
                            showLoading(it.isLoading)
                        }
                        else -> {
                        }
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                weatherViewModel.getWeatherLocations.collect {
                    when (it) {
                        is WeatherState.Success<*> -> {
                            val locationItems = it.data as? List<Location>
                            if (!locationItems.isNullOrEmpty())
                                binding.weatherSearchView.recLocations.apply {
                                    visible()
                                    adapter = LocationItemAdapter(locationItems) { location ->
                                        weatherViewModel.getWeatherItems(location.region)
                                        gone()
                                        binding.hideSearchBar(this)
                                        query = location.region
                                        isComingFromFav = false
                                    }
                                }
                        }
                        is WeatherState.Error -> {}
                        else -> {}
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.progress.visible()
        else binding.progress.gone()
    }

    private fun toggleSearchView(binding: FragmentWeatherBinding) {
        with(binding) {
            weatherSearchView.imgBack.setOnClickListener {
                hideSearchBar(it)
            }

            imgSearch.setOnClickListener {
                weatherSearchView.root.visible()
                grpSearchGone.invisible()
            }

            weatherSearchView.apply {
                imgClose.setOnClickListener {
                    edtSearch.text.clear()
                }
                edtSearch.addOnTextChange {
                    if (it.isEmpty()) {
                        this.imgClose.gone()
                    } else {
                        this.imgClose.visible()
                        query = it
                        weatherViewModel.getWeatherLocations(it)
                    }
                }
            }
        }
    }

    private fun FragmentWeatherBinding.hideSearchBar(it: View) {
        weatherSearchView.root.gone()
        grpSearchGone.visible()
        it.hideKeyboard()
        weatherSearchView.edtSearch.text.clear()
    }

    private fun FragmentWeatherBinding.showSearchBar(it: View) {
        weatherSearchView.root.visible()
        grpSearchGone.gone()
        it.hideKeyboard()
        weatherSearchView.edtSearch.text.clear()
    }

    private fun inflateUI(response: Weather, binding: FragmentWeatherBinding) {
        with(binding) {
            weather = response
            if (isComingFromFav) imgFav.setImageResource(R.drawable.ic_favorite)
            else imgFav.setImageResource(R.drawable.ic_un_favorite)
            response.location?.localtime?.split(" ")?.let {
                txtDate.text = formatToLocalDate(it[0])
                txtTime.text = formatToLocalTime(it[1])
            }
            txtLocationName.text = response.location?.region
            Glide.with(this@WeatherFragment)
                .load(String.format("%s%s", "https:", "${response.current?.condition?.icon}"))
                .into(imgWeather)
            txtTemp.text = if (!shared.isTempCeliusis) response.current?.tempF.toString()
                .plus("°F") else response.current?.tempC.toString().plus("°C")
            txtWeatherState.text = response.current?.condition?.text
            txtWeatherHumidity.text = String.format(
                "%s %s",
                response.current?.humidity.toString(),
                getString(R.string.humidity_unit)
            )
            txtWeatherWind.text = String.format(
                "%s %s", response.current?.wind.toString(), getString(R.string.wind_unit)
            )
            response.forecast?.forecastDay?.let {
                recWeatherDays.adapter = WeatherDaysAdapter(it)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}


