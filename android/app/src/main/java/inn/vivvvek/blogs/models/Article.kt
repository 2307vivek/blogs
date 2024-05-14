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
package inn.vivvvek.blogs.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlogArticle(
    val id: String,
    val article: Article,
    val company: Company,
)

@Serializable
data class Articles(
    val page: Int,
    val articles: List<BlogArticle>
)

@Serializable
data class Article(
    val title: String,
    val description: String = "",
    @SerialName("link") val url: String,
    val guid: String = "",
    @SerialName("publishedParsed") val publishedAt: String = "",
    val image: Image? = null,
)

@Serializable
data class Image(
    val url: String,
)
