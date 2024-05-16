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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import inn.vivvvek.blogs.auth.BlogAuth
import inn.vivvvek.blogs.data.repository.ArticlesRepository
import inn.vivvvek.blogs.models.AuthenticatedUser
import inn.vivvvek.blogs.models.BlogArticle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
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

    private val _state = MutableStateFlow<PagingData<BlogArticle>>(PagingData.empty())
    val state: StateFlow<PagingData<BlogArticle>> = _state

    init {
        getLatestArticles()
    }

    fun getLatestArticles() {
        viewModelScope.launch {
            articlesRepository.getLatestArticles()
                .distinctUntilChanged()
                .collect {
                    _state.value = it
                }
        }
    }
}

data class HomeScreenState(
    val isLoading: Boolean = false,
    val articles: List<BlogArticle> = emptyList(),
    val error: String? = null
)
