package com.practice.newsapplication.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.practice.newsapplication.BottomMenuScreen
import com.practice.newsapplication.R
import com.practice.newsapplication.models.TopNewsArticles
import com.practice.newsapplication.network.NewsManager
import com.practice.newsapplication.ui.MainViewModel

@Composable
fun Sources(viewModel: MainViewModel) {

    val items = listOf(
        "TechCrunch" to "techcrunch",
        "TalckSport" to "talcksport",
        "Business Insider" to "business",
        "Reuters" to "reuters",
        "Politico" to "politico",
        "TheVerge" to "the-verge"
    )

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "${viewModel.sourceName.collectAsState().value} Source") },
            actions = {
                var menuExanded by remember { mutableStateOf(false) }
                IconButton(onClick = { menuExanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = null)
                }
                MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(.16.dp))) {
                    DropdownMenu(expanded = menuExanded, onDismissRequest = {
                        menuExanded = false
                    }) {
                        items.forEach {
                            DropdownMenuItem(onClick = {
                                viewModel.sourceName.value = it.second
                                viewModel.getArticleBySource
                                menuExanded = false
                            }) {
                                Text(it.first)
                            }
                        }
                    }
                }
            })
    }) {
        viewModel.getArticleBySource()
        val articles = viewModel.getArticleBySource.collectAsState().value
        SourceContent(article = articles.articles ?: listOf())
    }
}

@Composable
fun SourceContent(article: List<TopNewsArticles>) {
    val uriHandler = LocalUriHandler.current

    LazyColumn {
        items(article) { article ->
            val annotatedString = buildAnnotatedString {
                pushStringAnnotation(
                    tag = "URL",
                    annotation = article.url ?: "newsapi.org"
                )
                withStyle(
                    style = SpanStyle(
                        color = colorResource(id = R.color.purple_500),
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Read Full Article Here")
                }
            }
            Card(
                backgroundColor = colorResource(id = R.color.purple_500),
                elevation = 6.dp,
                modifier = Modifier.padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .height(200.dp)
                        .padding(end = 8.dp, start = 8.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {

                    Text(
                        text = article.title ?: "Not Available",
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = article.description ?: "Not Available",
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )

                    Card(
                        backgroundColor = Color.White,
                        elevation = 6.dp,
                    ) {
                        //open the URL link
                        ClickableText(
                            text = annotatedString,
                            modifier = Modifier.padding(6.dp), onClick = {
                                annotatedString.getStringAnnotations(it, it).firstOrNull()
                                    ?.let { result ->
                                        if (result.tag == "URL") {
                                            uriHandler.openUri(result.item)
                                        }
                                    }
                            })
                    }
                }
            }
        }
    }
}