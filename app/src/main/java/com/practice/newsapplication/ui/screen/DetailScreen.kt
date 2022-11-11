package com.practice.newsapplication.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.practice.newsapplication.NewsData

@Composable
fun DetailScreen(navController: NavController, newsData: NewsData){
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold)
        Button(onClick = {
            navController.navigate("TopNews")
        }) {
            Text(text = "Go to top news + ${newsData.author}")
        }
    }
}

@Preview
@Composable
fun DetailScreenPreview(){
    TopNews(rememberNavController())
}