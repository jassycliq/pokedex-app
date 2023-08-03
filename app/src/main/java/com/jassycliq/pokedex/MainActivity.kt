package com.jassycliq.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.core.graphics.luminance
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jassycliq.pokedex.extension.rememberLazyGridState
import com.jassycliq.pokedex.ui.MainViewModel
import com.jassycliq.pokedex.ui.MainViewModel.SideEffect.NavigateToPokemonDetails
import com.jassycliq.pokedex.ui.MainViewModel.SideEffect.NavigateToPokemonList
import com.jassycliq.pokedex.ui.component.PokedexFloatingActionButton
import com.jassycliq.pokedex.ui.component.PokedexTopAppBar
import com.jassycliq.pokedex.ui.component.getDefaultThemeColors
import com.jassycliq.pokedex.ui.navigation.PokedexNavigation
import com.jassycliq.pokedex.ui.theme.PokedexTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@ExperimentalFoundationApi
@ExperimentalLayoutApi
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUi = rememberSystemUiController()
            val defaultColors = getDefaultThemeColors()
            PokedexTheme(systemUi = systemUi) {
                // region State
                val navController: NavHostController = rememberNavController()
                val viewModel: MainViewModel = koinViewModel { parametersOf(defaultColors) }
                val state by viewModel.collectAsState()

                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(state = state.topAppBarState)
//                val setLazyGridState: (LazyGridState) -> Unit = { viewModel.setLazyGridState(it) }
                LaunchedEffect(Unit) {
                    viewModel.setScrollToTop { launch { animateScrollToItem(0) } }
                }

                LaunchedEffect(state.themeColors) {
                    state.themeColors.primary.luminance.let {
                        systemUi.setSystemBarsColor(
                            color = Color.Transparent,
                            darkIcons = it > 0.5f,
                        )
                    }
                }

                viewModel.collectSideEffect { sideEffect ->
                    when (sideEffect) {
                        is NavigateToPokemonList ->
                            viewModel.setState(
                                forceTopAppBar = false,
                                showFab = true,
                                showBackIcon = false,
                                showSearchIcon = true,
                            )
                        is NavigateToPokemonDetails ->
                            viewModel.setState(
                                forceTopAppBar = true,
                                showFab = false,
                                showBackIcon = true,
                                showSearchIcon = false,
                            )
                    }
                }
                // endregion

                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        PokedexTopAppBar(
                            colors = state.themeColors,
                            scrollBehavior = if (state.forceTopAppBar) null else scrollBehavior,
                            navigationIcon = {
                                AnimatedVisibility(visible = state.showBackIcon) {
                                    IconButton(onClick = navController::popBackStack) {
                                        Icon(
                                            imageVector = Icons.TwoTone.ArrowBack,
                                            contentDescription = "Back button"
                                        )
                                    }
                                }
                            },
                        ) {
                            AnimatedVisibility(visible = state.showSearchIcon) {
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Icons.TwoTone.Search,
                                        contentDescription = "Search button"
                                    )
                                }
                            }
                        }
                    },
                    floatingActionButton = {
                        PokedexFloatingActionButton(
                            showFab = when (state.showFab) {
                                true -> state.canScrollBackwards
                                false -> false
                            },
                            scrollToTop = { derivedStateOf { state.scrollToTop?.invoke(state.lazyGridState) } },
                        )
                    },
                    floatingActionButtonPosition = FabPosition.End,
                    content = { paddingValues ->
                        Surface(
                            color = MaterialTheme.colorScheme.surface,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                                .consumeWindowInsets(paddingValues),
                        ) {
                            PokedexNavigation(
                                navController = navController,
                                onNavigateToDestination = viewModel::onNavigateToDestination,
//                                lazyGridState = state.lazyGridState,
//                                setLazyGridState = setLazyGridState,
                            )
                        }
                    }
                )
            }
        }
    }
}
