package com.example.catbreeds.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.catbreeds.presentation.navigation.CatBreedDetailsRoute
import com.example.catbreeds.presentation.navigation.CatBreedsOverViewRoute
import com.example.catbreeds.presentation.ui.screen.BreedDetailScreen
import com.example.catbreeds.presentation.ui.screen.BreedListOverViewScreen
import com.example.catbreeds.presentation.ui.theme.CatBreedsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatBreedsActivity : ComponentActivity() {

    val viewModel: CatBreedsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatBreedsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = CatBreedsOverViewRoute) {
                        composable<CatBreedsOverViewRoute> {
                            BreedListOverViewScreen(
                                viewModel,
                                modifier = Modifier.padding(innerPadding),
                                navigateToDetailScreen = { breedId ->
                                    navController.navigate(CatBreedDetailsRoute(breedId))
                                },
                            )
                        }
                        composable<CatBreedDetailsRoute> { navBackStackEntry ->
                            val breedDetailRoute: CatBreedDetailsRoute = navBackStackEntry.toRoute()
                            BreedDetailScreen(
                                breedId = breedDetailRoute.id,
                                viewModel = viewModel,
                                modifier = Modifier.padding(innerPadding),
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
