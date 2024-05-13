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

    init {
        getLatestArticles()
    }

    fun getLatestArticles() {
        viewModelScope.launch {
            val articles = articlesRepository.getLatestArticles(1)
            Log.d("debuggg", articles.articles[0].toString())
        }
    }
}
