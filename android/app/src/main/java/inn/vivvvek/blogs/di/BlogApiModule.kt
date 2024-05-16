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
package inn.vivvvek.blogs.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import inn.vivvvek.blogs.data.network.BlogApiService
import inn.vivvvek.blogs.utils.NetworkUtils
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
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
        .addConverterFactory(json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
        .build()
}

private val json = Json {
    ignoreUnknownKeys = true
}
