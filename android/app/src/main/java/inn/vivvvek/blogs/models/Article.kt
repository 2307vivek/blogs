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

@Serializable
data class Company(
    val title: String,
    @SerialName("html_url") val blogUrl: String,
    @SerialName("xml_url") val blogFeedUrl: String,
)
