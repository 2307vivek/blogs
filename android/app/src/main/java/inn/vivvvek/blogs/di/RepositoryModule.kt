package inn.vivvvek.blogs.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import inn.vivvvek.blogs.data.network.BlogApiService
import inn.vivvvek.blogs.data.repository.ArticlesRepository
import inn.vivvvek.blogs.data.repository.BlogRepository
import inn.vivvvek.blogs.data.repository.impl.ArticlesRepositoryImpl
import inn.vivvvek.blogs.data.repository.impl.BlogRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Binds
    fun bindArticleRepository(apiService: BlogApiService) : ArticlesRepository {
        return ArticlesRepositoryImpl(apiService)
    }

    @Binds
    fun bindBlogRepository(apiService: BlogApiService) : BlogRepository {
        return BlogRepositoryImpl(apiService)
    }
}