package net.hrec.pruebatecnica.usecases.detalle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.hrec.pruebatecnica.databinding.VhFoodPairingBinding
import net.hrec.pruebatecnica.model.IngredientsBeer

class IngredientsAdapter(private val ingredients: IngredientsBeer?, private val isMalt: Boolean) : RecyclerView.Adapter<IngredientsAdapter.IngredientsHolder>() {
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
        var name = ""
        var value = ""
        var unit = ""
        if (isMalt) {
            val list = ingredients?.malt
            list?.let {
                name = it[position].name ?: ""
                it[position].amount?.let { amount ->
                    value = getUnit(amount.unit)
                    unit = amount.value.toString()
                }
            }
        } else {
            val list = ingredients?.hops
            list?.let {
                name = it[position].name ?: ""
                it[position].amount?.let { amount ->
                    value = getUnit(amount.unit)
                    unit = amount.value.toString()
                }
            }
        }
        holder.tvViewHolderString.text = "$name: $unit $value"
    }

    private fun getUnit(unit: String?) : String {
        return when(unit) {
            "kilograms" -> {
                "kg."
            }
            "grams" -> {
                "g."
            }
            else -> {
                ""
            }
        }
    }

    override fun getItemCount(): Int {
        return if (isMalt) {
            ingredients?.malt?.size ?: 0
        } else {
            ingredients?.hops?.size ?: 0
        }
    }
}