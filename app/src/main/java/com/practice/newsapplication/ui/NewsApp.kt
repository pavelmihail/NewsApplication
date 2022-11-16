package com.practice.newsapplication.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.practice.newsapplication.BottomMenuScreen
import com.practice.newsapplication.MockData
import com.practice.newsapplication.components.BottomMenu
import com.practice.newsapplication.models.TopNewsArticles
import com.practice.newsapplication.network.Api
import com.practice.newsapplication.network.NewsManager
import com.practice.newsapplication.ui.screen.Categories
import com.practice.newsapplication.ui.screen.DetailScreen
import com.practice.newsapplication.ui.screen.Sources
import com.practice.newsapplication.ui.screen.TopNews

//main composable
@Composable
fun NewsApp(mainViewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()
    MainScreen(
        navController = navController,
        scrollState = scrollState,
        mainViewModel = mainViewModel
    )
}

@Composable
fun MainScreen(
    navController: NavHostController,
    scrollState: ScrollState,
    mainViewModel: MainViewModel
) {
    Scaffold(bottomBar = { BottomMenu(navController = navController) }) {
        Navigation(
            navController = navController,
            scrollState = scrollState,
            paddingValues = it,
            viewModel = mainViewModel
        )
    }
}

//define the navigation
@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    newsManager: NewsManager = NewsManager(Api.retrofitService),
    paddingValues: PaddingValues,
    viewModel: MainViewModel
) {
    val articles = mutableListOf(TopNewsArticles())
    val topArticles = viewModel.newsResponse.collectAsState().value.articles
    articles.addAll(topArticles ?: listOf())

    NavHost(
        navController = navController,
        startDestination = BottomMenuScreen.TopNews.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        bottomNavigation(
            navController = navController,
            articles = articles,
            newsManager = newsManager,
            viewModel = viewModel
        )
        composable(
            "DetailScreen/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt("index")
            index?.let {
                val article = articles[index]
                DetailScreen(scrollState, article, navController)
            }
        }
    }
}

fun NavGraphBuilder.bottomNavigation(
    navController: NavController,
    articles: List<TopNewsArticles>,
    newsManager: NewsManager,
    viewModel: MainViewModel
) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController = navController, articles = articles)
    }

    composable(BottomMenuScreen.Categories.route) {

        viewModel.getArticlesByCategory("business")
        viewModel.onSelectedCategoryChanged("business")

        Categories(viewModel = viewModel, onFetchCategory = {

            viewModel.onSelectedCategoryChanged(it)
            viewModel.getArticlesByCategory(it)

        })
    }

    composable(BottomMenuScreen.Sources.route) {
        Sources(viewModel = viewModel)
    }
}