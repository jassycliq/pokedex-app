package com.jassycliq.pokedex.ui.pokemon

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.jassycliq.pokedex.R
import com.jassycliq.pokedex.extension.orEmptyThreeLine
import com.jassycliq.pokedex.ui.pokemon.Pages.First
import com.jassycliq.pokedex.ui.pokemon.Pages.Fourth
import com.jassycliq.pokedex.ui.pokemon.Pages.Second
import com.jassycliq.pokedex.ui.pokemon.Pages.Third
import com.jassycliq.pokedex.ui.pokemon.PokemonDetailViewModel.State
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
@ExperimentalMaterial3Api
fun PokemonDetailScreen(
    fetchPokemon: () -> Unit,
    state: State,
) {

    LaunchedEffect(Unit) {
        fetchPokemon()
    }

    PokemonDetailContent(state = state)
}

@ExperimentalFoundationApi
@Composable
@ExperimentalMaterial3Api
private fun PokemonDetailContent(state: State) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        val (card, background, image, vector) = createRefs()
        val imageBoundary = createGuidelineFromTop(.45f)
        val cardBoundary = createGuidelineFromTop(.4f)

        Surface(
            color = Color(state.cardColors.surface),
            modifier = Modifier
                .constrainAs(background) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(imageBoundary)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(16.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Text(
                        text = state.name.orEmpty(),
                        color = Color(state.cardColors.text),
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier
                            .placeholder(
                                visible = state.name.isNullOrEmpty(),
                                highlight = PlaceholderHighlight.shimmer(),
                            )
                            .alignByBaseline(),
                    )
                    Text(
                        text = state.id.orEmpty(),
                        color =  Color(state.cardColors.text),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .placeholder(
                                visible = state.id.isNullOrEmpty(),
                                highlight = PlaceholderHighlight.shimmer(),
                            )
                            .alignByBaseline(),
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                        modifier = Modifier
                            .alignByBaseline(),
                    ) {
                        state.types?.forEach {
                            TypeChip(
                                text = it,
                                bodyColor = Color(state.cardColors.text),
                                backgroundColor = Color(state.cardColors.surface).copy(alpha = 0.5f),
                            )
                        }
                    }
                    Text(
                        text = state.genus.orEmpty(),
                        color = Color(state.cardColors.text),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier
                            .placeholder(
                                visible = state.genus.isNullOrEmpty(),
                                highlight = PlaceholderHighlight.shimmer(),
                            )
                            .alignByBaseline(),
                    )
                }
            }
        }

        AsyncImage(
            model = null,
            fallback = painterResource(id = R.drawable.ic_pokeball),
            alpha = 0.25f,
            colorFilter = ColorFilter.tint(
                color = MaterialTheme.colorScheme.primary.compositeOver(Color(state.cardColors.surface)),
                blendMode = BlendMode.SrcIn
            ),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            alignment = Alignment.BottomCenter,
            modifier = Modifier
                .rotate(-25f)
                .constrainAs(vector) {
                    bottom.linkTo(background.bottom)
                    end.linkTo(background.end)
                    start.linkTo(background.start)
                    height = Dimension.percent(.35f)
                    width = Dimension.fillToConstraints
                },
        )

        ElevatedCard(
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp,
            ),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),
            modifier = Modifier
                .constrainAs(card) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    top.linkTo(cardBoundary)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
        ) {
            val pagerState = rememberPagerState()
            val coroutineScope = rememberCoroutineScope()
            val pages = listOf(First("About"), Second("Base Stats"), Third("Evolution"), Fourth("Moves"))

            Column {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        )
                    }
                ) {
                    pages.forEachIndexed { index, page ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                        ) {
                            Text(
                                text = page.name,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier
                                    .padding(top = 32.dp, bottom = 16.dp)
                            )
                        }
                    }
                }

                HorizontalPager(
                    pageCount = pages.size,
                    state = pagerState,
                    key = { pages[it].name }
                ) { page ->
                    Column(modifier = Modifier.fillMaxSize()) {
                        when (pages[page]) {
                             is First -> {
                                 Text(
                                     text = state.species.orEmptyThreeLine(),
                                     style = MaterialTheme.typography.bodyMedium,
                                     modifier = Modifier
                                         .fillMaxWidth()
                                         .padding(16.dp)
                                         .placeholder(
                                             visible = state.species.isNullOrEmpty(),
                                             color = MaterialTheme.colorScheme.surfaceVariant,
                                             highlight = PlaceholderHighlight.shimmer(),
                                         ),
                                 )
                                 ElevatedCard(
                                     modifier = Modifier
                                         .align(Alignment.CenterHorizontally)
                                         .wrapContentHeight()
                                         .fillMaxWidth(0.8f),
                                     colors = CardDefaults.elevatedCardColors(
                                         containerColor = MaterialTheme.colorScheme.surface,
                                         contentColor = MaterialTheme.colorScheme.onSurface,
                                     ),
                                 ) {
                                     Column(
                                         verticalArrangement = Arrangement.spacedBy(4.dp),
                                         modifier = Modifier
                                             .fillMaxWidth()
                                             .align(Alignment.CenterHorizontally)
                                             .padding(
                                                 vertical = 16.dp,
                                                 horizontal = 32.dp,
                                             ),
                                     ) {
                                         Row {
                                             Text(
                                                 text = "Height",
                                                 style = MaterialTheme.typography.labelLarge,
                                                 color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                                 modifier = Modifier
                                                     .weight(1f),
                                             )
                                             Text(
                                                 text = "Weight",
                                                 style = MaterialTheme.typography.labelLarge,
                                                 color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                                 modifier = Modifier
                                                     .weight(1f),
                                             )
                                         }
                                         Row {
                                             Text(
                                                 text = state.height.orEmpty(),
                                                 style = MaterialTheme.typography.labelLarge,
                                                 modifier = Modifier
                                                     .placeholder(
                                                         visible = state.height.isNullOrEmpty(),
                                                         color = MaterialTheme.colorScheme.surfaceVariant,
                                                         highlight = PlaceholderHighlight.shimmer(),
                                                     )
                                                     .weight(1f),
                                             )
                                             Text(
                                                 text = state.weight.orEmpty(),
                                                 style = MaterialTheme.typography.labelLarge,
                                                 modifier = Modifier
                                                     .placeholder(
                                                         visible = state.weight.isNullOrEmpty(),
                                                         color = MaterialTheme.colorScheme.surfaceVariant,
                                                         highlight = PlaceholderHighlight.shimmer(),
                                                     )
                                                     .weight(1f),
                                             )
                                         }
                                     }
                                 }

                                 Text(
                                     text = "Breeding",
                                     style = MaterialTheme.typography.titleSmall,
                                     modifier = Modifier
                                         .padding(top = 16.dp)
                                         .padding(horizontal = 16.dp),
                                 )
                             }
                            is Fourth -> Text(
                                text = "Dope",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(16.dp),
                            )
                            is Second -> Text(
                                text = "Yeah yuh",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(16.dp),
                            )
                            is Third -> Text(
                                text = "Another one",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(16.dp),
                            )
                        }
                    }
                }
            }
        }

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(state.avatar)
                .build(),
            contentDescription = null,
            alignment = Alignment.BottomCenter,
            fallback = painterResource(id = R.drawable.ic_unknown_pokemon),
            modifier = Modifier
                .constrainAs(image) {
                    bottom.linkTo(imageBoundary)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.percent(.35f)
                },
        )
    }
}

//@ExperimentalFoundationApi
//@Preview
//@Composable
//@ExperimentalMaterial3Api
//fun PreviewPokemonDetail() {
//    PokemonDetailContent(State())
//}

sealed class Pages {
    abstract val name: String
    data class First(override val name: String) : Pages()
    data class Second(override val name: String) : Pages()
    data class Third(override val name: String) : Pages()
    data class Fourth(override val name: String) : Pages()
}