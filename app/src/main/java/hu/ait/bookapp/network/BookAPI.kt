package hu.ait.bookapp.network

import hu.ait.bookapp.data.Doc
import hu.ait.bookapp.data.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface BookAPI {
    @GET("search.json")
    suspend fun getBookInfo(
        @Query("q") BookTitle: String
    ): SearchResult


}