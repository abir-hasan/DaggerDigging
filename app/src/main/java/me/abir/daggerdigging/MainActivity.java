package me.abir.daggerdigging;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import me.abir.daggerdigging.dagger.activity_main.DaggerMainActivityComponent;
import me.abir.daggerdigging.dagger.activity_main.MainActivityComponent;
import me.abir.daggerdigging.dagger.activity_main.MainActivityModule;
import me.abir.daggerdigging.models.Result;

public class MainActivity extends AppCompatActivity implements MainScreenContract.View {

    private static final String TAG = "MainActivity";

    private MainActivityComponent mainActivityComponent;
    private RecyclerView rvTvSeries;
    private TvAdapter tvAdapter;
    private MainScreenContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDependency();
        initView();
        getDataFromApi();
    }

    private void initDependency() {
        mainActivityComponent = DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this))
                .tMDbServiceComponent(BaseApp.get(this).tmDbServiceComponent())
                .build();

        presenter = mainActivityComponent.getPresenter();
    }

    private void initView() {
        rvTvSeries = findViewById(R.id.rvTvSeries);
        rvTvSeries.setLayoutManager(new LinearLayoutManager(this));
        tvAdapter = mainActivityComponent.tvAdapter();
        rvTvSeries.setAdapter(tvAdapter);
    }

    private void getDataFromApi() {
        presenter.populateData();
    }


    @Override
    public void showData(List<Result> resultList) {
        tvAdapter.setTVData(resultList);
    }

    @Override
    public void showErrorOnLoading() {
        Log.d(TAG, "showErrorOnLoading() called");
    }
}
