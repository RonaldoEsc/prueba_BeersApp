package net.hrec.pruebatecnica.usecases.detalle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.hrec.pruebatecnica.databinding.VhFoodPairingBinding
import net.hrec.pruebatecnica.model.IngredientsBeer

class IngredientsAdapter(private val ingredients: IngredientsBeer?) : RecyclerView.Adapter<IngredientsAdapter.IngredientsHolder>() {
    private lateinit var binding: VhFoodPairingBinding
    inner class IngredientsHolder(binding: VhFoodPairingBinding): RecyclerView.ViewHolder(binding.root) {
        val tvViewHolderString = binding.tvViewHolderString
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = VhFoodPairingBinding.inflate(layoutInflater, parent, false)
        return IngredientsHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientsHolder, position: Int) {
        //val list = ingredients?.hops
        //holder.tvViewHolderString.text = ("${list?.get(position)?.amount?.unit}" ?: ""
    }

    override fun getItemCount(): Int {
        return ingredients?.hops?.size ?: 0
    }
}