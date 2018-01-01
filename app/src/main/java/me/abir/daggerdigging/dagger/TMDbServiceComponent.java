package me.abir.daggerdigging.dagger;

import com.squareup.picasso.Picasso;

import dagger.Component;
import me.abir.daggerdigging.network.TMDbService;

/**
 * Created by Abir on 01-Jan-18.
 */

@TMDbAppScope
@Component(modules = {TMDbServiceModule.class, PicassoModule.class})
public interface TMDbServiceComponent {

    Picasso getPicasso();

    TMDbService getTMDbService();
}
