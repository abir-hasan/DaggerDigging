package me.abir.daggerdigging.dagger;

import android.content.Context;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by Abir on 01-Jan-18.
 */

@Module(includes = ContextModule.class)
public class NetworkModule {


    @Provides
    public HttpLoggingInterceptor loggingInterceptor() {
        return new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Timber.i(message);
                    }
                });
    }

    @Provides
    public Cache cache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1000 * 1000); //10MB
    }

    @Provides
    public File file(Context context) {
        return new File(context.getCacheDir(), "okhttp_cache");
        //cacheFile.mkdirs();
    }


    @Provides
    public OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();
    }
}
