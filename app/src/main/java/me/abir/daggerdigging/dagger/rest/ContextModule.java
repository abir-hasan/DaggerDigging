package me.abir.daggerdigging.dagger.rest;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Abir on 01-Jan-18.
 */

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @TMDbAppScope
    public Context context() {
        return context;
    }

}
