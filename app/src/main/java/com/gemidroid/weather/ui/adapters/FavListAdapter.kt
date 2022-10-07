package com.gemidroid.weather.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gemidroid.weather.databinding.WeatherFavoriteItemBinding
import com.gemidroid.data.model.Weather
import javax.inject.Inject

class FavListAdapter @Inject constructor(
    private var items: List<Weather>,
    private val onWeatherItemClick: (Weather) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var _binding: WeatherFavoriteItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        _binding =
            WeatherFavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return object : RecyclerView.ViewHolder(binding.root) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val weatherItem = items[position]
        with(binding) {
            txtLocationCountry.text = weatherItem.location?.country
            txtLocationCity.text = weatherItem.location?.region
            Glide.with(this.root.context)
                .load(String.format("%s%s", "https:", "${weatherItem.current?.condition?.icon}"))
                .into(imgWeather)
            holder.itemView.setOnClickListener {
                onWeatherItemClick.invoke(weatherItem)
            }
        }
    }

    fun appendList(list: List<Weather>) {
        items = list
        notifyDataSetChanged()

    }

    override fun getItemCount() = items.size
}