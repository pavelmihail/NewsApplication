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

@Composable
fun DetailScreen(navController: NavController){
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold)
        Button(onClick = {
            navController.navigate("TopNews")
        }) {
            Text(text = "Go to top news")
        }
    }
}

@Preview
@Composable
fun DetailScreenPreview(){
    TopNews(rememberNavController())
}