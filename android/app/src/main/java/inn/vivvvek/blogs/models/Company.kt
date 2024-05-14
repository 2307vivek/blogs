package inn.vivvvek.blogs.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Company(
    val title: String,
    @SerialName("html_url") val blogUrl: String,
    @SerialName("xml_url") val blogFeedUrl: String,
)
