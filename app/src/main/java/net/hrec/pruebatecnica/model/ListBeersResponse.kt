package net.hrec.pruebatecnica.model

import com.google.gson.annotations.SerializedName

data class ListBeersResponse(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: Int? = null,
    @SerializedName("tagline")
    var tagline: Int? = null
)
/*
{"id":1,"name":"Buzz","tagline":"A Real Bitter Experience.","first_brewed":"09/2007",
    "description":"A light, crisp and bitter IPA brewed with English and American hops. "
    + "A small batch brewed only once.","image_url":"https://images.punkapi.com/v2/keg.png",
    "abv":4.5,"ibu":60,"target_fg":1010,"target_og":1044,"ebc":20,"srm":10,"ph":4.4,"attenuation_level":75,
    "volume":{"value":20,"unit":"litres"},"boil_volume":{"value":25,"unit":"litres"},
    "method":{"mash_temp":[{"temp":{"value":64,"unit":"celsius"},"duration":75}],
    "fermentation":{"temp":{"value":19,"unit":"celsius"}},"twist":null},
    "ingredients":{"malt":[{"name":"Maris Otter Extra Pale","amount":{"value":3.3,"unit":"kilograms"}},
    {"name":"Caramalt","amount":{"value":0.2,"unit":"kilograms"}},
    {"name":"Munich","amount":{"value":0.4,"unit":"kilograms"}}],
    "hops":[{"name":"Fuggles","amount":{"value":25,"unit":"grams"},
        "add":"start","attribute":"bitter"},{"name":"First Gold","amount":{"value":25,"unit":"grams"},
        "add":"start","attribute":"bitter"},{"name":"Fuggles","amount":{"value":37.5,"unit":"grams"},
        "add":"middle","attribute":"flavour"}*/