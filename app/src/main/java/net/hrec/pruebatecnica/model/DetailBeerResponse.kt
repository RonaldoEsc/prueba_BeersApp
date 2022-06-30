package net.hrec.pruebatecnica.model

import com.google.gson.annotations.SerializedName

data class DetailBeerResponse(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("tagline")
    var tagline: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("image_url")
    var imageUrl: String? = null,
    @SerializedName("abv")
    var abv: String? = null,
    @SerializedName("ibu")
    var ibu: String? = null,
    @SerializedName("target_fg")
    var target_fg: String? = null,
    @SerializedName("target_og")
    var target_og: String? = null,
    @SerializedName("ebc")
    var ebc: String? = null,
    @SerializedName("srm")
    var srm: String? = null,
    @SerializedName("ph")
    var ph: String? = null,
    @SerializedName("attenuation_level")
    var attenuation_level: String? = null,
    @SerializedName("volume")
    var volume: MeasurementUnit? = null,
    @SerializedName("boil_volume")
    var boil_volume: MeasurementUnit? = null,
    @SerializedName("method")
    var method: MethodBeer? = null,
    @SerializedName("ingredients")
    var ingredients: IngredientsBeer? = null,
    @SerializedName("food_pairing")
    var food_pairing: List<String>? = null,
    @SerializedName("brewers_tips")
    var brewers_tips: String? = null,
    @SerializedName("contributed_by")
    var contributed_by: String? = null
)

data class MeasurementUnit(
    @SerializedName("value")
    var value: String? = null,
    @SerializedName("unit")
    var unit: String? = null
)

data class MethodBeer(
    @SerializedName("mash_temp")
    var mash_temp: String? = null,
    @SerializedName("fermentation")
    var fermentation: String? = null,
    @SerializedName("twist")
    var twist: String? = null
)

data class IngredientsBeer(
    @SerializedName("malt")
    var malt: IngredientsBeerMalt? = null,
    @SerializedName("hops")
    var hops: IngredientsBeerHops? = null,
    @SerializedName("yeast")
    var yeast: String? = null
)

data class IngredientsBeerMalt(
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("amount")
    var amount: MeasurementUnit? = null
)

data class IngredientsBeerHops(
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("amount")
    var amount: MeasurementUnit? = null,
    @SerializedName("add")
    var add: String? = null,
    @SerializedName("attribute")
    var attribute: String? = null
)