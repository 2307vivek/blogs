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
package inn.vivvvek.blogs.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import inn.vivvvek.blogs.auth.BlogAuth
import inn.vivvvek.blogs.data.repository.ArticlesRepository
import inn.vivvvek.blogs.models.AuthenticatedUser
import inn.vivvvek.blogs.models.BlogArticle
import inn.vivvvek.blogs.models.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val auth: BlogAuth,
    private val articlesRepository: ArticlesRepository
) : ViewModel() {
    val currentUser: AuthenticatedUser?
        get() = auth.loggedInUser

    val isLoggedIn: Boolean
        get() = auth.isLoggedIn

    val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state

    init {
        getLatestArticles()
    }

    fun getLatestArticles() {
        _state.value = HomeScreenState(isLoading = true)
        viewModelScope.launch {
            when (val result = articlesRepository.getLatestArticles(1)) {
                is Result.Success -> {
                    _state.value = HomeScreenState(articles = result.data.articles, isLoading = false)
                    Log.d("HomeViewModel", "getLatestArticles: ${result.data.articles[0]}")
                }
                is Result.Error -> {
                    _state.value = HomeScreenState(error = result.error, isLoading = false)
                    Log.d("HomeViewModel", "getLatestArticles: ${result.error}")
                }
            }
        }
    }
}

data class HomeScreenState(
    val isLoading: Boolean = false,
    val articles: List<BlogArticle> = emptyList(),
    val error: String? = null
)
