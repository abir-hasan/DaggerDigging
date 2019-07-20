package me.abir.daggerdigging.dagger.movies

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import me.abir.daggerdigging.movie.MoviesActivity
import me.abir.daggerdigging.movie.MoviesActivityAdapter
import me.abir.daggerdigging.movie.MoviesActivityVM

@Module
class MoviesActivityModule(private val activity: MoviesActivity) {

    @Provides
    @MoviesActivityScope
    fun getViewModel() = ViewModelProviders.of(activity).get(MoviesActivityVM::class.java)

    @Provides
    @MoviesActivityScope
    fun getCompositeDisposable() = CompositeDisposable()

    @Provides
    @MoviesActivityScope
    fun getAdapter() = MoviesActivityAdapter()
}