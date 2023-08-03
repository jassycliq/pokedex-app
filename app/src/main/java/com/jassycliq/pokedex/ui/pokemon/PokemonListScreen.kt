package com.jassycliq.pokedex.ui.pokemon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.graphics.drawable.toBitmap
import androidx.paging.compose.LazyPagingItems
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jassycliq.pokedex.R
import com.jassycliq.pokedex.domain.model.Colors
import com.jassycliq.pokedex.domain.model.NamedApiResource
import com.jassycliq.pokedex.domain.model.pokemon.Pokemon
import com.jassycliq.pokedex.extension.items
import com.jassycliq.pokedex.extension.toDisplayName
import com.jassycliq.pokedex.ui.component.getDefaultThemeColors

@Composable
@ExperimentalLayoutApi
@ExperimentalMaterial3Api
fun PokemonListScreen(
    lazyGridState: LazyGridState,
    pokemonPagingItems: LazyPagingItems<NamedApiResource>,
    fetchPokemonDetails: suspend (String) -> Pokemon?,
    navigateToPokemonDetails: (Int, Colors) -> Unit,
) {
    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            items = pokemonPagingItems,
        ) { pokemon ->
            pokemon?.let {
                PokemonCard(
                    pokemon = it,
                    fetchPokemonDetails = fetchPokemonDetails,
                    navigateToPokemonDetails = navigateToPokemonDetails,
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun PokemonCard(
    pokemon: NamedApiResource,
    fetchPokemonDetails: suspend (String) -> Pokemon?,
    navigateToPokemonDetails: (Int, Colors) -> Unit,
) {
    val defaultColors = getDefaultThemeColors()
    var cardColors: Colors by remember { mutableStateOf(defaultColors) }
    var pokemonDetails: Pokemon? by remember { mutableStateOf(null) }

    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .allowHardware(false)
        .data(pokemonDetails?.sprites?.other?.officialArtwork?.frontDefault)
        .listener(
            onSuccess = { _, result ->
                Palette.Builder(result.drawable.toBitmap()).generate { palette ->
                    val pokedexBackground = Color(palette?.dominantSwatch?.rgb ?: defaultColors.surface)
                    var pokedexTitle: Color

                    pokedexBackground.luminance().let {
                        pokedexTitle = when (it > .5f) {
                            true -> Color.Black.copy(.7f).compositeOver(pokedexBackground)
                            false -> Color.White.copy(.8f).compositeOver(pokedexBackground)
                        }
                    }

                    cardColors = cardColors.copy(
                        surface = pokedexBackground.toArgb(),
                        primary = pokedexBackground.toArgb(),
                        onPrimary = pokedexTitle.toArgb(),
                        text = pokedexTitle.toArgb(),
                    )
                }
            }
        )
        .build()

    LaunchedEffect(pokemon.url) {
        fetchPokemonDetails(pokemon.url)?.let {
            pokemonDetails = it
        }
    }

    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color(cardColors.surface),
            ),
        modifier = Modifier
            .clickable {
                pokemonDetails?.id?.let {
                    navigateToPokemonDetails(it, cardColors)
                }
            }
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .fillMaxWidth()
                .height(150.dp),
        ) {
            val (column, image, vector) = createRefs()

            AsyncImage(
                model = null,
                fallback = painterResource(id = R.drawable.ic_pokeball),
                alpha = 0.25f,
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.primary.compositeOver(Color(cardColors.surface)),
                    blendMode = BlendMode.SrcIn
                ),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                alignment = Alignment.BottomCenter,
                modifier = Modifier
                    .rotate(-25f)
                    .constrainAs(vector) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        height = Dimension.matchParent
                        width = Dimension.percent(0.9f)
                    }
                    .fillMaxSize(),
            )
            AsyncImage(
                model = imageRequest,
                fallback = painterResource(id = R.drawable.ic_unknown_pokemon),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                alignment = Alignment.BottomEnd,
                modifier = Modifier
                    .constrainAs(image) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        height = Dimension.matchParent
                        width = Dimension.percent(0.75f)
                    }
                    .fillMaxSize(),
            )

            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .constrainAs(column) {
                        start.linkTo(parent.start)
                        height = Dimension.matchParent
                    }
            ) {
                Text(
                    text = pokemon.name.toDisplayName(),
                    color = Color(cardColors.text),
                    style = MaterialTheme.typography.titleLarge,
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterVertically),
                ) {
                    pokemonDetails?.types?.forEach { type ->
                        TypeChip(
                            text = type.type.name.capitalize(Locale.current),
                            bodyColor = Color(cardColors.text),
                            backgroundColor = Color(cardColors.surface).copy(alpha = 0.5f),
                        )
                    }
                }
            }
        }
    }
}
