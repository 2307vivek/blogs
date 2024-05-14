package inn.vivvvek.blogs.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Blog(
    val id: String,
    val company: Company,
    @SerialName("blog_title") val title: String,
    val description: String,
    @SerialName("link") val url: String,
    @SerialName("feed_link") val feedLink: String,
    val image: Image? = null
)

@Serializable
data class Blogs(
    val blogs: List<Blog>,
    val page: Int,
)

