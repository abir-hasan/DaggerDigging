package me.abir.daggerdigging;

import java.util.List;

import me.abir.daggerdigging.models.Result;

/**
 * Created by abirhasan on 3/20/18.
 */

public interface MainScreenContract {

    interface View {
        void showData(List<Result> resultList);

        void showErrorOnLoading();
    }

    interface Presenter {
        void populateData();
    }
}
