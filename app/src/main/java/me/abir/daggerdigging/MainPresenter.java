package me.abir.daggerdigging;

import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import me.abir.daggerdigging.db.AppDatabase;
import me.abir.daggerdigging.models.Result;
import me.abir.daggerdigging.models.TopTvModel;
import me.abir.daggerdigging.network.TMDbService;

/**
 * Created by abirhasan on 3/20/18.
 */

public class MainPresenter implements MainScreenContract.Presenter {
    private static final String TAG = "MainPresenter";
    private TMDbService tmDbService;
    private MainScreenContract.View view;
    private int pageCount = 1;
    private int getPage = 0;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private AppDatabase database;


    public MainPresenter(MainScreenContract.View view, TMDbService tmDbService, AppDatabase database) {
        this.view = view;
        this.tmDbService = tmDbService;
        this.database = database;
    }

    @Override
    public void populateData() {

        disposable.add(asdfdsaf(pageCount)
                .subscribeOn(Schedulers.io())
                /*.map(new Function<TopTvModel, List<Result>>() {
                    @Override
                    public List<Result> apply(TopTvModel topTvModel) throws Exception {
                        return topTvModel.getResults();
                    }
                })*/
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Result>>() {
                    @Override
                    public void onNext(List<Result> results) {
                        if (results != null) {
                            // view.showData(results);
                            database.getTvDao().multiInsert(results);
                        } else {
                            view.showErrorOnLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ");
                        view.showErrorOnLoading();
                    }

                    @Override
                    public void onComplete() {
                        /*Log.e(TAG, "onComplete: count: " + pageCount);
                        pageCount++;*/
                        //view.showToastOnComplete();
                    }
                }));


    }


    @Override
    public void unSubscribeRx() {
        if (!disposable.isDisposed())
            disposable.dispose();
    }

    @Override
    public void getDbUpdate() {
        /*disposable.add(database.getTvDao().getAllRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Result>>() {
                    @Override
                    public void accept(List<Result> results) throws Exception {
                        Log.w(TAG, "accept() called with: results = [" + results.size() + "]");
                        if (results.size() > 0)
                            view.showData(results);
                    }
                }));*/
        disposable.add(database
                .getTvDao()
                .getMaxPageCount()
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer currentMax) throws Exception {
                        Log.e(TAG, "accept() called with: currentMax = [" + currentMax
                                + "] + pageCount = " + pageCount);
                        if (currentMax - pageCount < 3) {
                            getPage = currentMax + 1;
                            populateData();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "NO PAGE " );
                        getPage =1;
                        populateData();
                    }
                }));

        disposable.add(database.getTvDao().getAllAsList()
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Result>>() {
                    @Override
                    public void accept(List<Result> results) throws Exception {
                        Log.d(TAG, "accept() db size = [" + results.size() + "]");
                    }
                }));

        Log.i(TAG, "getDbUpdate() pageCount: " + pageCount);
        disposable.add(database.getTvDao().getAllReposByPage(pageCount)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                /*.subscribeWith(new DisposableObserver<List<Result>>() {
                    @Override
                    public void onNext(List<Result> results) {
                        Log.w(TAG, "onNext() called with: results = [" + results.size() + "]");
                        if (results.size() > 0)
                            view.showData(results);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " );
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: " );
                    }
                }));*/
                .subscribe(new Consumer<List<Result>>() {
                    @Override
                    public void accept(List<Result> results) throws Exception {
                        Log.w(TAG, "From DB Update accept() size = [" + results.size() + "] forPage: " + pageCount);
                        if (results.size() > 0) {
                            view.showData(results);
                            pageCount++;
                        }
                    }
                }));
    }


    private Observable<List<Result>> getData(final int pageNo) {
        return tmDbService.getTopTvSeriesRx("4ff569ef5e249f43e790c8e30cbee249",
                "en-US", pageNo)
                .concatMap(new Function<TopTvModel, ObservableSource<? extends List<Result>>>() {
                    @Override
                    public ObservableSource<List<Result>> apply(TopTvModel topTvModel) throws Exception {
                        return Observable.just(topTvModel.getResults());
                    }
                });
    }

    private Observable<List<Result>> asdfdsaf(final int pageNo) {
        return tmDbService.getTopTvSeriesRx("4ff569ef5e249f43e790c8e30cbee249",
                "en-US", pageNo)
                .concatMap(new Function<TopTvModel, ObservableSource<? extends List<Result>>>() {
                    @Override
                    public ObservableSource<List<Result>> apply(TopTvModel topTvModel) throws Exception {
                        return Observable.fromIterable(topTvModel.getResults())
                                .map(new Function<Result, Result>() {
                                    @Override
                                    public Result apply(Result result) throws Exception {
                                        result.setPageNo(getPage);
                                        return result;
                                    }
                                }).toList().toObservable();
                    }
                });
    }
}
