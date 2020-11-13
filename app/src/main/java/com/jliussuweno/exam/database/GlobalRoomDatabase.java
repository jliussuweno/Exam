package com.jliussuweno.exam.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.jliussuweno.exam.model.User;

@Database(entities = {User.class}, version = 2, exportSchema = false)
public abstract class GlobalRoomDatabase extends RoomDatabase {

    public static GlobalRoomDatabase INSTANCE;
    public abstract GlobalDao globalDao();

    public static GlobalRoomDatabase getInstance(final Context context){
        if (INSTANCE == null){
            synchronized (GlobalRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GlobalRoomDatabase.class, "database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
