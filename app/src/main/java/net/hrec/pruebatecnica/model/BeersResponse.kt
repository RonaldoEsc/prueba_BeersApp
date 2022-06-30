package net.hrec.pruebatecnica.model

import com.google.gson.annotations.SerializedName

data class BeersResponse(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("tagline")
    var tagline: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("image_url")
    var imageUrl: String? = null
)