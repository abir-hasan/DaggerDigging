package me.abir.daggerdigging;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.abir.daggerdigging.dagger.activity_main.DaggerMainActivityComponent;
import me.abir.daggerdigging.dagger.activity_main.MainActivityComponent;
import me.abir.daggerdigging.dagger.activity_main.MainActivityModule;
import me.abir.daggerdigging.models.Result;

public class MainActivity extends AppCompatActivity implements MainScreenContract.View, SearchView.OnQueryTextListener {

    private static final String TAG = "MainActivity";

    private MainActivityComponent mainActivityComponent;
    private RecyclerView rvTvSeries;
    private boolean isSearched;
    @Inject
    TvAdapter tvAdapter;
    @Inject
    MainScreenContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDependency();
        initView();
        getDataFromApi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.unSubscribeRx();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_tv_series, menu);
        MenuItem menuItem = menu.findItem(R.id.searchBar);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "Query Inserted", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (tvAdapter != null && newText != null) {
            tvAdapter.filter(newText);
        }
        if (newText != null && newText.isEmpty()) {
            isSearched = false;
        } else {
            isSearched = true;
        }

        return true;
    }

    private void initDependency() {
        mainActivityComponent = DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this))
                .tMDbServiceComponent(BaseApp.get(this).tmDbServiceComponent())
                .build();

        mainActivityComponent.inject(this);
    }

    private void initView() {
        rvTvSeries = findViewById(R.id.rvTvSeries);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvTvSeries.setLayoutManager(manager);
        rvTvSeries.setAdapter(tvAdapter);
        paginate(manager);
    }

    private void paginate(LinearLayoutManager manager) {
        rvTvSeries.addOnScrollListener(new RecyclerScrollUpListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.w(TAG, "onLoadMore() called with: page = [" + page + "], totalItemsCount = [" + totalItemsCount + "]");
                if (!isSearched) {
                    //presenter.getDbUpdate();
                    getDataFromApi();
                } else {
                    Log.w(TAG, "onLoadMore() On going search. won't load more tv series!");
                }
            }
        });
    }

    private void getDataFromApi() {
        //presenter.populateData();
        presenter.getDbUpdate();
    }


    @Override
    public void showData(List<Result> resultList) {
        Log.v(TAG, "showData() called with: resultList = [" + resultList + "]");
        tvAdapter.setTVData(resultList);
        /*tvAdapter.setTVData(getresults(page));
        page+=100;*/
    }



    private List<Result> getresults(int start){
        List<Result> results = new ArrayList<>();
        for (int i=start; i<start+100;i++){
            Result r = new Result();
            r.setId(i);
            r.setName(String.valueOf(i));
            results.add(r);
        }
        return results;
    }

    @Override
    public void showToastOnComplete() {
        Toast.makeText(this, "Top series list served", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorOnLoading() {
        Log.d(TAG, "showErrorOnLoading() called");
    }
}
