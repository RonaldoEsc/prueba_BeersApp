package net.hrec.pruebatecnica.model

import com.google.gson.annotations.SerializedName

data class BeerDataFavorite(
    var id: Int? = null,
    var name: String? = null,
    var tagline: String? = null,
    var imageUrl: String? = null,
    var rate: Int? = null
)
