package inn.vivvvek.blogs.data.network

import inn.vivvvek.blogs.models.Article
import inn.vivvvek.blogs.models.Articles
import inn.vivvvek.blogs.models.BlogArticle
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BlogApiService {

    @GET("articles")
    suspend fun getLatestArticles(
        @Query("page") page: Int
    ): Articles

    @GET("articles/{company}")
    suspend fun getArticleByCompany(
        @Query("page") page: Int,
        @Path("company") companyName: String
    ): Articles
}