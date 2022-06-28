package net.hrec.pruebatecnica.usecases.common

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.hrec.pruebatecnica.databinding.BeerViewHolderBinding
import net.hrec.pruebatecnica.model.BeersResponse

class BeersListAdapter(val beer: (Int) -> Unit): RecyclerView.Adapter<BeersListAdapter.BeerViewHolder>() {
    private var listBeers = mutableListOf<BeersResponse>()
    class BeerViewHolder(private val binding: BeerViewHolderBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(beer: BeersResponse) {

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: MutableList<BeersResponse>) {
        listBeers = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = BeerViewHolderBinding.inflate(layoutInflater, parent, false)
        return BeerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val beer = listBeers[position]
        holder.bind(beer)
    }

    override fun getItemCount(): Int {
        return listBeers.size
    }
}