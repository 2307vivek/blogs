package inn.vivvvek.blogs.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import inn.vivvvek.blogs.data.network.BlogApiService
import inn.vivvvek.blogs.utils.NetworkUtils
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BlogApiModule {

    // provide a singleton BlogApiService instance
    @Provides
    @Singleton
    fun provideBlogApiService(): BlogApiService =
        retrofitBuilder.create(BlogApiService::class.java)

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(NetworkUtils.BASE_URL)
        .addConverterFactory(Json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
        .build()
}

fun String.toMediaType(): MediaType = MediaType.get("application/json; charset=utf-8")