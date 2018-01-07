package me.abir.daggerdigging;

import android.app.Activity;
import android.app.Application;

import me.abir.daggerdigging.dagger.ContextModule;
import me.abir.daggerdigging.dagger.DaggerTMDbServiceComponent;
import me.abir.daggerdigging.dagger.TMDbServiceComponent;
import timber.log.Timber;

/**
 * Created by Abir on 28-Dec-17.
 */

public class BaseApp extends Application {
    private static final String TAG = "BaseApp";
    private TMDbServiceComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        component = DaggerTMDbServiceComponent.builder()
                // We only need to add this because it's the only external dependency
                .contextModule(new ContextModule(this))
                .build();
    }

    public TMDbServiceComponent tmDbServiceComponent() {
        return this.component;
    }

    public static BaseApp get(Activity activity) {
        return (BaseApp) activity.getApplication();
    }

}
