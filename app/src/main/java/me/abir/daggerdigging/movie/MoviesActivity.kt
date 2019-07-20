package me.abir.daggerdigging.movie

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import me.abir.daggerdigging.BaseApp
import me.abir.daggerdigging.R
import me.abir.daggerdigging.dagger.movies.DaggerMoviesActivityComponent
import me.abir.daggerdigging.dagger.movies.MoviesActivityModule
import me.abir.daggerdigging.dagger.rest.TMDbServiceComponent
import me.abir.daggerdigging.models.Result
import me.abir.daggerdigging.showSnackbarShort
import javax.inject.Inject

class MoviesActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MoviesActivity"
    }

    @Inject
    lateinit var appComponent: TMDbServiceComponent

    @Inject
    lateinit var viewModel: MoviesActivityVM

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var mAdapter: MoviesActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDependency()
        initViews()
        observeTvSeriesData()
    }

    private fun observeTvSeriesData() {
        viewModel.getSeriesData(appComponent.tmDbService)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { pbLoading.visibility = View.VISIBLE }
                .doAfterTerminate { pbLoading.visibility = View.GONE }
                .subscribe(
                        { handleResponse(it) },
                        {
                            handleResponse(it.message
                                    ?: getString(R.string.text_something_went_wrong))
                        }
                ).let { compositeDisposable.add(it) }
    }

    private fun handleResponse(response: Any) {
        try {
            when (response) {
                is String -> {
                    showSnackbarShort(response)
                }
                is List<*> -> {
                    val list = ArrayList<Result>()
                    //val anotherList = response.filterIsInstance<Result>().takeIf { it.size == response.size }
                    list.addAll(response.filterIsInstance<Result>())
                    mAdapter.updateList(list)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun onClickSeries(position: Int) {
        Log.i(TAG, "$position")
    }

    private fun initViews() {
        with(rvTvSeries) {
            layoutManager = LinearLayoutManager(this@MoviesActivity)
            adapter = mAdapter
        }
        mAdapter.onItemClickListener = ::onClickSeries
    }

    private fun initDependency() {
        val component = DaggerMoviesActivityComponent.builder()
                .moviesActivityModule(MoviesActivityModule(this))
                .tMDbServiceComponent(BaseApp.get(this).tmDbServiceComponent())
                .build()
        component.inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}