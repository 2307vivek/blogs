package inn.vivvvek.blogs.data.repository.impl

import inn.vivvvek.blogs.data.network.BlogApiService
import inn.vivvvek.blogs.data.repository.BlogRepository
import inn.vivvvek.blogs.models.Blogs
import inn.vivvvek.blogs.models.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BlogRepositoryImpl @Inject constructor(
    private val api: BlogApiService
) : BlogRepository {
    override suspend fun getAllBlogs(page: Int): Result<Blogs> {
        try {
            val response = api.getAllBlogs(page)
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