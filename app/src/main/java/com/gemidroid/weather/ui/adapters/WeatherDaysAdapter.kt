package com.gemidroid.weather.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gemidroid.weather.databinding.WeatherItemBinding
import com.gemidroid.data.model.WeatherDay
import org.joda.time.LocalDate
import javax.inject.Inject

class WeatherDaysAdapter @Inject constructor(private val items: List<WeatherDay>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var _binding: WeatherItemBinding? = null
    private val binding get() = _binding!!
    private val dayList = mutableListOf("Today", "Tomorrow")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        _binding = WeatherItemBinding.inflate(LayoutInflater.from(parent.context))
        return object : RecyclerView.ViewHolder(binding.root) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        with(binding) {
            Glide.with(holder.itemView.context).load("https:${item.day?.condition?.icon}")
                .into(imgWeather)
            txtTemp.text = "${item.day?.minTempF}°/${item.day?.maxTempF}°F "
            txtDay.text = if (position >= 2)
                LocalDate.now().plusDays(position).toString("EEEE")
            else dayList[position]
        }
    }

    override fun getItemCount() = items.size
}