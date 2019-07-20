package me.abir.daggerdigging.dagger.movies

import dagger.Component
import me.abir.daggerdigging.dagger.rest.TMDbServiceComponent
import me.abir.daggerdigging.movie.MoviesActivity


@MoviesActivityScope
@Component(modules = [MoviesActivityModule::class], dependencies = [TMDbServiceComponent::class])
interface MoviesActivityComponent {

    fun inject(activity: MoviesActivity)
}