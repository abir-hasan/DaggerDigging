package me.abir.daggerdigging.dagger.activity_main;

import dagger.Component;
import me.abir.daggerdigging.MainActivity;
import me.abir.daggerdigging.MainScreenContract;
import me.abir.daggerdigging.TvAdapter;
import me.abir.daggerdigging.dagger.rest.TMDbServiceComponent;

/**
 * Created by Abir on 07-Jan-18.
 */

@MainActivityScope
@Component(modules = MainActivityModule.class, dependencies = TMDbServiceComponent.class)
public interface MainActivityComponent {
/*
    TvAdapter tvAdapter();

    MainScreenContract.Presenter getPresenter();*/

    void inject(MainActivity activity);
}
