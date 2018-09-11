package com.charlyge.android.depopularmovies.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.charlyge.android.depopularmovies.model.Movies;

/**
 * Created by DELL PC on 8/31/2018.
 */

@Database(entities = {Movies.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static String DATABASE_NAME = "moviesDb";
    private static AppDatabase appDatabaseInstance;

    public static AppDatabase getAppDatabaseInstance(Context context){
        if(appDatabaseInstance==null){
            synchronized (LOCK){
                appDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,AppDatabase.DATABASE_NAME)
                .build();

            }

        }
        return appDatabaseInstance;
    }
    public abstract MovieDao movieDao();
}
