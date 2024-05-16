package inn.vivvvek.blogs.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import inn.vivvvek.blogs.models.BlogArticle

fun LazyListScope.articleList(blogArticles: LazyPagingItems<BlogArticle>) {
    items(
        blogArticles.itemCount,
    ) { index ->
        ArticleItem(
            blogArticles[index]!!,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
    blogArticles.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            loadState.refresh is LoadState.Error -> {
                val error = blogArticles.loadState.refresh as LoadState.Error
                item {
                    ErrorMessage(
                        modifier = Modifier.fillParentMaxSize(),
                        message = error.error.localizedMessage!!,
                        onClickRetry = { retry() })
                }
            }

            loadState.append is LoadState.Loading -> {
                item {
                    Row(horizontalArrangement = Arrangement.Center) {
                        CircularProgressIndicator()
                    }
                }
            }

            loadState.append is LoadState.Error -> {
                val error = blogArticles.loadState.append as LoadState.Error
                item {
                    ErrorMessage(
                        modifier = Modifier,
                        message = error.error.localizedMessage!!,
                        onClickRetry = { retry() })
                }
            }
        }
    }
}