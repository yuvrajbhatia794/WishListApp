package com.example.mywishlistapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.mywishlistapp.HomeView


@Composable
fun Navigation(modifier :Modifier = Modifier, viewModel: WishViewModel = viewModel(), navController : NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Home){
        composable<Screen.Home> {
            HomeView(modifier, navController, viewModel)
        }

        composable<Screen.AddScreen> {
            val id = it.toRoute<Screen.AddScreen>().id
            AddEditDetailView(id, viewModel, navController)
        }
    }
}