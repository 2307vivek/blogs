package inn.vivvvek.blogs.data.repository

import inn.vivvvek.blogs.models.Blog
import inn.vivvvek.blogs.models.Blogs
import inn.vivvvek.blogs.models.Result

interface BlogRepository {

    suspend fun getAllBlogs(page: Int) : Result<Blogs>
}