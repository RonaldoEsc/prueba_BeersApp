package net.hrec.pruebatecnica.model

import com.google.gson.annotations.SerializedName

data class DetailBeerResponse(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("tagline")
    var tagline: String? = null,
    @SerializedName("first_brewed")
    var first_brewed: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("image_url")
    var imageUrl: String? = null,
    @SerializedName("abv")
    var abv: Double? = null,
    @SerializedName("ibu")
    var ibu: Double? = null,
    @SerializedName("target_fg")
    var target_fg: Double? = null,
    @SerializedName("target_og")
    var target_og: Double? = null,
    @SerializedName("ebc")
    var ebc: Double? = null,
    @SerializedName("srm")
    var srm: Double? = null,
    @SerializedName("ph")
    var ph: Double? = null,
    @SerializedName("attenuation_level")
    var attenuation_level: Double? = null,
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
    var value: Int? = null,
    @SerializedName("unit")
    var unit: String? = null
)

data class MeasurementDoubleUnit(
    @SerializedName("value")
    var value: Double? = null,
    @SerializedName("unit")
    var unit: String? = null
)

data class MethodBeer(
    @SerializedName("mash_temp")
    var mash_temp: List<MashMethod>? = null,
    @SerializedName("fermentation")
    var fermentation: TempMethod? = null,
    @SerializedName("twist")
    var twist: String? = null
)

data class TempMethod(
    @SerializedName("temp")
    var temp: MeasurementUnit? = null
)

data class MashMethod(
    @SerializedName("temp")
    var temp: MeasurementUnit? = null,
    @SerializedName("duration")
    var duration: Int? = null
)

data class IngredientsBeer(
    @SerializedName("malt")
    var malt: List<IngredientsBeerMalt>? = null,
    @SerializedName("hops")
    var hops: List<IngredientsBeerHops>? = null,
    @SerializedName("yeast")
    var yeast: String? = null
)

data class IngredientsBeerMalt(
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("amount")
    var amount: MeasurementDoubleUnit? = null
)

data class IngredientsBeerHops(
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("amount")
    var amount: MeasurementDoubleUnit? = null,
    @SerializedName("add")
    var add: String? = null,
    @SerializedName("attribute")
    var attribute: String? = null
)