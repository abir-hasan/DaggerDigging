package me.abir.daggerdigging;

import android.util.Log;

import java.util.List;

import me.abir.daggerdigging.models.Result;
import me.abir.daggerdigging.models.TopTvModel;
import me.abir.daggerdigging.network.TMDbService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abirhasan on 3/20/18.
 */

public class MainPresenter implements MainScreenContract.Presenter {
    private static final String TAG = "MainPresenter";
    private Call<TopTvModel> responseCall;
    private TMDbService tmDbService;
    private MainScreenContract.View view;
    private List<Result> modelResponse;

    public MainPresenter(MainScreenContract.View view, TMDbService tmDbService) {
        this.view = view;
        this.tmDbService = tmDbService;
    }

    @Override
    public void populateData() {

        responseCall = tmDbService.getTopTvSeries("4ff569ef5e249f43e790c8e30cbee249",
                "en-US", 1);
        responseCall.enqueue(new Callback<TopTvModel>() {
            @Override
            public void onResponse(Call<TopTvModel> call, Response<TopTvModel> response) {
                Log.w(TAG, "onResponse() called with: call = [" + call + "]," +
                        " response = [" + response.body().getResults() + "]");
                modelResponse = response.body().getResults();

                if (modelResponse != null) {
                    view.showData(modelResponse);
                } else {
                    view.showErrorOnLoading();
                }
            }

            @Override
            public void onFailure(Call<TopTvModel> call, Throwable t) {
                view.showErrorOnLoading();
            }
        });

    }
}
