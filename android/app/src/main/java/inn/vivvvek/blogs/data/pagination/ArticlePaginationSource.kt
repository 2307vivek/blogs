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
