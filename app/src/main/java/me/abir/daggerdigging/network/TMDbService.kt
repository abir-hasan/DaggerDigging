package me.abir.daggerdigging.network


import io.reactivex.Observable
import io.reactivex.Single
import me.abir.daggerdigging.models.TopTvModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Abir on 28-Dec-17.
 */

interface TMDbService {

    companion object {
        val BASE_URL_V3 = "https://api.themoviedb.org/3/"
        val POSTER_URL = "https://image.tmdb.org/t/p/w185/"
    }

    //tv/top_rated?api_key={{api_key}}&language=en-US&page=1
    @GET("tv/top_rated")
    fun getTopTvSeries(@Query("api_key") apiKey: String,
                       @Query("language") language: String,
                       @Query("page") page: Int): Call<TopTvModel>

    @GET("tv/top_rated")
    fun getTopTvSeriesRx(@Query("api_key") apiKey: String,
                         @Query("language") language: String,
                         @Query("page") page: Int): Observable<TopTvModel>

    @GET("tv/top_rated")
    fun getTopTvSeriesSingle(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
    ): Single<Response<TopTvModel>>

}
