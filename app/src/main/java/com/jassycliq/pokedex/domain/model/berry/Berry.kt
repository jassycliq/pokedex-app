package com.jassycliq.pokedex.domain.model.berry

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class Berry(
    /**
     * The firmness of this type of berry, used in making Pokéblocks or Poffins.
     */
    @Json(name = "firmness")
    val firmness: Firmness,
    /**
     * A list of references to each flavor a berry can have and the potency of each of those flavors in regard to this berry.
     */
    @Json(name = "flavors")
    val flavors: List<Flavor>,
    /**
     * The time it takes the tree to grow one stage, in hours. Berry trees go through four of these growth stages before they can be picked.
     */
    @Json(name = "growth_time")
    val growthTime: Int,
    /**
     * The identifier for this resource.
     */
    @Json(name = "id")
    val id: Int,
    /**
     * Berry are actually items. This is a reference to the item specific data for this berry.
     */
    @Json(name = "item")
    val item: Item,
    /**
     * The maximum number of these berries that can grow on one tree in Generation IV.
     */
    @Json(name = "max_harvest")
    val maxHarvest: Int,
    /**
     * The name for this resource.
     */
    @Json(name = "name")
    val name: String,
    /**
     * The power of the move "Natural Gift" when used with this Berry.
     */
    @Json(name = "natural_gift_power")
    val naturalGiftPower: Int,
    /**
     * The type inherited by "Natural Gift" when used with this Berry.
     */
    @Json(name = "natural_gift_type")
    val naturalGiftType: NaturalGiftType,
    /**
     * The size of this Berry, in millimeters.
     */
    @Json(name = "size")
    val size: Int,
    /**
     * The smoothness of this Berry, used in making Pokéblocks or Poffins.
     */
    @Json(name = "smoothness")
    val smoothness: Int,
    /**
     * The speed at which this Berry dries out the soil as it grows. A higher rate means the soil dries more quickly.
     */
    @Json(name = "soil_dryness")
    val soilDryness: Int,
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class Firmness(
        @Json(name = "name")
        val name: String,
        @Json(name = "url")
        val url: String,
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class Flavor(
        @Json(name = "flavor")
        val flavor: Flavor, // The referenced berry flavor.
        @Json(name = "potency")
        val potency: Int, // How powerful the referenced flavor is for this berry.
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class Flavor(
            @Json(name = "name")
            val name: String,
            @Json(name = "url")
            val url: String,
        )
    }

    @Keep
    @JsonClass(generateAdapter = true)
    data class Item(
        @Json(name = "name")
        val name: String,
        @Json(name = "url")
        val url: String,
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class NaturalGiftType(
        @Json(name = "name")
        val name: String,
        @Json(name = "url")
        val url: String,
    )
}
