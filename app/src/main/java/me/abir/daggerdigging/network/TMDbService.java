package me.abir.daggerdigging.network;


import io.reactivex.Observable;
import me.abir.daggerdigging.models.TopTvModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Abir on 28-Dec-17.
 */

public interface TMDbService {

    String BASE_URL_V3 = "https://api.themoviedb.org/3/";
    String POSTER_URL = "https://image.tmdb.org/t/p/w185/";

    //tv/top_rated?api_key={{api_key}}&language=en-US&page=1
    @GET("tv/top_rated")
    Call<TopTvModel> getTopTvSeries(@Query("api_key") String apiKey,
                                    @Query("language") String language,
                                    @Query("page") int page);

    @GET("tv/top_rated")
    Observable<TopTvModel> getTopTvSeriesRx(@Query("api_key") String apiKey,
                                            @Query("language") String language,
                                            @Query("page") int page);
}
