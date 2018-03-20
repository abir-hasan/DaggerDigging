package me.abir.daggerdigging.dagger.activity_main;

import dagger.Component;
import me.abir.daggerdigging.TvAdapter;
import me.abir.daggerdigging.dagger.rest.TMDbServiceComponent;
import me.abir.daggerdigging.network.TMDbService;

/**
 * Created by Abir on 07-Jan-18.
 */

@MainActivityScope
@Component(modules = MainActivityModule.class, dependencies = TMDbServiceComponent.class)
public interface MainActivityComponent {

    TvAdapter tvAdapter();

    TMDbService tmDbService();
}
