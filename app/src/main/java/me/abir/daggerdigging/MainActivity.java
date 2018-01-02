package me.abir.daggerdigging;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import me.abir.daggerdigging.models.TopTvModel;
import me.abir.daggerdigging.network.TMDbService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TMDbService tmDbService;
    private Call<TopTvModel> responseCall;
    private RecyclerView rvTvSeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tmDbService = BaseApp.get(this).getTMDbService();

        responseCall = tmDbService.getTopTvSeries("4ff569ef5e249f43e790c8e30cbee249",
                "en-US", 1);
        responseCall.enqueue(new Callback<TopTvModel>() {
            @Override
            public void onResponse(Call<TopTvModel> call, Response<TopTvModel> response) {
                Log.w(TAG, "onResponse() called with: call = [" + call + "]," +
                        " response = [" + response.body().getResults() + "]");
            }

            @Override
            public void onFailure(Call<TopTvModel> call, Throwable t) {
                Log.e(TAG, "onFailure() ");
            }
        });

        initView();
    }

    private void initView() {
        rvTvSeries = findViewById(R.id.rvTvSeries);
        rvTvSeries.setLayoutManager(new LinearLayoutManager(this));
        rvTvSeries.setAdapter(new TvAdapter());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (responseCall != null) {
            responseCall.cancel();
        }
    }
}
