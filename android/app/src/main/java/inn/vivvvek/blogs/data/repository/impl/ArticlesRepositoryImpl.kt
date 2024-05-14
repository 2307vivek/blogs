package inn.vivvvek.blogs.data.repository.impl

import inn.vivvvek.blogs.data.network.BlogApiService
import inn.vivvvek.blogs.data.repository.ArticlesRepository
import inn.vivvvek.blogs.models.Articles
import inn.vivvvek.blogs.models.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticlesRepositoryImpl @Inject constructor(
    private val api: BlogApiService
) : ArticlesRepository {
    override suspend fun getLatestArticles(page: Int): Result<Articles> {
        try {
            val response = api.getLatestArticles(page)
            if (response.isSuccessful) {
                response.body()?.let {
                    return Result.Success(it)
                }
            }
            return if (response.code() == 401) {
                Result.Error("Unauthorized")
            } else {
                Result.Error("Something went wrong")
            }
        } catch (e: Exception) {
            return Result.Error("Cannot connect to server.")
        }
    }

    override suspend fun getArticleByCompany(page: Int, companyName: String): Result<Articles> {
        try {
            val response = api.getArticleByCompany(page, companyName)
            if (response.isSuccessful) {
                response.body()?.let {
                    return Result.Success(it)
                }
            }
            return if (response.code() == 401) {
                Result.Error("Unauthorized")
            } else {
                Result.Error("Something went wrong")
            }
        } catch (e: Exception) {
            return Result.Error("Cannot connect to server.")
        }
    }
}