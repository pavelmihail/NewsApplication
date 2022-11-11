package com.practice.newsapplication.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.practice.newsapplication.ui.screen.DetailScreen
import com.practice.newsapplication.ui.screen.TopNews

//main composable
@Composable
fun NewsApp(){
    Navigation()
}

//define the navigation
@Composable
fun Navigation(){
    val navController = rememberNavController()
    //navigation composable component
    NavHost(navController = navController, startDestination = "TopNews"){
        composable("TopNews"){
            TopNews(navController = navController)
        }
        composable("DetailScreen"){
            DetailScreen(navController = navController)
        }
    }
}