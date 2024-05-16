/*
 * Copyright 2024 Vivek Singh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package inn.vivvvek.blogs.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import inn.vivvvek.blogs.models.Article
import inn.vivvvek.blogs.models.BlogArticle
import inn.vivvvek.blogs.models.Company
import inn.vivvvek.blogs.ui.screens.components.ArticleItem
import inn.vivvvek.blogs.utils.DateUtils

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToSignIn: () -> Unit
) {
    LaunchedEffect(viewModel.isLoggedIn) {
        if (!viewModel.isLoggedIn) {
            navigateToSignIn()
        }
    }

    val homeScreenState by viewModel.state.collectAsState()

    HomeScreen(state = homeScreenState)
}

@Composable
fun HomeScreen(state: HomeScreenState) {
    Scaffold(
        topBar = {
            MainTopAppBar(title = "Explore")
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.error != null) {
                Text(text = state.error)
            } else {
                MainContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    blogArticles = state.articles
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    title: String
) {
    TopAppBar(
        title = { Text(text = title) },
        actions = {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "Menu"
            )
        },
    )
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    blogArticles: List<BlogArticle>
) {
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(
                items = blogArticles,
                //key = { it.id }
            ) {
                ArticleItem(
                    blogArticle = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun HomePreview() {
    val state = HomeScreenState(
        isLoading = false,
        error = null,
        articles = listOf(blogArticle, blogArticle, blogArticle)
    )
    HomeScreen(state)
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun PreviewArticleItem() {

    Surface(onClick = { /*TODO*/ }) {
        ArticleItem(
            blogArticle = blogArticle,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

val blogArticle = BlogArticle(
    id = "",
    article = Article(
        title = "This is a article title.",
        description = "This is a article description.",
        url = "",
        guid = "",
        publishedAt = DateUtils.parseDate("2023-07-01T00:00:00Z").toString(),
        image = null
    ),
    company = Company(
        title = "Netflix",
        blogUrl = "",
        blogFeedUrl = ""
    )
)
