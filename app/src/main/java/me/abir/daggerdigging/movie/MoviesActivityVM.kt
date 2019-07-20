package me.abir.daggerdigging.movie

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import me.abir.daggerdigging.models.TopTvModel
import me.abir.daggerdigging.network.TMDbService

class MoviesActivityVM : ViewModel() {

    companion object {
        const val TAG = "MoviesActivityVM"
        const val API_KEY = "4ff569ef5e249f43e790c8e30cbee249"
        const val LANGUAGE = "en-US"
    }

    fun getSeriesData(tmDbService: TMDbService): Single<Any> {
        return tmDbService.getTopTvSeriesSingle(API_KEY, LANGUAGE, 1)
                .map {
                    if (it.isSuccessful){
                        return@map (it.body() as TopTvModel).results
                    }else{
                        return@map "Something went wrong!"
                    }
                }

    }
}