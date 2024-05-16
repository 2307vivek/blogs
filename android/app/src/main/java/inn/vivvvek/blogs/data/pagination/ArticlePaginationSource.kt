package inn.vivvvek.blogs.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import inn.vivvvek.blogs.data.network.BlogApiService
import inn.vivvvek.blogs.models.BlogArticle
import java.io.IOException
import javax.inject.Inject

class ArticlePaginationSource @Inject constructor(
    private val apiService: BlogApiService
) : PagingSource<Int, BlogArticle>() {
    override fun getRefreshKey(state: PagingState<Int, BlogArticle>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BlogArticle> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getLatestArticles(currentPage)
            if (response.isSuccessful) {
                val article = response.body()
                if (article != null) {
                    LoadResult.Page(
                        data = article.articles,
                        prevKey = if (currentPage == 1) null else currentPage - 1,
                        nextKey = currentPage.plus(1)
                    )
                } else {
                    LoadResult.Error(IOException("Something went wrong"))
                }
            } else {
                LoadResult.Error(IOException("Something went wrong"))
            }
        } catch (e: IOException) {
            LoadResult.Error(IOException("Cannot connect to the internet."))
        }
    }
}