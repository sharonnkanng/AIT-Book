package hu.ait.bookapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    @SerialName("docs")
    var docs: List<Doc>? = null,
    @SerialName("numFound")
    var numFound: Int? = null,
    @SerialName("numFoundExact")
    var numFoundExact: Boolean? = null,
    @SerialName("offset")
    var offset: String? = null,
    @SerialName("q")
    var q: String? = null,
    @SerialName("start")
    var start: Int? = null
)