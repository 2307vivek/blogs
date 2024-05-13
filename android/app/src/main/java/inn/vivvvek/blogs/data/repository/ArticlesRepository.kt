package inn.vivvvek.blogs.data.repository

import inn.vivvvek.blogs.data.network.BlogApiService
import javax.inject.Inject

class ArticlesRepository @Inject constructor(
    private val api: BlogApiService
) {

    suspend fun getLatestArticles(page: Int) = api.getLatestArticles(page)
}