package com.jassycliq.pokedex.domain.model.pokemon

import androidx.annotation.Keep
import com.jassycliq.pokedex.domain.database.entity.PokemonEntity
import com.jassycliq.pokedex.domain.model.NamedApiResource
import com.jassycliq.pokedex.domain.model.emptyNamedApiResource
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class Pokemon(
    @Json(name = "abilities")
    val abilities: List<Ability> = listOf(),
    @Json(name = "base_experience")
    val baseExperience: Int? = null,
    @Json(name = "forms")
    val forms: List<NamedApiResource> = listOf(),
    @Json(name = "game_indices")
    val gameIndices: List<GameIndice> = listOf(),
    @Json(name = "height")
    val height: Int = 0,
    @Json(name = "held_items")
    val heldItems: List<HeldItem> = listOf(),
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "is_default")
    val isDefault: Boolean = false,
    @Json(name = "location_area_encounters")
    val locationAreaEncounters: String = "",
    @Json(name = "moves")
    val moves: List<Move> = listOf(),
    @Json(name = "name")
    val name: String = "",
    @Json(name = "order")
    val order: Int = 0,
    @Json(name = "past_types")
    val pastTypes: List<PastType> = listOf(),
    @Json(name = "species")
    val species: Species = Species(),
    @Json(name = "sprites")
    val sprites: Sprites = Sprites(),
    @Json(name = "stats")
    val stats: List<Stat> = listOf(),
    @Json(name = "types")
    val types: List<Type> = listOf(),
    @Json(name = "weight")
    val weight: Int = 0
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class Ability(
        @Json(name = "ability")
        val ability: NamedApiResource = emptyNamedApiResource(),
        @Json(name = "is_hidden")
        val isHidden: Boolean = false,
        @Json(name = "slot")
        val slot: Int = 0
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class GameIndice(
        @Json(name = "game_index")
        val gameIndex: Int = 0,
        @Json(name = "version")
        val version: NamedApiResource = emptyNamedApiResource()
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class HeldItem(
        @Json(name = "item")
        val item: NamedApiResource = emptyNamedApiResource(),
        @Json(name = "version_details")
        val versionDetails: List<VersionDetail> = listOf()
    ) {

        @Keep
        @JsonClass(generateAdapter = true)
        data class VersionDetail(
            @Json(name = "rarity")
            val rarity: Int = 0,
            @Json(name = "version")
            val version: NamedApiResource = emptyNamedApiResource()
        )
    }

    @Keep
    @JsonClass(generateAdapter = true)
    data class Move(
        @Json(name = "move")
        val move: NamedApiResource = emptyNamedApiResource(),
        @Json(name = "version_group_details")
        val versionGroupDetails: List<VersionGroupDetail> = listOf()
    ) {

        @Keep
        @JsonClass(generateAdapter = true)
        data class VersionGroupDetail(
            @Json(name = "level_learned_at")
            val levelLearnedAt: Int = 0,
            @Json(name = "move_learn_method")
            val moveLearnMethod: NamedApiResource = emptyNamedApiResource(),
            @Json(name = "version_group")
            val versionGroup: NamedApiResource = emptyNamedApiResource()
        )
    }

    @Keep
    @JsonClass(generateAdapter = true)
    data class PastType(
        @Json(name = "generation")
        val generation: NamedApiResource = emptyNamedApiResource(),
        @Json(name = "types")
        val types: List<Type> = listOf()
    ) {

        @Keep
        @JsonClass(generateAdapter = true)
        data class Type(
            @Json(name = "slot")
            val slot: Int = 0,
            @Json(name = "type")
            val type: NamedApiResource = emptyNamedApiResource()
        )
    }

    @Keep
    @JsonClass(generateAdapter = true)
    data class Sprites(
        @Json(name = "back_default")
        val backDefault: String? = "",
        @Json(name = "back_female")
        val backFemale: String? = null,
        @Json(name = "back_shiny")
        val backShiny: String? = "",
        @Json(name = "back_shiny_female")
        val backShinyFemale: String? = null,
        @Json(name = "front_default")
        val frontDefault: String? = "",
        @Json(name = "front_female")
        val frontFemale: String? = null,
        @Json(name = "front_shiny")
        val frontShiny: String? = "",
        @Json(name = "front_shiny_female")
        val frontShinyFemale: String? = null,
        @Json(name = "other")
        val other: Other = Other(),
        @Json(name = "versions")
        val versions: Versions = Versions(),
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class Other(
            @Json(name = "dream_world")
            val dreamWorld: DreamWorld = DreamWorld(),
            @Json(name = "home")
            val home: Home = Home(),
            @Json(name = "official-artwork")
            val officialArtwork: OfficialArtwork = OfficialArtwork()
        ) {
            @Keep
            @JsonClass(generateAdapter = true)
            data class DreamWorld(
                @Json(name = "front_default")
                val frontDefault: String? = "",
                @Json(name = "front_female")
                val frontFemale: String? = null
            )

            @Keep
            @JsonClass(generateAdapter = true)
            data class Home(
                @Json(name = "front_default")
                val frontDefault: String? = "",
                @Json(name = "front_female")
                val frontFemale: String? = null,
                @Json(name = "front_shiny")
                val frontShiny: String? = "",
                @Json(name = "front_shiny_female")
                val frontShinyFemale: String? = null
            )

            @Keep
            @JsonClass(generateAdapter = true)
            data class OfficialArtwork(
                @Json(name = "front_default")
                val frontDefault: String? = ""
            )
        }

        @Keep
        @JsonClass(generateAdapter = true)
        data class Versions(
            @Json(name = "generation-i")
            val generationI: GenerationI = GenerationI(),
            @Json(name = "generation-ii")
            val generationIi: GenerationIi = GenerationIi(),
            @Json(name = "generation-iii")
            val generationIii: GenerationIii = GenerationIii(),
            @Json(name = "generation-iv")
            val generationIv: GenerationIv = GenerationIv(),
            @Json(name = "generation-v")
            val generationV: GenerationV = GenerationV(),
            @Json(name = "generation-vi")
            val generationVi: GenerationVi = GenerationVi(),
            @Json(name = "generation-vii")
            val generationVii: GenerationVii = GenerationVii(),
            @Json(name = "generation-viii")
            val generationViii: GenerationViii = GenerationViii()
        ) {
            @Keep
            @JsonClass(generateAdapter = true)
            data class GenerationI(
                @Json(name = "red-blue")
                val redBlue: RedBlue = RedBlue(),
                @Json(name = "yellow")
                val yellow: Yellow = Yellow()
            ) {
                @Keep
                @JsonClass(generateAdapter = true)
                data class RedBlue(
                    @Json(name = "back_default")
                    val backDefault: String? = "",
                    @Json(name = "back_gray")
                    val backGray: String? = "",
                    @Json(name = "front_default")
                    val frontDefault: String? = "",
                    @Json(name = "front_gray")
                    val frontGray: String? = ""
                )

                @Keep
                @JsonClass(generateAdapter = true)
                data class Yellow(
                    @Json(name = "back_default")
                    val backDefault: String? = "",
                    @Json(name = "back_gray")
                    val backGray: String? = "",
                    @Json(name = "front_default")
                    val frontDefault: String? = "",
                    @Json(name = "front_gray")
                    val frontGray: String? = ""
                )
            }

            @Keep
            @JsonClass(generateAdapter = true)
            data class GenerationIi(
                @Json(name = "crystal")
                val crystal: Crystal = Crystal(),
                @Json(name = "gold")
                val gold: Gold = Gold(),
                @Json(name = "silver")
                val silver: Silver = Silver()
            ) {
                @Keep
                @JsonClass(generateAdapter = true)
                data class Crystal(
                    @Json(name = "back_default")
                    val backDefault: String? = "",
                    @Json(name = "back_shiny")
                    val backShiny: String? = "",
                    @Json(name = "front_default")
                    val frontDefault: String? = "",
                    @Json(name = "front_shiny")
                    val frontShiny: String? = ""
                )

                @Keep
                @JsonClass(generateAdapter = true)
                data class Gold(
                    @Json(name = "back_default")
                    val backDefault: String? = "",
                    @Json(name = "back_shiny")
                    val backShiny: String? = "",
                    @Json(name = "front_default")
                    val frontDefault: String? = "",
                    @Json(name = "front_shiny")
                    val frontShiny: String? = ""
                )

                @Keep
                @JsonClass(generateAdapter = true)
                data class Silver(
                    @Json(name = "back_default")
                    val backDefault: String? = "",
                    @Json(name = "back_shiny")
                    val backShiny: String? = "",
                    @Json(name = "front_default")
                    val frontDefault: String? = "",
                    @Json(name = "front_shiny")
                    val frontShiny: String? = ""
                )
            }

            @Keep
            @JsonClass(generateAdapter = true)
            data class GenerationIii(
                @Json(name = "emerald")
                val emerald: Emerald = Emerald(),
                @Json(name = "firered-leafgreen")
                val fireredLeafgreen: FireredLeafgreen = FireredLeafgreen(),
                @Json(name = "ruby-sapphire")
                val rubySapphire: RubySapphire = RubySapphire()
            ) {
                @Keep
                @JsonClass(generateAdapter = true)
                data class Emerald(
                    @Json(name = "front_default")
                    val frontDefault: String? = "",
                    @Json(name = "front_shiny")
                    val frontShiny: String? = ""
                )

                @Keep
                @JsonClass(generateAdapter = true)
                data class FireredLeafgreen(
                    @Json(name = "back_default")
                    val backDefault: String? = "",
                    @Json(name = "back_shiny")
                    val backShiny: String? = "",
                    @Json(name = "front_default")
                    val frontDefault: String? = "",
                    @Json(name = "front_shiny")
                    val frontShiny: String? = ""
                )

                @Keep
                @JsonClass(generateAdapter = true)
                data class RubySapphire(
                    @Json(name = "back_default")
                    val backDefault: String? = "",
                    @Json(name = "back_shiny")
                    val backShiny: String? = "",
                    @Json(name = "front_default")
                    val frontDefault: String? = "",
                    @Json(name = "front_shiny")
                    val frontShiny: String? = ""
                )
            }

            @Keep
            @JsonClass(generateAdapter = true)
            data class GenerationIv(
                @Json(name = "diamond-pearl")
                val diamondPearl: DiamondPearl = DiamondPearl(),
                @Json(name = "heartgold-soulsilver")
                val heartgoldSoulsilver: HeartgoldSoulsilver = HeartgoldSoulsilver(),
                @Json(name = "platinum")
                val platinum: Platinum = Platinum()
            ) {
                @Keep
                @JsonClass(generateAdapter = true)
                data class DiamondPearl(
                    @Json(name = "back_default")
                    val backDefault: String? = "",
                    @Json(name = "back_female")
                    val backFemale: String? = null,
                    @Json(name = "back_shiny")
                    val backShiny: String? = "",
                    @Json(name = "back_shiny_female")
                    val backShinyFemale: String? = null,
                    @Json(name = "front_default")
                    val frontDefault: String? = "",
                    @Json(name = "front_female")
                    val frontFemale: String? = null,
                    @Json(name = "front_shiny")
                    val frontShiny: String? = "",
                    @Json(name = "front_shiny_female")
                    val frontShinyFemale: String? = null
                )

                @Keep
                @JsonClass(generateAdapter = true)
                data class HeartgoldSoulsilver(
                    @Json(name = "back_default")
                    val backDefault: String? = "",
                    @Json(name = "back_female")
                    val backFemale: String? = null,
                    @Json(name = "back_shiny")
                    val backShiny: String? = "",
                    @Json(name = "back_shiny_female")
                    val backShinyFemale: String? = null,
                    @Json(name = "front_default")
                    val frontDefault: String? = "",
                    @Json(name = "front_female")
                    val frontFemale: String? = null,
                    @Json(name = "front_shiny")
                    val frontShiny: String? = "",
                    @Json(name = "front_shiny_female")
                    val frontShinyFemale: String? = null
                )

                @Keep
                @JsonClass(generateAdapter = true)
                data class Platinum(
                    @Json(name = "back_default")
                    val backDefault: String? = "",
                    @Json(name = "back_female")
                    val backFemale: String? = null,
                    @Json(name = "back_shiny")
                    val backShiny: String? = "",
                    @Json(name = "back_shiny_female")
                    val backShinyFemale: String? = null,
                    @Json(name = "front_default")
                    val frontDefault: String? = "",
                    @Json(name = "front_female")
                    val frontFemale: String? = null,
                    @Json(name = "front_shiny")
                    val frontShiny: String? = "",
                    @Json(name = "front_shiny_female")
                    val frontShinyFemale: String? = null
                )
            }

            @Keep
            @JsonClass(generateAdapter = true)
            data class GenerationV(
                @Json(name = "black-white")
                val blackWhite: BlackWhite = BlackWhite()
            ) {
                @Keep
                @JsonClass(generateAdapter = true)
                data class BlackWhite(
                    @Json(name = "animated")
                    val animated: Animated = Animated(),
                    @Json(name = "back_default")
                    val backDefault: String? = "",
                    @Json(name = "back_female")
                    val backFemale: String? = null,
                    @Json(name = "back_shiny")
                    val backShiny: String? = "",
                    @Json(name = "back_shiny_female")
                    val backShinyFemale: String? = null,
                    @Json(name = "front_default")
                    val frontDefault: String? = "",
                    @Json(name = "front_female")
                    val frontFemale: String? = null,
                    @Json(name = "front_shiny")
                    val frontShiny: String? = "",
                    @Json(name = "front_shiny_female")
                    val frontShinyFemale: String? = null
                ) {
                    @Keep
                    @JsonClass(generateAdapter = true)
                    data class Animated(
                        @Json(name = "back_default")
                        val backDefault: String? = "",
                        @Json(name = "back_female")
                        val backFemale: String? = null,
                        @Json(name = "back_shiny")
                        val backShiny: String? = "",
                        @Json(name = "back_shiny_female")
                        val backShinyFemale: String? = null,
                        @Json(name = "front_default")
                        val frontDefault: String? = "",
                        @Json(name = "front_female")
                        val frontFemale: String? = null,
                        @Json(name = "front_shiny")
                        val frontShiny: String? = "",
                        @Json(name = "front_shiny_female")
                        val frontShinyFemale: String? = null
                    )
                }
            }

            @Keep
            @JsonClass(generateAdapter = true)
            data class GenerationVi(
                @Json(name = "omegaruby-alphasapphire")
                val omegarubyAlphasapphire: OmegarubyAlphasapphire = OmegarubyAlphasapphire(),
                @Json(name = "x-y")
                val xY: XY = XY()
            ) {
                @Keep
                @JsonClass(generateAdapter = true)
                data class OmegarubyAlphasapphire(
                    @Json(name = "front_default")
                    val frontDefault: String? = "",
                    @Json(name = "front_female")
                    val frontFemale: String? = null,
                    @Json(name = "front_shiny")
                    val frontShiny: String? = "",
                    @Json(name = "front_shiny_female")
                    val frontShinyFemale: String? = null
                )

                @Keep
                @JsonClass(generateAdapter = true)
                data class XY(
                    @Json(name = "front_default")
                    val frontDefault: String? = "",
                    @Json(name = "front_female")
                    val frontFemale: String? = null,
                    @Json(name = "front_shiny")
                    val frontShiny: String? = "",
                    @Json(name = "front_shiny_female")
                    val frontShinyFemale: String? = null
                )
            }

            @Keep
            @JsonClass(generateAdapter = true)
            data class GenerationVii(
                @Json(name = "icons")
                val icons: Icons = Icons(),
                @Json(name = "ultra-sun-ultra-moon")
                val ultraSunUltraMoon: UltraSunUltraMoon = UltraSunUltraMoon()
            ) {
                @Keep
                @JsonClass(generateAdapter = true)
                data class Icons(
                    @Json(name = "front_default")
                    val frontDefault: String? = "",
                    @Json(name = "front_female")
                    val frontFemale: String? = null
                )

                @Keep
                @JsonClass(generateAdapter = true)
                data class UltraSunUltraMoon(
                    @Json(name = "front_default")
                    val frontDefault: String? = "",
                    @Json(name = "front_female")
                    val frontFemale: String? = null,
                    @Json(name = "front_shiny")
                    val frontShiny: String? = "",
                    @Json(name = "front_shiny_female")
                    val frontShinyFemale: String? = null
                )
            }

            @Keep
            @JsonClass(generateAdapter = true)
            data class GenerationViii(
                @Json(name = "icons")
                val icons: Icons = Icons()
            ) {
                @Keep
                @JsonClass(generateAdapter = true)
                data class Icons(
                    @Json(name = "front_default")
                    val frontDefault: String? = "",
                    @Json(name = "front_female")
                    val frontFemale: String? = null
                )
            }
        }
    }

    @Keep
    @JsonClass(generateAdapter = true)
    data class Stat(
        @Json(name = "base_stat")
        val baseStat: Int = 0,
        @Json(name = "effort")
        val effort: Int = 0,
        @Json(name = "stat")
        val stat: NamedApiResource = emptyNamedApiResource()
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class Type(
        @Json(name = "slot")
        val slot: Int = 0,
        @Json(name = "type")
        val type: NamedApiResource = emptyNamedApiResource()
    )
}

fun Pokemon.toEntity(): PokemonEntity = PokemonEntity(
    id = id,
    name = name,
    height = height,
    weight = weight,
    order = order,
)