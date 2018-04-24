package me.abir.daggerdigging;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
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
    private final CompositeDisposable disposable = new CompositeDisposable();


    public MainPresenter(MainScreenContract.View view, TMDbService tmDbService) {
        this.view = view;
        this.tmDbService = tmDbService;
    }

    @Override
    public void populateData() {

        disposable.add(getData(pageCount)
                .subscribeOn(Schedulers.io())
                .map(new Function<TopTvModel, List<Result>>() {
                    @Override
                    public List<Result> apply(TopTvModel topTvModel) throws Exception {
                        return topTvModel.getResults();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Result>>() {
                    @Override
                    public void onNext(List<Result> results) {
                        if (results != null) {
                            view.showData(results);
                        } else {
                            view.showErrorOnLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showErrorOnLoading();
                    }

                    @Override
                    public void onComplete() {
                        pageCount++;
                        view.showToastOnComplete();
                    }
                }));

    }

    @Override
    public void unSubscribeRx() {
        if (!disposable.isDisposed())
            disposable.dispose();
    }


    Observable<TopTvModel> getData(int pageNo) {
        return tmDbService.getTopTvSeriesRx("4ff569ef5e249f43e790c8e30cbee249",
                "en-US", pageNo);
    }
}
