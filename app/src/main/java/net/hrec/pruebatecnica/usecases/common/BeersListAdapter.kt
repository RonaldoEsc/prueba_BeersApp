package net.hrec.pruebatecnica.usecases.common

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.hrec.pruebatecnica.R
import net.hrec.pruebatecnica.databinding.BeerViewHolderBinding
import net.hrec.pruebatecnica.model.BeersResponse

class BeersListAdapter(val beerId: (Int) -> Unit): RecyclerView.Adapter<BeersListAdapter.BeerViewHolder>() {
    private var listBeers = mutableListOf<BeersResponse>()
    private val favoriteSelectedList = mutableListOf<Int>()
    class BeerViewHolder(binding: BeerViewHolderBinding): RecyclerView.ViewHolder(binding.root) {
        private val tvBeerName = binding.tvBeerName
        private val tvBeerTagName = binding.tvBeerTagName
        private val imgBeer = binding.imgBeer
        val ibFavorite = binding.ibFavorite
        val clHolderEvent = binding.clHolderEvent

        fun bind(beer: BeersResponse) {
            tvBeerName.text = beer.name
            tvBeerTagName.text = beer.tagline
            Picasso.get().load(beer.imageUrl).placeholder(R.drawable.progress_animation).into(imgBeer)
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
        if (favoriteSelectedList.isEmpty()) {
            holder.ibFavorite.setImageResource(R.drawable.ic_favorite_border)
        } else {
            var existPositionInList = false
            favoriteSelectedList.forEach {
               if (it == position) {
                   existPositionInList = true
                   return@forEach
               }
            }
            if (existPositionInList) {
                holder.ibFavorite.setImageResource(R.drawable.ic_favorite)
            } else {
                holder.ibFavorite.setImageResource(R.drawable.ic_favorite_border)
            }
        }
        holder.ibFavorite.setOnClickListener {
            holder.ibFavorite.setImageResource(R.drawable.ic_favorite)
            if (favoriteSelectedList.isEmpty()) {
                favoriteSelectedList.add(position)
            } else {
                var isInList = false
                favoriteSelectedList.forEach {
                    if (it == position) {
                        isInList = true
                    }
                }
                if (isInList) {
                    favoriteSelectedList.remove(position)
                } else {
                    favoriteSelectedList.add(position)
                }
            }
            notifyDataSetChanged()
        }
        holder.clHolderEvent.setOnClickListener {
            beerId(beer.id!!)
        }
    }

    override fun getItemCount(): Int {
        return listBeers.size
    }
}