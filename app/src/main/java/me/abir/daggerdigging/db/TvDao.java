package me.abir.daggerdigging.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import me.abir.daggerdigging.models.Result;

@Dao
public interface TvDao {

    @Query("SELECT * FROM result")
    Flowable<List<Result>> getAllRepos();

    @Query("SELECT * FROM result")
    Single<List<Result>> getAllAsList();

    @Query("SELECT max(pageNo) as maxPage FROM result ")
    Single<Integer> getMaxPageCount();

    @Query("SELECT * FROM result where pageNo=:pageNo order by voteAverage DESC")
    Flowable<List<Result>> getAllReposByPage(int pageNo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Result result);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void multiInsert(List<Result> results);

    @Query("DELETE from result")
    void deleteAll();

    @Delete
    void delete(Result result);
}
