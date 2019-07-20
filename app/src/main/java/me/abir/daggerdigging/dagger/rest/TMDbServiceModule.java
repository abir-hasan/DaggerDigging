package me.abir.daggerdigging.dagger.rest;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import me.abir.daggerdigging.network.TMDbService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Abir on 01-Jan-18.
 */

@Module(includes = {NetworkModule.class, GsonModule.class})
public class TMDbServiceModule {

    @Provides
    @TMDbAppScope
    public TMDbService tmdbService(Retrofit retrofit) {

        return retrofit.create(TMDbService.class);
    }

    @Provides
    @TMDbAppScope
    public Retrofit retrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(TMDbService.Companion.getBASE_URL_V3())
                .build();
    }
}
