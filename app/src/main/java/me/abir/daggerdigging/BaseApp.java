package me.abir.daggerdigging;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.squareup.picasso.Picasso;

import me.abir.daggerdigging.dagger.ContextModule;
import me.abir.daggerdigging.dagger.DaggerTMDbServiceComponent;
import me.abir.daggerdigging.dagger.TMDbServiceComponent;
import me.abir.daggerdigging.network.TMDbService;
import timber.log.Timber;

/**
 * Created by Abir on 28-Dec-17.
 */

public class BaseApp extends Application {
    private static final String TAG = "BaseApp";
    private TMDbService tmdbService;
    private Picasso picasso;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        TMDbServiceComponent component = DaggerTMDbServiceComponent.builder()
                // We only need to add this because it's the only external dependency
                .contextModule(new ContextModule(this))
                .build();

        tmdbService = component.getTMDbService();
        picasso = component.getPicasso();

        TMDbService tmDbService2 = component.getTMDbService();
        Picasso picasso2 = component.getPicasso();

        TMDbService tmDbService3 = component.getTMDbService();
        Picasso picasso3 = component.getPicasso();

        Log.i(TAG, "tmdbService: " + tmdbService);
        Log.w(TAG, "picasso: " + picasso);
        Log.i(TAG, "tmDbService2: " + tmDbService2);
        Log.w(TAG, "picasso2: " + picasso2);
        Log.i(TAG, "tmDbService3: " + tmDbService3);
        Log.w(TAG, "picasso3: " + picasso3);
    }

    public static BaseApp get(Activity activity) {
        return (BaseApp) activity.getApplication();
    }

    public TMDbService getTMDbService() {
        return tmdbService;
    }

    public Picasso getPicasso() {
        return picasso;
    }
}
