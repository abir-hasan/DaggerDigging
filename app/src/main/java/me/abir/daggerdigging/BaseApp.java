package me.abir.daggerdigging;

import android.app.Activity;
import android.app.Application;

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
