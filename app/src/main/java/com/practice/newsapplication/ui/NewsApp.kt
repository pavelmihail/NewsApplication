package com.practice.newsapplication.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.practice.newsapplication.BottomMenuScreen
import com.practice.newsapplication.MockData
import com.practice.newsapplication.components.BottomMenu
import com.practice.newsapplication.network.NewsManager
import com.practice.newsapplication.ui.screen.Categories
import com.practice.newsapplication.ui.screen.DetailScreen
import com.practice.newsapplication.ui.screen.Sources
import com.practice.newsapplication.ui.screen.TopNews

//main composable
@Composable
fun NewsApp() {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()
    MainScreen(navController = navController, scrollState = scrollState)
}

@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState) {
    Scaffold(bottomBar = { BottomMenu(navController = navController) }) {
        Navigation(navController = navController, scrollState = scrollState)
    }
}

//define the navigation
@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    newsManager: NewsManager = NewsManager()
) {
    val articles = newsManager.newsResponse.value.articles
    Log.d("news", "$articles")

    //navigation composable component
    NavHost(navController = navController, startDestination = "TopNews") {
        bottomNavigation(navController = navController)
        composable("TopNews") {
            TopNews(navController = navController)
        }
        composable(
            "DetailScreen/{newsId}",
            arguments = listOf(navArgument("newsId") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("newsId")
            val newsData = MockData.getNews(id)
            DetailScreen(scrollState, newsData, navController)
        }
    }
}

fun NavGraphBuilder.bottomNavigation(navController: NavController) {
    composable(BottomMenuScreen.TopNew.route) {
        TopNews(navController = navController)
    }
    composable(BottomMenuScreen.Categories.route) {
        Categories()
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources()
    }
}