package com.practice.newsapplication.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.practice.newsapplication.BottomMenuScreen
import com.practice.newsapplication.MockData
import com.practice.newsapplication.components.BottomMenu
import com.practice.newsapplication.models.TopNewsArticles
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
        Navigation(navController = navController, scrollState = scrollState, paddingValues = it)
    }
}

//define the navigation
@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    newsManager: NewsManager = NewsManager(),
    paddingValues: PaddingValues
) {
    val articles = newsManager.newsResponse.value.articles
    Log.d("news", "$articles")

    articles?.let{
        //navigation composable component
        NavHost(navController = navController, startDestination = BottomMenuScreen.TopNews.route, modifier = Modifier.padding(paddingValues = paddingValues)) {
            bottomNavigation(navController = navController, articles)
            composable(
                "DetailScreen/{index}",
                arguments = listOf(navArgument("index") { type = NavType.IntType })
            ) { navBackStackEntry ->
                val index = navBackStackEntry.arguments?.getInt("index")
                index?.let{
                    val article = articles[index]
                    DetailScreen(scrollState, article, navController)
                }

            }
        }
    }
}

fun NavGraphBuilder.bottomNavigation(navController: NavController, articles:List<TopNewsArticles>) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController = navController, articles = articles)
    }
    composable(BottomMenuScreen.Categories.route) {
        Categories()
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources()
    }
}