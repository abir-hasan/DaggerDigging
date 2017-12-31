package me.abir.daggerdigging;

import android.app.Activity;
import android.app.Application;

import com.fatboyindustrial.gsonjodatime.DateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import me.abir.daggerdigging.network.TMDbService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Abir on 28-Dec-17.
 */

public class BaseApp extends Application {

    private TMDbService tmdbService;

    @Override
    public void onCreate() {
        super.onCreate();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeConverter());

        Gson gson = gsonBuilder.create();

        Retrofit tmdbRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(TMDbService.BASE_URL_V3)
                .build();

        tmdbService = tmdbRetrofit.create(TMDbService.class);
    }

    public static BaseApp get(Activity activity) {
        return (BaseApp) activity.getApplication();
    }

    public TMDbService getTMDbService() {
        return tmdbService;
    }
}
