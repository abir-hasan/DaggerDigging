package me.abir.daggerdigging.dagger;

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
    public Context context() {
        return context;
    }

}
