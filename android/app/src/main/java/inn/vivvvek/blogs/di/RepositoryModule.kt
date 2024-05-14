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
    fun bindArticleRepository(apiService: BlogApiService): ArticlesRepository {
        return ArticlesRepositoryImpl(apiService)
    }

    @Binds
    fun bindBlogRepository(apiService: BlogApiService): BlogRepository {
        return BlogRepositoryImpl(apiService)
    }
}
