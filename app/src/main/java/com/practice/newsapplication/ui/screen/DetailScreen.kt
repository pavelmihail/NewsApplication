package com.practice.newsapplication.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.practice.newsapplication.MockData
import com.practice.newsapplication.MockData.getTimeAgo
import com.practice.newsapplication.NewsData
import com.practice.newsapplication.R

@Composable
fun DetailScreen(scrollState: ScrollState, newsData: NewsData, navController: NavController) {
    Scaffold(topBar = {
        DetailTopAppBar(onBackPress = { navController.popBackStack() })
    }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold)
            Image(painter = painterResource(id = newsData.image), contentDescription = "")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoWithIcon(Icons.Default.Edit, info = newsData.author)
                //display how much time ago the article was released
                InfoWithIcon(
                    Icons.Default.DateRange,
                    info = MockData.stringToDate(newsData.publishedAt).getTimeAgo()
                )
            }
            Text(text = newsData.title, fontWeight = FontWeight.Bold)
            Text(text = newsData.description, modifier = Modifier.padding(top = 16.dp))
        }


    }
}

@Composable
fun DetailTopAppBar(onBackPress: () -> Unit = {}) {
    TopAppBar(
        title = { Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold) },
        navigationIcon = {
            IconButton(onClick = { onBackPress }) {
                Image(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        })
}

@Composable
fun InfoWithIcon(icon: ImageVector, info: String) {
    Row {
        Icon(
            icon,
            contentDescription = "",
            modifier = Modifier.padding(end = 8.dp),
            colorResource(id = R.color.purple_500)
        )
        Text(text = info)
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    TopNews(rememberNavController())
}