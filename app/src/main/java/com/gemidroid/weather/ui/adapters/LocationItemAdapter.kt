package com.gemidroid.weather.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gemidroid.weather.databinding.LocationItemBinding
import com.gemidroid.data.model.Location
import javax.inject.Inject

class LocationItemAdapter @Inject constructor(
    private val items: List<Location>,
    private val onItemClick: (Location) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var _binding: LocationItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        _binding = LocationItemBinding.inflate(LayoutInflater.from(parent.context))
        return object : RecyclerView.ViewHolder(binding.root) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            onItemClick.invoke(item)
        }
        with(binding) {
            txtLocationName.text = item.region
            txtLocationCountry.text = " - ".plus(item.country)
        }
    }

    override fun getItemCount() = items.size
}