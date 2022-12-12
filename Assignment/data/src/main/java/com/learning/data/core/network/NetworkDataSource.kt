package com.learning.data.core.network

import com.learning.data.core.network.api.ClassifiedListingApi
import com.learning.data.core.network.api.GetMovieDetailsResponse
import com.learning.data.core.network.api.GetMovieResponse
import retrofit2.Retrofit

open class NetworkDataSource(
    retrofitClient: Retrofit
) : ClassifiedListingApi {

    private val listingClient: ClassifiedListingApi by lazy {
        retrofitClient.create(
            ClassifiedListingApi::class.java
        )
    }

    override suspend fun getMovieList(base: String, apiKey: String): GetMovieResponse =
        listingClient.getMovieList(base, apiKey)

    override suspend fun getMovieDetails(base: String, apiKey: String): GetMovieDetailsResponse =
        listingClient.getMovieDetails(base, apiKey)


}
    


