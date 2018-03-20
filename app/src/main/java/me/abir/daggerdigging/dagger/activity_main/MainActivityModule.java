package me.abir.daggerdigging.dagger.activity_main;

import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import me.abir.daggerdigging.MainActivity;
import me.abir.daggerdigging.MainPresenter;
import me.abir.daggerdigging.MainScreenContract;
import me.abir.daggerdigging.TvAdapter;
import me.abir.daggerdigging.network.TMDbService;

/**
 * Created by Abir on 07-Jan-18.
 */

@Module
public class MainActivityModule {

    private final MainActivity mainActivity;

    public MainActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Provides
    @MainActivityScope
    public TvAdapter tvAdapter(Picasso picasso) {
        return new TvAdapter(mainActivity, picasso);
    }

    @Provides
    @MainActivityScope
    public MainScreenContract.Presenter getMainScreenPresent(TMDbService tmDbService) {
        return new MainPresenter(mainActivity, tmDbService);
    }

}
