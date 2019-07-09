package me.abir.daggerdigging.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import me.abir.daggerdigging.models.Result;

@Database(entities = {Result.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext()
                            , AppDatabase.class
                            , "offline_db").build();
        }
        return INSTANCE;
    }

    public abstract TvDao getTvDao();

}
