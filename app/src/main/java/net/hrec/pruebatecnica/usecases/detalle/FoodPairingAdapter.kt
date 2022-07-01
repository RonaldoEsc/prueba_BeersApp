package net.hrec.pruebatecnica.usecases.detalle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.hrec.pruebatecnica.databinding.VhFoodPairingBinding

class FoodPairingAdapter(private val list: List<String>?) : RecyclerView.Adapter<FoodPairingAdapter.FoodPairingViewHolder>() {
    private lateinit var binding: VhFoodPairingBinding
    inner class FoodPairingViewHolder(binding: VhFoodPairingBinding): RecyclerView.ViewHolder(binding.root) {
        val tvViewHolderString = binding.tvViewHolderString
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodPairingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = VhFoodPairingBinding.inflate(layoutInflater, parent, false)
        return FoodPairingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodPairingViewHolder, position: Int) {
        val text = list?.get(position)
        holder.tvViewHolderString.text = text
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}